package chapter04.conditional

import common.CommonUtils
import common.Log
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class AmbExample {
    fun marbleDiagram() {
        val data1 = arrayOf("1", "3", "5")
        val data2 = arrayOf("2-R", "4-R")

        val sources = listOf(
            Observable.fromArray(*data1)
                .doOnComplete { Log.d("Observable #1 : onComplete()") },

            Observable.fromArray(*data2)
                .delay(100, TimeUnit.MILLISECONDS)
                .doOnComplete { Log.d("Observable #2 : onComplete()") }
        )

        Observable.amb(sources)
            .doOnComplete { Log.d("Result : onComplete()") }
            .subscribe { data -> Log.it(data) }
        CommonUtils.sleep(1000)
    }
}

fun main()  {
    val demo = AmbExample()
    demo.marbleDiagram()
}