package box.example.showcase.ui.pages.bored

import android.util.Log
import box.example.showcase.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class Activity(
    val activity: String,
    val type: String,
    val participants: Long,
    val price: Double,
    val link: String,
    val key: String,
    val accessibility: Double
)

interface BoredApi {
    @GET("api/activity")
    suspend fun getActivity(): Response<Activity>
}

suspend fun nextActivity(): Activity? {
    val api = ApiHelper.getInstance().create(BoredApi::class.java)
    try {
        val result = api.getActivity()
        Log.v("boxx [Response]: ", result.toString())
        Log.v("boxx [Body]: ", result.body().toString())
        return result.body()
    } catch (e: Exception) {
        Log.e("boxx", e.message.toString())
    }
    return null
}

object ApiHelper {
    private const val baseUrl = "https://www.boredapi.com/"

    fun getInstance(): Retrofit {
        val logging = HttpLoggingInterceptor()
        val httpclient = OkHttpClient.Builder()

        if (false)
            httpclient.addInterceptor(logging)

        if (BuildConfig.DEBUG) {
            // development build
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            logging.setLevel(HttpLoggingInterceptor.Level.NONE)
        } else {
            // production build
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
            logging.setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder().baseUrl(baseUrl)
            .client(httpclient.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}
