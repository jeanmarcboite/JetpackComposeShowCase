package box.example.showcase.applib.books

import box.example.showcase.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApiQ {
    @GET("search.json")
    fun getBooks(@Query("title") query: String): Result<String>
}

interface BookApi {
    @GET("search.json?title=lord")
    suspend fun getBooks(): Response<BookList>
}

interface BookService {
    suspend fun getBooks(query: String): Response<BookList>
}


object OpenLibraryApiHelper {
    private const val baseUrl = "https://openlibrary.org/"

    private var instance: BookApi? = null

    fun getInstance(): BookApi {
        if (instance == null)
            instance = getNewInstance()
        return instance!!
    }

    private fun getNewInstance(): BookApi {
        val logging = HttpLoggingInterceptor()
        val httpclient = OkHttpClient.Builder()
        httpclient.addInterceptor(logging)

        if (BuildConfig.DEBUG) {
            // development build
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            // production build
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        }

        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder().baseUrl(baseUrl)
            .client(httpclient.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(BookApi::class.java)
    }
}