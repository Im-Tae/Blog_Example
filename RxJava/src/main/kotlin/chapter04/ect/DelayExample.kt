package chapter04.ect

import common.CommonUtils
import common.Log
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class DelayExample {
    fun marbleDiagram() {
        val data = arrayOf("1", "7", "2", "3", "4")

        val source = Observable.fromArray(*data)
            .delay(100, TimeUnit.MILLISECONDS)
        source.subscribe { value -> Log.it(value) }
        CommonUtils.sleep(1000)
    }
}

fun main() {
    val demo = DelayExample()
    demo.marbleDiagram()
}