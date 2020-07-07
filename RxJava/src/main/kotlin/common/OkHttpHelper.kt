package common

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException

class OkHttpHelper {
    private val client: OkHttpClient = OkHttpClient()

    @Throws(IOException::class)
    fun get(url: String): String {
        val request: Request = Request.Builder()
            .url(url)
            .build()

        val response: Response = client.newCall(request).execute()
        return response.body!!.string()
    }

    @Throws(IOException::class)
    fun getWithLog(url: String): String {
        Log.d("OkHttp call URL = $url")
        return get(url)
    }
}