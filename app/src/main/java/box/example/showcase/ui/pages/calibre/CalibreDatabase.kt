package box.example.showcase.ui.pages.calibre

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CalibreBook::class], version = 23, exportSchema = false)
abstract class CalibreDatabase : RoomDatabase() {
    abstract fun dao(): CalibreDao

    companion object {

        private var INSTANCE: CalibreDatabase? = null

        fun getDatabase(context: Context): CalibreDatabase {
            Log.e("boxxx", "getDatabase")
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CalibreDatabase::class.java,
                    "books"
                ).fallbackToDestructiveMigration()
                    .build()

                instance
            }
        }
    }
}