package box.example.showcase.applib.books.models.calibre

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import box.example.showcase.ui.pages.database.DATABASE_NAME
import box.example.showcase.ui.pages.database.DATABASE_VERSION
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource

class MetadataDatabaseHelper(context: Context) :
    OrmLiteSqliteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        Log.e("boxxx", "MetadatabaseHelper::onCreate Not yet implemented")
    }

    override fun onUpgrade(
        database: SQLiteDatabase?,
        connectionSource: ConnectionSource?,
        oldVersion: Int,
        newVersion: Int
    ) {
        Log.e("boxxx", "MetadatabaseHelper::onUpgrade Not yet implemented")
    }

}
