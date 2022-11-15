package box.example.showcase.ui.pages.todo.models

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {
    @Query("SELECT * from todo")
    fun getAll(): LiveData<List<Todo>>

    @Query("SELECT * from todo where ID = :id")
    fun getById(id: Int): Todo?

    @Insert
    suspend fun insert(item: Todo)

    @Update
    suspend fun update(item: Todo)

    @Delete
    suspend fun delete(item: Todo)

    @Query("DELETE FROM todo")
    suspend fun deleteAll()
}