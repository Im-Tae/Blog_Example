package chapter04.transform

import common.CommonUtils
import common.Log
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class SwitchMapExample {
    fun marbleDiagram() {
        CommonUtils.start()

        val balls = arrayOf("1", "3", "5")
        val source = Observable.interval(100, TimeUnit.MILLISECONDS)
            .map { idx -> balls[idx.toInt()] }
            .take(balls.size.toLong())
            .switchMap { ball ->
                Observable.interval(200, TimeUnit.MILLISECONDS)
                    .map { "$ball ◇" }
                    .take(2)
            }

        source.subscribe { data -> Log.it(data) }
        CommonUtils.sleep(2000)
    }

    fun usingDoOnNext() {
        CommonUtils.start()

        val balls = arrayOf("1", "3", "5")
        val source = Observable.interval(100, TimeUnit.MILLISECONDS)
            .map { idx -> balls[idx.toInt()] }
            .take(balls.size.toLong())
            .doOnNext { data -> Log.it(data) }
            .switchMap { ball ->
                Observable.interval(200, TimeUnit.MILLISECONDS)
                    .map { "$ball ◇" }
                    .take(2)
            }

        source.subscribe { data -> Log.it(data) }
        CommonUtils.sleep(2000)
    }
}

fun main() {
    val demo = SwitchMapExample()
    demo.marbleDiagram()
    demo.usingDoOnNext()
}