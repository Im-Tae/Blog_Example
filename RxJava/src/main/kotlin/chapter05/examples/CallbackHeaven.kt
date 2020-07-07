package chapter05.examples

import common.CommonUtils
import common.Log
import common.OkHttpHelper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class CallbackHeaven {

    private val FIRST_URL = "https://api.github.com/zen"
    private val SECOND_URL = "${CommonUtils.GITHUB_ROOT}/samples/callback_heaven.md"

    fun usingConcat() {
        CommonUtils.start()

        val source = Observable.just(FIRST_URL)
            .subscribeOn(Schedulers.io())
            .map(OkHttpHelper()::get)
            .concatWith(
                Observable.just(SECOND_URL)
                    .map(OkHttpHelper()::get)
            )

        source.subscribe { data -> Log.it(data) }
        CommonUtils.sleep(5000)
    }
}

fun main() {
    val demo = CallbackHeaven()
    demo.usingConcat()
}