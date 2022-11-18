package box.example.showcase.ui.pages.database

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.documentfile.provider.DocumentFile
import box.example.showcase.R
import box.example.showcase.applib.books.models.calibre.MetadataAuthor
import box.example.showcase.applib.books.models.calibre.MetadataBook
import box.example.showcase.applib.books.models.calibre.MetadataDatabaseHelper
import box.example.showcase.ui.Page
import box.example.showcase.ui.pages.database.components.View
import box.example.showcase.ui.theme.margin_half
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.GenericRawResults
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.Whatsapp
import kotlinx.coroutines.launch
import java.io.FileOutputStream
import java.io.OutputStream


const val DATABASE_NAME = "metadata.db"
const val DATABASE_VERSION = 25

class DatabasePage :
    Page(
        FontAwesomeIcons.Brands.Whatsapp,
        R.string.database_page_route,
        R.string.database_page_title
    ) {
    val databaseAvailable = mutableStateOf(false)

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    override fun Content() {
        val scroll = rememberScrollState()
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current
        val listDocumentFile = remember { mutableStateOf<List<DocumentFile>?>(null) }
        val listBook = remember { mutableStateOf<List<MetadataBook>?>(null) }
        val listAuthor = remember { mutableStateOf<List<MetadataAuthor>?>(null) }

        val launcher =
            rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
                it?.run {
                    copyDatabase(context, this)
                }
            }

        val directoryLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) {
                it?.run {
                    val tree = DocumentFile.fromTreeUri(context, this)
                    val metadata: DocumentFile? = tree?.listFiles()?.find { documentFile ->
                        documentFile.name == DATABASE_NAME
                    }
                    if (metadata?.isFile == true) {
                        copyDatabase(context, metadata.uri)
                    }

                }
            }

        Column {
            Button(modifier = Modifier.fillMaxWidth(),
                onClick = {
                    launcher.launch("application/octet-stream")
                }) {
                Text("copy database")
            }

            Button(modifier = Modifier.fillMaxWidth(),
                onClick = {
                    directoryLauncher.launch(null)
                }) {
                Text("copy calibre library database")
            }

            if (databaseAvailable.value) {
                val dbHelper =
                    OpenHelperManager.getHelper(context, MetadataDatabaseHelper::class.java)
                if (dbHelper.isOpen) {
                    Log.d("boxxxx", "database ${dbHelper.databaseName} is open")
                }
                try {
                    val dao = dbHelper.getDao(MetadataBook::class.java)
                    // Don't kow why sqlite_schema does not work (sqlite version too old?)
                    val query: GenericRawResults<Array<String>> =
                        dao.queryRaw("SELECT name FROM sqlite_master WHERE type = 'table'")
                    val results = query.results.map {
                        it.toList()
                    }
                    Log.d("boxxxx", "tables ${results}")
                    val list: MutableList<MetadataBook> = dao.queryForAll()
                    Log.d("boxxx [ormlite]", "list of ${list.size} books")
                    listBook.value = list

                    listBook.value = dbHelper.getDao(MetadataBook::class.java).queryForAll()
                    listAuthor.value = dbHelper.getDao(MetadataAuthor::class.java).queryForAll()

                } catch (e: Exception) {
                    val errorMessage = if (e.message == null) {
                        ""
                    } else {
                        val err = StringBuilder()
                        err.append(e.message)
                        var ecause = e.cause
                        while (ecause != null) {
                            err.append(": ")
                            err.append(ecause.message)
                            ecause = ecause.cause
                        }
                        err.toString()
                    }
                    Log.e("boxxx [readDatabase]", errorMessage)
                    e.printStackTrace()
                    coroutineScope.launch {
                        mainViewModel.snackbarHostState.showSnackbar(
                            errorMessage,
                            withDismissAction = true,
                            duration = SnackbarDuration.Indefinite
                        )
                    }
                }
            }

            listBook.value?.apply {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(top = margin_half)
                ) {
                    items(listBook.value!!) {
                        it.View()
                    }
                }
            }

            listDocumentFile.value?.run {
                Column(modifier = Modifier.verticalScroll(scroll)) {
                    forEach {
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            Row {
                                Text(it.name.toString())
                                if (it.type != null)
                                    Text(
                                        "[${it.type.toString()}]",
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                            }
                        }
                    }
                }
            }
        }
    }

    fun copyDatabase(context: Context, input: Uri) {
        databaseAvailable.value = false
        val item = context.contentResolver.openInputStream(input)
        val bytes: ByteArray? = item?.readBytes()
        item?.close()

        bytes?.run {
            val output: OutputStream = FileOutputStream(context.getDatabasePath(DATABASE_NAME))
            output.write(this, 0, size)
            output.close()
            databaseAvailable.value = true
        }
    }
}

