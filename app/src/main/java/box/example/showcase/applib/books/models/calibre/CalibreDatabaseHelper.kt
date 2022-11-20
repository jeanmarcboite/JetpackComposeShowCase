package box.example.showcase.applib.books.models.calibre

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource

class CalibreDatabaseHelper(context: Context) :
    OrmLiteSqliteOpenHelper(context, DatabaseName, null, DatabaseVersion) {
    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        Log.e("boxxx", "MetadatabaseHelper::onCreate Not yet implemented")
    }

    override fun onUpgrade(
        database: SQLiteDatabase?,
        connectionSource: ConnectionSource?,
        oldVersion: Int,
        newVersion: Int
    ) {
        Log.w(
            "boxxx",
            "MetadatabaseHelper::onUpgrade($oldVersion, $newVersion) Not yet implemented"
        )
    }

    companion object {
        const val DatabaseName = "metadata.db"
        const val DatabaseVersion = 25
    }
}
