package box.example.showcase.applib.books.models.calibre

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.Dao

class CalibreDatabase {
    val books = mutableStateOf<List<CalibreEntity>?>(null)
    val authors = mutableStateOf<List<CalibreEntity>?>(null)
    var tables: List<List<String>> = listOf()
    val customColumns: MutableMap<Int, Pair<CustomColumnEntry, List<CustomColumn>>> = mutableMapOf()
    fun read(context: Context) {
        var errorMessage = "database read error"
        try {
            val dbHelper =
                OpenHelperManager.getHelper(context, CalibreDatabaseHelper::class.java)
            if (!dbHelper.isOpen)
                throw Exception("helper not open")

            errorMessage = "cannot get database tables"
            val dao: Dao<CalibreBook, *> = dbHelper.getDao(CalibreBook::class.java)
            // Don't kow why sqlite_schema does not work (sqlite version too old?)
            tables =
                dao.queryRaw("SELECT name FROM sqlite_master WHERE type = 'table'").results.map {
                    it.toList()
                }
            errorMessage = "cannot get books"

            books.value = dbHelper.getDao(CalibreBook::class.java).queryForAll()

            errorMessage = "cannot get authors"
            authors.value =
                dbHelper.getDao(CalibreAuthor::class.java).queryForAll()

            errorMessage = "cannot get custom columns"
            var custom_columns: List<CustomColumnEntry>? =
                dbHelper.getDao(CustomColumnEntry::class.java).queryForAll()
            custom_columns?.forEach {
                errorMessage = "cannot get custom column ${it.id}"
                val customColumn: List<CustomColumn> = when (it.id) {
                    1 -> dbHelper.getDao(CustomColumn1::class.java).queryForAll()
                    2 -> dbHelper.getDao(CustomColumn2::class.java).queryForAll()
                    else -> listOf()
                }
                customColumns[it.id] = Pair(it, customColumn)
            }

            val books: Map<Int, CalibreBook>? = books.value?.associate {
                it.id to it as CalibreBook
            }
            //val customLinks: MutableMap<CustomColumnEntry, List<BooksCustomColumnsLink>> = mutableMapOf()
            customColumns.forEach {
                errorMessage = "cannot get custom column links ${it.key}"
                val customLinks: List<BooksCustomColumnsLink> = when (it.key) {
                    1 -> dbHelper.getDao(BooksCustomColumn1Link::class.java).queryForAll()
                    2 -> dbHelper.getDao(BooksCustomColumn2Link::class.java).queryForAll()
                    else -> listOf()
                }
                customLinks?.forEach { link: BooksCustomColumnsLink ->
                    val custom_column: List<CustomColumn>? =
                        customColumns[link.value]?.second
                    val book = books?.get(link.book)
                    if (book != null && custom_column != null) {
                        book.customColumns[link.value] = custom_column
                    }
                }
            }

            val authors: Map<Int, CalibreAuthor>? = authors.value?.associate {
                it.id to it as CalibreAuthor
            }

            errorMessage = "cannot get languages"
            val languagesMap: Map<Int, CalibreLanguage>? =
                dbHelper.getDao(CalibreLanguage::class.java).queryForAll()?.associate {
                    it.id to it as CalibreLanguage
                }

            errorMessage = "cannot book authors links"

            dbHelper.getDao(BooksAuthorsLink::class.java).queryForAll().forEach {
                authors?.get(it.author)?.apply {
                    books?.get(it.book)?.authors?.add(this)
                }
                books?.get(it.book)?.apply {
                    authors?.get(it.author)?.books?.add(this)
                }
            }

            errorMessage = "cannot comments"
            dbHelper.getDao(CalibreComment::class.java).queryForAll().forEach {
                books?.get(it.book)?.apply {
                    comment = it.text.toString()
                }
            }

            dbHelper.getDao(CalibreBooksLanguagesLink::class.java).queryForAll()
                .forEach { languageLink ->
                    books?.get(languageLink.book)?.apply {
                        if (languagesMap?.get(languageLink.lang_code)?.lang_code != null) {
                            languages.add(languagesMap.get(languageLink.lang_code)?.lang_code!!)
                        }
                    }
                }
        } catch (e: Exception) {
            throw Exception(errorMessage, e)
        }
    }
}