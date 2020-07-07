package chapter04.conditional

import common.CommonUtils
import common.Log
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

class TakeUntilExample {
    fun marbleDiagram() {
        val data = arrayOf("1", "2", "3", "4", "5", "6")

        val source = Observable.fromArray(*data)
            .zipWith(
                Observable.interval(100, TimeUnit.MILLISECONDS),
                BiFunction { value: String, _: Long -> value }
            )
            .takeUntil(Observable.timer(500, TimeUnit.MILLISECONDS))

        source.subscribe { value -> Log.it(value) }
        CommonUtils.sleep(1000)
    }
}

fun main() {
    val demo = TakeUntilExample()
    demo.marbleDiagram()
}