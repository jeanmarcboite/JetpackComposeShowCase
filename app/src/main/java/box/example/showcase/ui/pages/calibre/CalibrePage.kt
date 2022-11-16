package box.example.showcase.ui.pages.calibre

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.documentfile.provider.DocumentFile
import androidx.room.Room
import box.example.showcase.R
import box.example.showcase.ui.Page
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.BookReader
import java.io.File

class CalibrePage :
    Page(
        FontAwesomeIcons.Solid.BookReader,
        R.string.calibre_page_route,
        R.string.calibre_page_title
    ) {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val documentTree = remember { mutableStateOf<Uri?>(null) }
        val scroll = rememberScrollState(0)
        val launcher =
            rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) {
                documentTree.value = it

                if (documentTree.value != null) {
                    val tree = DocumentFile.fromTreeUri(context, documentTree.value!!)
                    val files: Array<DocumentFile>? = tree?.listFiles()
                    Log.d("boxxx", "${tree?.listFiles()}")
                }
            }

        Column {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    launcher.launch(null)
                },
            ) {
                Text("Read Calibre Library")

                Icon(
                    FontAwesomeIcons.Solid.BookReader, "",
                    modifier = Modifier
                        .size(32.dp)
                        .padding(start = 16.dp)
                )
            }

            Text(documentTree.value.toString())

            Column(modifier = Modifier.verticalScroll(scroll)) {
                if (documentTree.value != null) {
                    val tree = DocumentFile.fromTreeUri(context, documentTree.value!!)
                    val files: Array<DocumentFile>? = tree?.listFiles()

                    val metadata: DocumentFile? = files?.find {
                        it.name == "metadata.db"
                    }
                    if (metadata == null) {
                        Text("no metadata")

                    } else {
                        readDatabase(metadata)
                        files?.forEach {
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
    }
}

@Composable
fun readDatabase(metadata: DocumentFile) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        try {
            val database: CalibreDatabase =
                Room.databaseBuilder(context, CalibreDatabase::class.java, metadata.name!!)
                    .createFromFile(File(metadata.uri.path))
                    .build()
            val books = database.dao().getAll()
            Log.d("boxxxx", books.toString())
        } catch (e: Exception) {
            Log.e("boxxx [readDatabase]", "${e.message}: ${e.cause}")
        }
    }
}