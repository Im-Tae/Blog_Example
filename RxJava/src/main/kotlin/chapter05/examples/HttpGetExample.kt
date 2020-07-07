package chapter05.examples

import common.Log
import okhttp3.*
import java.io.IOException

class HttpGetExample {
    private val client : OkHttpClient = OkHttpClient()
    private val URL_README = "https://raw.githubusercontent.com/Im-Tae/RxJava2_Study/master/samples/README_TEST.md"

    fun run() {
        val request = Request.Builder()
            .url(URL_README)
            .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                Log.it(response.body!!.string())
            }
        })
    }
}

fun main() {
    val demo = HttpGetExample()
    demo.run()
}