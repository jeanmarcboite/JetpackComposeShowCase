package box.example.showcase.applib.books.models.calibre

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import box.example.showcase.ui.pages.database.DATABASE_NAME
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource

class MetadataDatabaseHelper(context: Context) :
    OrmLiteSqliteOpenHelper(context, DATABASE_NAME, null, 23) {
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
