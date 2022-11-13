package box.example.showcase.applib.books

import android.util.Log
import retrofit2.Response

class OpenLibraryBookService : BookService {
    override suspend fun getBooks(query: String): Response<BookList> {
        Log.d("boxx [OpenLibraryBookService]: ", query)
        val api = OpenLibraryApiHelper.getInstance()
        val result: Response<BookList> = api.getBooks(/*"lord+of+the"*/)
        Log.d("boxx [Response]: ", result.toString())
        Log.d("boxx [Body]: ", result.body().toString())

        return api.getBooks(/*"lord+of+the"*/)
    }
}

