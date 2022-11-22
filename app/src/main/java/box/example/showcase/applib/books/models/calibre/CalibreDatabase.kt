package box.example.showcase.applib.books.models.calibre

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.Dao

class CalibreDatabase(context: Context) {
    private var errorMessage = "database read error"

    val books = mutableStateOf<List<CalibreEntity>?>(null)
    val authors = mutableStateOf<List<CalibreEntity>?>(null)

    private val dao: Dao<CalibreBook, *>
    private val tables: List<String>
    private val CustomColumns: MutableMap<Int, Pair<CustomColumnEntry, List<CustomColumn>>> =
        mutableMapOf()

    init {
        try {
            val dbHelper =
                OpenHelperManager.getHelper(context, CalibreDatabaseHelper::class.java)
            if (!dbHelper.isOpen)
                throw Exception("helper not open")

            errorMessage = "cannot get database tables"
            dao = dbHelper.getDao(CalibreBook::class.java)
            // Don't kow why sqlite_schema does not work (sqlite version too old?)
            tables =
                dao.queryRaw("SELECT name FROM sqlite_master WHERE type = 'table'").results.map {
                    it.toList().first()
                }

            val customColumns = tables.filter { it.startsWith("custom_column_") }
            customColumns.forEach { getCustomColumns(it) }
            errorMessage = "cannot get books"

            books.value = dbHelper.getDao(CalibreBook::class.java).queryForAll()

            errorMessage = "cannot get authors"
            authors.value =
                dbHelper.getDao(CalibreAuthor::class.java).queryForAll()

            errorMessage = "cannot get custom columns"
            var custom_column_entries: List<CustomColumnEntry>? =
                dbHelper.getDao(CustomColumnEntry::class.java).queryForAll()
            custom_column_entries?.forEach { customColumnEntry ->
                errorMessage = "cannot get custom column ${customColumnEntry.id}"
                val listCustomColumn: List<CustomColumn> = when (customColumnEntry.id) {
                    1 -> dbHelper.getDao(CustomColumn1::class.java).queryForAll()
                    2 -> dbHelper.getDao(CustomColumn2::class.java).queryForAll()
                    else -> listOf()
                }
                Log.v(
                    "boxxx [custom column]",
                    "custom column ${customColumnEntry.id}[${customColumnEntry.name}] -> ${
                        listCustomColumn.joinToString(
                            " / "
                        )
                    }"
                )

                // set list of CustomColumn values for custom_column_i
                CustomColumns[customColumnEntry.id] = Pair(customColumnEntry, listCustomColumn)
            }

            val books: Map<Int, CalibreBook>? = books.value?.associate {
                it.id to it as CalibreBook
            }

            //map i to CustomColumnEntry
            //val customLinks: MutableMap<CustomColumnEntry, List<BooksCustomColumnsLink>> = mutableMapOf()
            // CustomColumns: MutableMap<Int, Pair<CustomColumnEntry, List<CustomColumn>>>
            CustomColumns.forEach { (cc_key: Int, cc_value: Pair<CustomColumnEntry, List<CustomColumn>>) ->
                val custom_column_map = cc_value.second.associate { it.id to it }
                // get book_custom_column_i_link
                errorMessage = "cannot get custom column links $cc_key"
                val book_custom_column_i_links: List<BooksCustomColumnsLink> = when (cc_key) {
                    1 -> dbHelper.getDao(BooksCustomColumn1Link::class.java).queryForAll()
                    2 -> dbHelper.getDao(BooksCustomColumn2Link::class.java).queryForAll()
                    else -> listOf()
                }
                book_custom_column_i_links?.forEach { bookCustomColumnLink ->
                    val book = books?.get(bookCustomColumnLink.book)
                    val custom_column: CustomColumn? =
                        custom_column_map[bookCustomColumnLink.value]

                    if (book != null && custom_column != null) {
                        if (book.customColumns[cc_value.first] == null)
                            book.customColumns[cc_value.first] = mutableListOf(custom_column)
                        else
                            book.customColumns[cc_value.first]!!.add(custom_column)
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

    private fun getCustomColumns(table: String) {
        try {
            val entries: List<Array<String>> = dao.queryRaw("select * from $table").results.toList()
            val entriesString: List<String> =
                dao.queryRaw("select * from $table").results.toList().map {
                    "[" + it.joinToString() + "]"
                }
            Log.d("boxxx [getCustom]", "$table > ${entriesString}")
            entries.forEach {
                //Log.d("boxxx [getCustom]", "$table > ${it.joinToString()}")
            }
            val info = dao.queryRaw("PRAGMA table_info($table)").results.toList().map {
                "[" + it.joinToString() + "]"
            }
            Log.d("boxxx [getCustom]", "$table > ${info}")
        } catch (e: Exception) {
            Log.e("boxxx [custom]", e.message.toString())
        }
    }
}
