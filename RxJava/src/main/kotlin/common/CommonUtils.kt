package common

import java.io.IOException
import java.net.InetAddress

class CommonUtils {
    private var ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    fun numberToAlphabet(x: Int) = ALPHABET[x % ALPHABET.length].toString()

    companion object {

        var startTime: Long? = null
        var GITHUB_ROOT = "https://raw.githubusercontent.com/Im-Tae/RxJava2_Study/master"


        fun start() {
            startTime = System.currentTimeMillis()
        }

        fun sleep(millis: Long) {
            try {
                Thread.sleep(millis)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        fun isNetworkAvailable(): Boolean {
            try {
                return InetAddress.getByName("www.google.com").isReachable(1000)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return false
        }
    }
}