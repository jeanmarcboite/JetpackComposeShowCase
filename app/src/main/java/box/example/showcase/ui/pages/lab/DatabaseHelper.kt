package box.example.showcase.ui.pages.lab

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils

class DatabaseHelper(context: Context) :
    OrmLiteSqliteOpenHelper(context, "entries", null, 2) {
    init {
        Log.d("boxxx [ormlite]", "DatabaseHelper")
    }

    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        Log.i("boxxx [ormlite]", "onCreate")
        TableUtils.createTableIfNotExists(connectionSource, LabNote::class.java)
        val dao = getDao(LabNote::class.java)
        val note = LabNote(title = "note-id")
        dao.create(note)
        //TableUtils.dropTable<LabNote, Any>(connectionSource, LabNote::class.java, true)
    }

    override fun onUpgrade(
        database: SQLiteDatabase?,
        connectionSource: ConnectionSource?,
        oldVersion: Int,
        newVersion: Int
    ) {
        Log.d("boxxx [ormlite]", "onUpgrade")
        TableUtils.createTableIfNotExists(connectionSource, LabNote::class.java)
    }

    fun getLabNoteDao(): Dao<LabNote, *> {
        if (labNoteDao == null) {
            labNoteDao = getDao(LabNote::class.java)
        }
        return labNoteDao!!
    }

    fun getLabNote2Dao(): Dao<LabNote2, *> {
        if (labNote2Dao == null) {
            labNote2Dao = getDao(LabNote2::class.java)
        }
        return labNote2Dao!!
    }

    companion object {
        var labNoteDao: Dao<LabNote, *>? = null
        var labNote2Dao: Dao<LabNote2, *>? = null
    }
}
