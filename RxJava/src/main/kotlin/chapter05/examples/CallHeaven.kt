package chapter05.examples

import common.CommonUtils
import common.Log
import common.OkHttpHelper
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class CallHeaven {

    private val FIRST_URL = "https://api.github.com/zen"
    private val SECOND_URL = "${CommonUtils.GITHUB_ROOT}/samples/callback_heaven.md"

    fun usingZip() {
        CommonUtils.start()

        val first = Observable.just(FIRST_URL)
            .subscribeOn(Schedulers.io())
            .map(OkHttpHelper()::get)

        val second = Observable.just(SECOND_URL)
            .subscribeOn(Schedulers.io())
            .map(OkHttpHelper()::get)

        Observable.zip(
            first,
            second,
            BiFunction { a: String, b: String -> "\n>> $a\n>> $b" }
        )
            .subscribe { data -> Log.it(data) }

        CommonUtils.sleep(5000)
    }
}

fun main() {
    val demo = CallHeaven()
    demo.usingZip()
}