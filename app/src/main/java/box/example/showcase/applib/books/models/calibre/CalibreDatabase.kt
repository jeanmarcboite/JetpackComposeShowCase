package box.example.showcase.applib.books.models.calibre

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.j256.ormlite.android.apptools.OpenHelperManager

class CalibreDatabase {
    val books = mutableStateOf<List<CalibreEntity>?>(null)
    val authors = mutableStateOf<List<CalibreEntity>?>(null)
    var tables: List<List<String>> = listOf()

    fun read(context: Context) {
        val dbHelper =
            OpenHelperManager.getHelper(context, CalibreDatabaseHelper::class.java)
        if (!dbHelper.isOpen)
            throw Exception("helper not open")

        val dao = dbHelper.getDao(CalibreBook::class.java)
        // Don't kow why sqlite_schema does not work (sqlite version too old?)
        tables = dao.queryRaw("SELECT name FROM sqlite_master WHERE type = 'table'").results.map {
            it.toList()
        }

        books.value = dbHelper.getDao(CalibreBook::class.java).queryForAll()
        authors.value =
            dbHelper.getDao(CalibreAuthor::class.java).queryForAll()

        val books: Map<Int, CalibreBook>? = books.value?.associate {
            it.id to it as CalibreBook
        }
        val authors: Map<Int, CalibreAuthor>? = authors.value?.associate {
            it.id to it as CalibreAuthor
        }
        val languagesMap: Map<Int, CalibreLanguage>? =
            dbHelper.getDao(CalibreLanguage::class.java).queryForAll()?.associate {
                it.id to it as CalibreLanguage
            }


        dbHelper.getDao(BooksAuthorsLink::class.java).queryForAll().forEach {
            authors?.get(it.author)?.apply {
                books?.get(it.book)?.authors?.add(this)
            }
            books?.get(it.book)?.apply {
                authors?.get(it.author)?.books?.add(this)
            }
        }

        dbHelper.getDao(CalibreComment::class.java).queryForAll().forEach {
            books?.get(it.book)?.apply {
                comment = it.text.toString()
            }
        }

        dbHelper.getDao(CalibreBooksLanguagesLink::class.java).queryForAll()
            .forEach { languageLink ->
                books?.get(languageLink.book)?.apply {
                    if (languagesMap?.get(languageLink.lang_code)?.lang_code != null) {
                        languages.add(languagesMap?.get(languageLink.lang_code)?.lang_code!!)
                    }
                }
            }
    }
}