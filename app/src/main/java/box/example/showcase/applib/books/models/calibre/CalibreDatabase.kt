package box.example.showcase.applib.books.models.calibre

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.GenericRawResults

class CalibreDatabase {
    val books = mutableStateOf<List<CalibreEntity>?>(null)
    val authors = mutableStateOf<List<CalibreEntity>?>(null)

    fun read(context: Context) {
        val dbHelper =
            OpenHelperManager.getHelper(context, CalibreDatabaseHelper::class.java)
        if (!dbHelper.isOpen)
            throw Exception("helper not open")

        val dao = dbHelper.getDao(CalibreBook::class.java)
        // Don't kow why sqlite_schema does not work (sqlite version too old?)
        val query: GenericRawResults<Array<String>> =
            dao.queryRaw("SELECT name FROM sqlite_master WHERE type = 'table'")
        val results = query.results.map {
            it.toList()
        }
        Log.d("boxxxx", "tables ${results}")
        //val list: MutableList<CalibreBook> = dao.queryForAll()
        //Log.d("boxxx [ormlite]", "list of ${list.size} books")
        books.value = dbHelper.getDao(CalibreBook::class.java).queryForAll()
        authors.value =
            dbHelper.getDao(CalibreAuthor::class.java).queryForAll()

        val books: Map<Int, CalibreBook>? = books.value?.associate {
            it.id to it as CalibreBook
        }
        val authors: Map<Int, CalibreAuthor>? = authors.value?.associate {
            it.id to it as CalibreAuthor
        }

        dbHelper.getDao(BooksAuthorsLink::class.java).queryForAll().forEach {
            authors?.get(it.author)?.apply {
                books?.get(it.book)?.authors?.add(this)
            }
            books?.get(it.book)?.apply {
                authors?.get(it.author)?.books?.add(this)
            }
        }

        books?.forEach {
            Log.d("boxxx [book]", it.value.title.toString())
        }
    }
}