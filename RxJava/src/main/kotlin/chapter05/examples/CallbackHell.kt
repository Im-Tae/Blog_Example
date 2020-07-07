package chapter05.examples

import common.CommonUtils.Companion.GITHUB_ROOT
import common.Log
import okhttp3.*
import java.io.IOException

class CallbackHell {
    private val FIRST_URL = "https://api.github.com/zen"
    private val SECOND_URL = "$GITHUB_ROOT/samples/callback_hell.md"

    private val client : OkHttpClient = OkHttpClient()

    private val onSuccess = object : Callback {

        override fun onFailure(call: Call, e: IOException) = e.printStackTrace()
        override fun onResponse(call: Call, response: Response) = Log.it(response.body!!.string())
    }

    fun run() {
        val request = Request.Builder()
            .url(FIRST_URL)
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) = e.printStackTrace()
            override fun onResponse(call: Call, response: Response) {
                Log.it(response.body!!.string())

                val request = Request.Builder()
                    .url(SECOND_URL)
                    .build()
                client.newCall(request).enqueue(onSuccess)
            }
        })
    }
}

fun main() {
    val demo = CallbackHell()
    demo.run()
}