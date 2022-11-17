package box.example.showcase.ui.pages.database

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
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.documentfile.provider.DocumentFile
import box.example.showcase.R
import box.example.showcase.ui.Page
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.Whatsapp

class DatabasePage :
    Page(
        FontAwesomeIcons.Brands.Whatsapp,
        R.string.database_page_route,
        R.string.database_page_title
    ) {
    @Composable
    override fun Content() {
        val scroll = rememberScrollState()
        val context = LocalContext.current
        val databasePath = LocalContext.current.getDatabasePath("metadata.db")
        val listDocumentFile = remember { mutableStateOf<List<DocumentFile>?>(null) }

        val launcher =
            rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
                it?.run {
                    val item = context.contentResolver.openInputStream(this)
                    val bytes: ByteArray? = item?.readBytes()
                    //println(bytes)
                    item?.close()
                    // copyDatabase(this, context.getDatabasePath("metadata.db"))
                    Log.d("boxxx", "copy ${bytes?.size} bytes to $databasePath")
                }
            }

        val directoryLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) {
                it?.run {
                    val tree = DocumentFile.fromTreeUri(context, this)
                    listDocumentFile.value = tree?.listFiles()?.toList()
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
}