package chapter04.combine

import common.CommonUtils
import common.Log
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class ConcatExample {
    fun marbleDiagram() {
        val onCompleteAction = { Log.d("onComplete()") }

        val data1 = arrayOf("1", "3", "5")
        val data2 = arrayOf("2", "4", "6")

        val source1 = Observable.fromArray(*data1)
            .doOnComplete(onCompleteAction)

        val source2 = Observable.interval(100, TimeUnit.MILLISECONDS)
            .map { data -> data.toInt() }
            .map { idx -> data2[idx] }
            .take(data2.size.toLong())
            .doOnComplete(onCompleteAction)

        val source = Observable.concat(source1, source2)
            .doOnComplete(onCompleteAction)

        source.subscribe { data -> Log.it(data) }
        CommonUtils.sleep(1000)
    }
}

fun main() {
    val demo = ConcatExample()
    demo.marbleDiagram()
}