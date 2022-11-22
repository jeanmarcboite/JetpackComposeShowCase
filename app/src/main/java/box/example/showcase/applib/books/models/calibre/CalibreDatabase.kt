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

            errorMessage = "cannot get books"
            books.value = dbHelper.getDao(CalibreBook::class.java).queryForAll()
            val bookMap: Map<Int, CalibreBook>? = books.value?.associate {
                it.id to it as CalibreBook
            }

            errorMessage = "cannot get authors"
            authors.value =
                dbHelper.getDao(CalibreAuthor::class.java).queryForAll()
            val authors: Map<Int, CalibreAuthor>? = authors.value?.associate {
                it.id to it as CalibreAuthor
            }

            errorMessage = "cannot get custom columns"
            getCustomColumns()

            val custom_column_entries: List<CustomColumnEntry>? =
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
                /*CustomColumns[customColumnEntry.id] = Pair(customColumnEntry, listCustomColumn)*/
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
                    val book = bookMap?.get(bookCustomColumnLink.book)
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


            errorMessage = "cannot get languages"
            val languagesMap: Map<Int, CalibreLanguage>? =
                dbHelper.getDao(CalibreLanguage::class.java).queryForAll()?.associate {
                    it.id to it as CalibreLanguage
                }

            errorMessage = "cannot book authors links"

            dbHelper.getDao(BooksAuthorsLink::class.java).queryForAll().forEach {
                authors?.get(it.author)?.apply {
                    bookMap?.get(it.book)?.authors?.add(this)
                }
                bookMap?.get(it.book)?.apply {
                    authors?.get(it.author)?.books?.add(this)
                }
            }

            errorMessage = "cannot comments"
            dbHelper.getDao(CalibreComment::class.java).queryForAll().forEach {
                bookMap?.get(it.book)?.apply {
                    comment = it.text.toString()
                }
            }

            dbHelper.getDao(CalibreBooksLanguagesLink::class.java).queryForAll()
                .forEach { languageLink ->
                    bookMap?.get(languageLink.book)?.apply {
                        if (languagesMap?.get(languageLink.lang_code)?.lang_code != null) {
                            languages.add(languagesMap.get(languageLink.lang_code)?.lang_code!!)
                        }
                    }
                }
        } catch (e: Exception) {
            throw Exception(errorMessage, e)
        }
    }

    private fun getCustomColumns() {
        val customColumns = tables.filter { it.startsWith("custom_column_") }
        customColumns.forEach { getCustomColumn(it) }

    }

    private fun getCustomColumn(table: String) {
        val TAG = "boxxx [getCustom]"
        fun log(info: String, list: List<Array<String>>) {
            Log.d(
                TAG, "$table $info > ${
                    list.map {
                        "[" + it.joinToString() + "]"
                    }
                }"
            )
        }

        fun List<Array<String>>.find(field: String): Int {
            return find {
                it[1] == field
            }?.get(0)?.toInt() ?: 0
        }
        try {
            val table_info: List<Array<String>> =
                dao.queryRaw("PRAGMA table_info($table)").results.toList()
            log("table info", table_info)
            val entries: List<Array<String>> = dao.queryRaw("select * from $table").results.toList()
            val bookLinks = if (table_info.size > 2) {
                // table_info = [[0, id, INTEGER, 0, null, 1], [1, book, INTEGER, 0, null, 0], [2, value, BOOL, 1, null, 0]]
                val idIndex = table_info.find("id")
                val bookIndex = table_info.find("book")
                val valueIndex = table_info.find("value")
                entries.map {
                    BooksLink(
                        id = it[idIndex].toInt(),
                        book = it[bookIndex].toInt(),
                        value = it[valueIndex]
                    )
                }
            } else if (table_info.size == 2) {
                // entries is list of possible values
                val entryMap: Map<Int, String> = entries.associate {
                    it[0].toInt() to it[1]
                }

                // we must get book_custom_column_I_link: [id, book, value] where value is an index to entry
                val link_table = "books_${table}_link"
                val link_table_info =
                    dao.queryRaw("PRAGMA table_info($link_table)").results.toList()
                log("link info", link_table_info)

                val links = dao.queryRaw("select * from $link_table").results.toList()
                log("links", links)
                val idIndex = link_table_info.find("id")
                val bookIndex = link_table_info.find("book")
                val valueIndex = link_table_info.find("value")
                links.map {
                    BooksLink(
                        id = it[idIndex].toInt(),
                        book = it[bookIndex].toInt(),
                        value = entryMap[it[valueIndex].toInt()] ?: ""
                    )
                }
            } else {
                listOf()
            }
            log("entries", entries)
            Log.d(TAG, "$table > $bookLinks")
        } catch (e: Exception) {
            Log.e("boxxx [custom]", e.message.toString())
        }


    }
}
