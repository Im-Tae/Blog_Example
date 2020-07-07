package chapter04.combine

import common.CommonUtils
import common.Log
import common.Shape
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

class CombineLatestExample {
    fun marbleDiagram() {
        val data1 = arrayOf("6", "7", "4", "2")
        val data2 = arrayOf("DIAMOND", "STAR", "PENTAGON")

        val source = Observable.combineLatest(
            Observable.fromArray(*data1)
                .zipWith(
                    Observable.interval(100, TimeUnit.MILLISECONDS),
                    BiFunction { shape: String, _: Long -> Shape().getColor(shape) }
                ),
            Observable.fromArray(*data2)
                .zipWith(
                    Observable.interval(150, 200, TimeUnit.MILLISECONDS),
                    BiFunction { shape: String, _: Long -> Shape().getSuffix(shape) }
                ),
            BiFunction { v1: String, v2: String -> v1 + v2 }
        )

        source.subscribe { data -> Log.it(data) }
        CommonUtils.sleep(1000)
    }
}

fun main() {
    val demo = CombineLatestExample()
    demo.marbleDiagram()
}