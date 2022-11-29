package box.example.showcase.ui.pages.lab

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import box.example.showcase.R
import box.example.showcase.ui.Page
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.table.TableUtils
import compose.icons.TablerIcons
import compose.icons.tablericons.Flask

class LabPage :
    Page(
        TablerIcons.Flask,
        R.string.lab_page_route,
        R.string.lab_page_title
    ) {
    @Composable
    override fun Content() {
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            try {
                val databasePath = context.getDatabasePath("entries")
                Log.d("boxx [ormlite]", "database path: $databasePath")
                val dbHelper = OpenHelperManager.getHelper(context, DatabaseHelper::class.java)
                val connectionSource = dbHelper.connectionSource
                TableUtils.createTableIfNotExists(connectionSource, LabNote::class.java)
                TableUtils.createTableIfNotExists(connectionSource, LabNote2::class.java)
                //val writableDatabase: SQLiteDatabase = dbHelper.writableDatabase
                //Log.d("boxxx [ormlite]", "writableDatabase isOpen: ${writableDatabase.isOpen}")
                //writableDatabase.insert("table")
                val dao = dbHelper.getLabNoteDao()
                dao.create(LabNote(title = "note"))
                val dao2 = dbHelper.getLabNote2Dao()
                dao2.create(LabNote2(title = "note2"))
                //val dao = dbHelper.getDao(LabNote::class.java)
                Log.d("boxxx [ormlite]", "queryforAll")
                val list = dao.queryForAll()
                Log.d("boxxx [ormlite]", "list: ${list}")
            } catch (e: Exception) {
                val cause = if (e.cause != null) ": ${e.cause}" else ""
                Log.e("boxxx [ormlite]", "${e.message}$cause")
            }
        }
        /*
        val databaseFilename = "sample.db"
        val connectionString = "jdbc:sqlite:$databaseFilename"
        val connectionSource = JdbcConnectionSource(connectionString)
        val dao =
            DaoManager.createDao(connectionSource, LabNote::class.java) as Dao<LabNote, String>

        // create table
        TableUtils.createTableIfNotExists(connectionSource, LabNote::class.java)

        // save dummy data
        val num = Random.nextInt(3)
        dao.createOrUpdate(LabNote("id-$num", num, "Hello!"))

        // query data by id
        for (i in 0..2) {
            val record = dao.queryForId("id-$i")
            println("Query for ID 'id-$i': $record")
        }

        // query all data
        dao.forEach { record -> println("Query for all: $record") }

        // close connection
        connectionSource.closeQuietly()

         */
    }
}
