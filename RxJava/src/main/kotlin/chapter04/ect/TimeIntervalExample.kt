package chapter04.ect

import common.CommonUtils
import common.Log
import io.reactivex.Observable
import kotlin.random.Random

class TimeIntervalExample {
    fun marbleDiagram() {
        val data = arrayOf("1", "3", "7")

        CommonUtils.start()

        val source = Observable.fromArray(*data)
            .delay { item ->
                Thread.sleep(Random.nextLong(100))
                Observable.just(item)
            }
            .timeInterval()

        source.subscribe { value -> Log.it(value) }
        CommonUtils.sleep(1000)
    }
}

fun main() {
    val demo = TimeIntervalExample()
    demo.marbleDiagram()
}