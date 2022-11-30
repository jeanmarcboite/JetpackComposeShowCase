package box.example.showcase.applib.books.components.calibre

import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.documentfile.provider.DocumentFile
import androidx.hilt.navigation.compose.hiltViewModel
import box.example.showcase.applib.books.models.EpubContainer
import box.example.showcase.applib.books.models.EpubMetadata
import box.example.showcase.applib.books.models.calibre.CalibreBook
import box.example.showcase.applib.books.models.calibre.CalibreCustomColumn
import box.example.showcase.applib.ui.components.data.*
import box.example.showcase.ui.components.OutlinedCard
import box.example.showcase.ui.models.CalibreDatabaseViewModel
import java.util.zip.ZipInputStream

@Composable
fun CalibreBook.ViewHeader(columnsList: Map<String, Boolean>) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        languages.forEach {
            it.Language()
        }

        Text(
            text = title.toString(), modifier = Modifier.padding(8.dp)
        )
    }

    columnsList.forEach {
        Row {
            columns[it.key]?.apply {
                if (it.value)
                    View(it.key)
                else
                    View()
            }
        }
    }

}

@Composable
fun CalibreBook.ViewSummary(onClick: () -> Unit = {}) {
    OutlinedCard(
        onClick = onClick,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(), label = {
            Text(authors.joinToString { it.name.toString() })
        }) {
        Column(modifier = Modifier.padding(8.dp)) {
            ViewHeader(
                mapOf(
                    "tags" to false,
                    "series" to true,
                )
            )
        }
    }
}

@Composable
fun CalibreBook.ViewDetails() {
    val viewModel = hiltViewModel<CalibreDatabaseViewModel>()
    val context = LocalContext.current
    val scroll = rememberScrollState(0)
    Column(modifier = Modifier.padding(8.dp)) {
        Text(authors.joinToString { it.name.toString() })
        val columnsList = mapOf(
            "tags" to false,
            "series" to true,
            "ratings" to false,
        )
        ViewHeader(columnsList)
        OpenFileButton()
        Column(modifier = Modifier.verticalScroll(scroll)) {
            columns.forEach {
                if (it.key !in (columnsList.keys)) {
                    it.value.View(it.key)
                }
            }
            customColumns.forEach { entry: Map.Entry<CalibreCustomColumn, MutableList<String>> ->
                entry.ViewIfComments()
            }
            customColumns.forEach { column: Map.Entry<CalibreCustomColumn, MutableList<String>> ->
                if (column.key.datatype != "bool" && column.key.datatype != "comments") {
                    column.View()
                }
            }
            comment?.apply {
                listOf(this).ViewHtml("Comments")
            }
        }
    }
}

@Composable
fun CalibreBook.OpenFileButton() {
    if (path != null) {
        val context = LocalContext.current
        val viewModel = hiltViewModel<CalibreDatabaseViewModel>()
        val directoryLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) {
                it?.run {
                    viewModel.documentTree.value = DocumentFile.fromTreeUri(context, this)
                    openFile(context, viewModel)
                }
            }
        //val item = context.contentResolver.openInputStream(path)

        Button(
            onClick = {
                if (viewModel.documentTree.value == null)
                    directoryLauncher.launch(null)
                else
                    openFile(context, viewModel)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Open")
        }
    }
}

fun CalibreBook.openFile(context: Context, viewModel: CalibreDatabaseViewModel) {
    if (path != null) {
        var documentFile = viewModel.documentTree.value
        var errorMessage: String? = null
        var parts = path.split("/")
        while (parts.size > 0 && errorMessage == null) {
            val directory = parts.first()
            documentFile = documentFile?.findFile(directory)
            if (documentFile?.isDirectory == false) {
                errorMessage = "could not find '${parts.first()}' in directory"
            }
            parts = parts.drop(1)
        }
        if (errorMessage != null)
            Log.e("boxxx [openFile]", errorMessage)
        else {
            val epub: DocumentFile? = documentFile!!.listFiles().find {
                it.name?.endsWith(".epub") == true
            }
            if (epub != null) {
                Log.v("boxxx [openFile]", "epub: ${epub.name}")
                val zipInputStream =
                    ZipInputStream(context.contentResolver.openInputStream(epub.uri))
                val unzip = generateSequence { zipInputStream.nextEntry }
                    .filterNot { it.isDirectory }
                    .map {
                        val bytes = ByteArray(it.size.toInt())
                        zipInputStream.read(bytes)
                        Pair(it.name, String(bytes))
                    }.toMap()
                Log.v(
                    "boxxx [openFile]",
                    "mimetype: ${unzip["mimetype"]}"
                )
                val container = EpubContainer.parseXml(unzip["META-INF/container.xml"] ?: "")
                Log.v(
                    "boxxx [openFile]",
                    "container: ${container}"
                )
                container.rootFile?.apply {
                    val epub = EpubMetadata.parseXml(unzip[container.rootFile] ?: "")
                    Log.v(
                        "boxxx [openFile]",
                        "epub: ${epub}"
                    )
                }
            }
        }
    }
}

@Composable
fun Map.Entry<CalibreCustomColumn, MutableList<String>>.View() {
    key.name?.let {
        when (key.datatype) {
            "bool" -> (value.first().toInt() != 0).Bool(it)
            "rating" -> {
                value.first().toFloat().Rating(it)
            }
            "datetime" -> value.View(it)
            "enumeration" -> key.ViewEnumeration(value)
            else -> value.Badges(it)
        }
    }
}

@Composable
fun CalibreCustomColumn.ViewEnumeration(value: List<String>) {
    OutlinedCard(modifier = Modifier.fillMaxWidth(), label = { Text(name.toString()) }) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            value.forEach {
                Text(it, color = colorMap[it] ?: Color.Red)
            }
        }
    }

}

@Composable
fun Map.Entry<CalibreCustomColumn, MutableList<String>>.ViewIfBool() {
    if (key.datatype == "bool") key.name?.let { (value.first().toInt() != 0).Bool(it) }
}


@Composable
fun Map.Entry<CalibreCustomColumn, MutableList<String>>.ViewIfComments() {
    if (key.datatype == "comments") key.name?.let { value.ViewHtml(it) }
}

