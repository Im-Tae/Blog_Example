package chapter04.create

import common.CommonUtils
import common.Log
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class IntervalRangeExample {
    fun printNumbers() {
        val source = Observable.intervalRange(1, 5, 100, 100, TimeUnit.MILLISECONDS)
        source.subscribe { data -> Log.it(data) }
        CommonUtils.sleep(1000)
    }

    fun makeWithInterval() {
        val source = Observable.interval(100, TimeUnit.MILLISECONDS)
            .map { data -> data + 1 }
            .take(5)
        source.subscribe { data -> Log.it(data) }
        CommonUtils.sleep(1000)
    }
}

fun main() {
    val demo = IntervalRangeExample()
    demo.printNumbers()
    demo.makeWithInterval()
}