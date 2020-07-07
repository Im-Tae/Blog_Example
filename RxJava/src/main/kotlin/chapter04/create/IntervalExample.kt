package chapter04.create

import common.CommonUtils
import common.Log
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class IntervalExample {
    fun printNumber() {
        CommonUtils.start()
        val source = Observable.interval(100, TimeUnit.MILLISECONDS)
            .map { data -> (data + 1) * 100 }
            .take(5)
        source.subscribe { data -> Log.it(data) }
        CommonUtils.sleep(1000)
    }

    fun nonInitialDelay() {
        CommonUtils.start()
        val source = Observable.interval(0, 100, TimeUnit.MILLISECONDS)
            .map { data -> data + 100 }
            .take(5)
        source.subscribe { data -> Log.it(data) }
        CommonUtils.sleep(1000)
    }
}

fun main() {
    val demo = IntervalExample()
    demo.printNumber()
    demo.nonInitialDelay()
}