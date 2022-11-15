package box.example.showcase.applib.notes

import androidx.room.*
import box.example.showcase.applib.notes.models.Note

@Dao
interface NotesDao {
    @Query("SELECT * from notes")
    suspend fun getAll(): List<Note>

    @Query("SELECT * from notes where id = :id")
    fun getById(id: String): Note?

    @Insert
    suspend fun insert(item: Note)

    @Update
    suspend fun update(item: Note)

    @Delete
    suspend fun delete(item: Note)

    @Query("DELETE FROM notes")
    suspend fun deleteAll()
}