package box.example.showcase.ui.pages.calibre

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase

class DBManager(val context: Context, val dbName: String, val dbVersion: Int) {
    private var dbHelper: DatabaseHelper? = null
    private var database: SQLiteDatabase? = null

    @Throws(SQLException::class)
    fun open(): DBManager {
        dbHelper = DatabaseHelper(context, dbName, dbVersion)
        database = dbHelper?.readableDatabase
        return this
    }

    fun close() {
        dbHelper?.close()
    }
}