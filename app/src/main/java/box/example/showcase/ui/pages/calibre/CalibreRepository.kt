package box.example.showcase.ui.pages.calibre

class CalibreRepository(private val calibreDao: CalibreDao) {
    suspend fun getAll(): List<CalibreBook> {
        return calibreDao.getAll()
    }
}