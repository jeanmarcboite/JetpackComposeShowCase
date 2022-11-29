package box.example.showcase.ui.pages.calibre.components

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.documentfile.provider.DocumentFile
import androidx.hilt.navigation.compose.hiltViewModel
import box.example.showcase.ApplicationStateViewModel
import box.example.showcase.applib.books.models.calibre.CalibreDatabaseHelper
import box.example.showcase.ui.models.CalibreDatabaseViewModel
import compose.icons.TablerIcons
import compose.icons.tablericons.Folder
import kotlinx.coroutines.launch
import java.io.FileOutputStream
import java.io.OutputStream

@Composable
fun CalibreDatabaseSelection(
) {
    val databaseVersion = hiltViewModel<CalibreDatabaseViewModel>().version

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = hiltViewModel<ApplicationStateViewModel>().snackbarHostState

    fun copyDatabase(context: Context, input: Uri) {
        val output = context.getDatabasePath(CalibreDatabaseHelper.DatabaseName)
        Log.d("boxxx [DirectoryLauncher]", "copy datbase ${input} to $output ")
        val item = context.contentResolver.openInputStream(input)
        val bytes: ByteArray? = item?.readBytes()
        item?.close()

        bytes?.apply {
            val outputStream: OutputStream =
                FileOutputStream(output)
            outputStream.write(this, 0, size)
            outputStream.close()
            databaseVersion.value++
            Log.d("boxxx [DirectoryLauncher]", "datbase ${input} copied to $output ")
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
                val errorMessage = if (metadata?.isFile == true) {
                    copyDatabase(context, metadata.uri)
                    null
                } else {
                    val error =
                        "could not find ${CalibreDatabaseHelper.DatabaseName} in directory"
                    Log.e("boxxx", error)
                    error
                }

                coroutineScope.launch {
                    if (errorMessage == null) {
                        snackbarHostState.showSnackbar(
                            "database copied",
                            duration = SnackbarDuration.Short
                        )
                    } else {
                        snackbarHostState.showSnackbar(
                            errorMessage,
                            withDismissAction = true,
                            duration = SnackbarDuration.Indefinite
                        )
                    }
                }
            }
        }

    ExtendedFloatingActionButton(
        modifier = Modifier.width(64.dp),
        onClick = { directoryLauncher.launch(null) },
        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
        //elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
        icon = {
            Icon(
                TablerIcons.Folder, "Open calibre database",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }, text = {
            Text(
                text = "open db",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        })
}