package chapter04.combine

import common.CommonUtils
import common.Log
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class MergeExample {
    fun marbleDiagram() {
        val data1 = arrayOf("1", "3")
        val data2 = arrayOf("2", "4", "6")

        val source1 = Observable.interval(0, 100, TimeUnit.MILLISECONDS)
            .map { data -> data.toInt() }
            .map { idx -> data1[idx] }
            .take(data1.size.toLong())

        val source2 = Observable.interval(50, TimeUnit.MILLISECONDS)
            .map { data -> data.toInt() }
            .map { idx -> data2[idx] }
            .take(data2.size.toLong())

        val source = Observable.merge(source1, source2)

        source.subscribe { data -> Log.it(data) }
        CommonUtils.sleep(1000)
    }
}

fun main() {
    val demo = MergeExample()
    demo.marbleDiagram()
}