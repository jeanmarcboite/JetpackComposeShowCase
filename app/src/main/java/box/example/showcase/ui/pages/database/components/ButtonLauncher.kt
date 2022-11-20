package box.example.showcase.ui.pages.database.components

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.documentfile.provider.DocumentFile
import box.example.showcase.applib.books.models.calibre.CalibreDatabaseHelper
import java.io.FileOutputStream
import java.io.OutputStream

@Composable
fun LauncherButton(databaseVersion: MutableState<Int>) {
    val context = LocalContext.current

    fun copyDatabase(context: Context, input: Uri) {
        val item = context.contentResolver.openInputStream(input)
        val bytes: ByteArray? = item?.readBytes()
        item?.close()

        bytes?.apply {
            val output: OutputStream =
                FileOutputStream(context.getDatabasePath(CalibreDatabaseHelper.DatabaseName))
            output.write(this, 0, size)
            output.close()
            databaseVersion.value++
        }
    }

/*
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            it?.run {
                copyDatabase(context, this)
            }
        }

 */

    val directoryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) {
            it?.run {
                val tree = DocumentFile.fromTreeUri(context, this)
                val metadata: DocumentFile? = tree?.listFiles()?.find { documentFile ->
                    documentFile.name == CalibreDatabaseHelper.DatabaseName
                }
                if (metadata?.isFile == true) {
                    copyDatabase(context, metadata.uri)
                }

            }
        }

    FloatingActionButton(
        onClick = {
            //launcher.launch("application/octet-stream")
            directoryLauncher.launch(null)
        },
        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
    ) {
        Icon(Icons.Filled.Add, "Localized description")
    }
}