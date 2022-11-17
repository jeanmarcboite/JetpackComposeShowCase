package box.example.showcase.ui.pages.calibre

import android.content.Context
import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context?, dbName: String, dbVersion: Int) :
    SQLiteOpenHelper(
        context,
        dbName,
        null,
        dbVersion
    ) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DatabaseHelper.Companion.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseHelper.Companion.TABLE_NAME)
        onCreate(db)
    }

    companion object {
        // Table Name
        const val TABLE_NAME = "COUNTRIES"

        // Table columns
        const val _ID = "_id"
        const val SUBJECT = "subject"
        const val DESC = "description"

        // Database Information
        const val DB_NAME = "JOURNALDEV_COUNTRIES.DB"

        // database version
        const val DB_VERSION = 1

        // Creating table query
        private val CREATE_TABLE =
            ("create table " + DatabaseHelper.Companion.TABLE_NAME + "(" + DatabaseHelper.Companion._ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.Companion.SUBJECT + " TEXT NOT NULL, " + DatabaseHelper.Companion.DESC + " TEXT);")
    }
}