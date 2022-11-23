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

            errorMessage = "cannot get data"
            val ratings: List<CalibreEntity>? =
                dbHelper.getDao(CalibreRating::class.java).queryForAll()
            val publishers: List<CalibreEntity>? =
                dbHelper.getDao(CalibreLibraryId::class.java).queryForAll()
            val entryMap: Map<String, List<CalibreEntity>?> = mapOf(
                "data" to dbHelper.getDao(CalibreData::class.java).queryForAll(),
                "feeds" to dbHelper.getDao(CalibreFeeds::class.java).queryForAll(),
                "identifiers" to dbHelper.getDao(CalibreIdentifiers::class.java).queryForAll(),
                "library_id" to dbHelper.getDao(CalibreLibraryId::class.java).queryForAll(),
                "publishers" to dbHelper.getDao(CalibrePublishers::class.java).queryForAll(),
                "ratings" to dbHelper.getDao(CalibreRating::class.java).queryForAll(),
                "series" to dbHelper.getDao(CalibreSeries::class.java).queryForAll(),
                "tags" to dbHelper.getDao(CalibreTag::class.java).queryForAll(),
            )
            entryMap.forEach { (key, calibreEntities) ->
                calibreEntities?.apply {
                    bookMap?.readBooksData(key, this)
                }
            }

            errorMessage = "cannot get custom columns"

            val custom_column_entries: List<CalibreCustomColumn>? =
                dbHelper.getDao(CalibreCustomColumn::class.java).queryForAll()
            val customColumnMap = custom_column_entries?.associate {
                it.id to it
            }
            getCustomColumns(bookMap, customColumnMap)

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

    private fun Map<Int, CalibreBook>.readBooksData(table: String, entries: List<CalibreEntity>) {
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
        try {
            val entryMap: Map<Int, CalibreEntity> = entries.associate {
                it.id to it
            }
            val link_table = "books_${table}_link"
            val link_table_info =
                dao.queryRaw("PRAGMA table_info($link_table)").results.toList()
            log("link info", link_table_info)

            val links = dao.queryRaw("select * from $link_table").results.toList()
            log("links", links)
            val idIndex = link_table_info.find("id")
            val bookIndex = link_table_info.find("book")
            val valueIndex = 2 // link_table_info.find("value")

            links.forEach {
                val id = it[idIndex].toInt()
                val book: CalibreBook? = this[it[bookIndex].toInt()]
                val value: CalibreEntity? = entryMap[it[valueIndex].toInt()]

                if (value != null)
                    book?.apply {
                        if (columns[table] == null)
                            columns[table] = mutableListOf()
                        columns[table]!!.add(value)
                    }
            }

        } catch (e: Exception) {
            Log.e("boxxx [custom]", e.message.toString())
        }

    }

    private fun getCustomColumns(
        bookMap: Map<Int, CalibreBook>?,
        customColumnMap: Map<Int, CalibreCustomColumn>?
    ) {
        val customColumns = tables.filter { it.startsWith("custom_column_") }
        customColumns.forEach {
            val column =
                customColumnMap?.get(it.split("_").last().toInt()) ?: CalibreCustomColumn(name = it)

            val booksLinks = getCustomColumn(it)

            booksLinks?.forEach { booksLink ->
                val book = bookMap?.get(booksLink.book)
                if (book != null) {
                    if (book.customColumns[column] == null)
                        book.customColumns[column] = mutableListOf()

                    book.customColumns[column]!!.add(booksLink.value)
                }
            }
        }

    }

    private fun List<Array<String>>.find(field: String): Int {
        return find {
            it[1] == field
        }?.get(0)?.toInt() ?: 0
    }

    private fun getCustomColumn(table: String): List<BooksLink>? {
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

        try {
            val table_info: List<Array<String>> =
                dao.queryRaw("PRAGMA table_info($table)").results.toList()
            log("table info", table_info)
            val entries: List<Array<String>> = dao.queryRaw("select * from $table").results.toList()
            val booksLinks = if (table_info.size > 2) {
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
            Log.d(TAG, "$table > $booksLinks")

            return booksLinks
        } catch (e: Exception) {
            Log.e("boxxx [custom]", e.message.toString())
        }

        return null
    }
}
