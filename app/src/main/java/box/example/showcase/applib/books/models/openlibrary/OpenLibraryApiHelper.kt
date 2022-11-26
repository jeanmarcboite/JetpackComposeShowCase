package box.example.showcase.applib.books.models.openlibrary

import box.example.showcase.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object OpenLibraryApiHelper {
    private const val baseUrl = "https://openlibrary.org/"

    private var instance: OpenLibraryApi? = null

    fun getInstance(): OpenLibraryApi {
        if (instance == null)
            instance = getNewInstance()
        return instance!!
    }

    private fun getNewInstance(): OpenLibraryApi {
        val logging = HttpLoggingInterceptor()
        val httpclient = OkHttpClient.Builder()
        httpclient.addInterceptor(logging)
        httpclient.addNetworkInterceptor(CacheInterceptor())

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
            .build().create(OpenLibraryApi::class.java)
    }
}

// https://amitshekhar.me/blog/caching-with-okhttp-interceptor-and-retrofit
class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val response: okhttp3.Response = chain.proceed(chain.request())
        val cacheControl = CacheControl.Builder()
            .maxAge(10, TimeUnit.DAYS)
            .build()
        return response.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build()
    }
}