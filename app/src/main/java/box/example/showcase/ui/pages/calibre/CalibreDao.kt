package box.example.showcase.ui.pages.calibre

import androidx.room.Dao
import androidx.room.Query

@Dao
interface CalibreDao {
    @Query("SELECT * from books")
    suspend fun getAll(): List<CalibreBook>
}