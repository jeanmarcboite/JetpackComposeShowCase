package box.example.showcase.ui.pages.database

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
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
import box.example.showcase.ui.Page
import box.example.showcase.ui.pages.lab.LabNote
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.GenericRawResults
import com.j256.ormlite.support.ConnectionSource
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.Whatsapp
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


const val DATABASE_NAME = "metadata.db"

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
        val databasePath = LocalContext.current.getDatabasePath(DATABASE_NAME)
        val listDocumentFile = remember { mutableStateOf<List<DocumentFile>?>(null) }

        val launcher =
            rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
                it?.run {
                    copyDatabase(context, this, context.getDatabasePath(DATABASE_NAME))
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
                        copyDatabase(context, metadata.uri, context.getDatabasePath(DATABASE_NAME))
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
                val dao = dbHelper.getDao(LabNote::class.java)
                try {
                    val results: GenericRawResults<Array<String>> =
                        dao.queryRaw("SELECT name FROM sqlite_schema WHERE type = 'table'")
                    Log.d("boxxxx", "tables $results")
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

    fun copyDatabase(context: Context, input: Uri, databasePath: File) {
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

class MetadataDatabaseHelper(context: Context) :
    OrmLiteSqliteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(
        database: SQLiteDatabase?,
        connectionSource: ConnectionSource?,
        oldVersion: Int,
        newVersion: Int
    ) {
        TODO("Not yet implemented")
    }

}
