package box.example.showcase.applib.notes

import box.example.showcase.applib.notes.models.Note

class NotesRepository(private val noteDao: NotesDao) {

    suspend fun getAll(): List<Note> {
        return noteDao.getAll()
    }

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun update(noteItem: Note) {
        noteDao.update(noteItem)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    suspend fun deleteAll() {
        noteDao.deleteAll()
    }
}