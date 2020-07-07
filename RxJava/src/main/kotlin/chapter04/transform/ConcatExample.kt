package chapter04.transform

import common.CommonUtils
import common.Log
import io.reactivex.Observable
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit


class ConcatExample {
    fun marbleDiagram() {
        CommonUtils.start()

        val balls = arrayOf("1", "3", "5")
        val source = Observable.interval(100, TimeUnit.MILLISECONDS)
            .map { idx -> balls[idx.toInt()] }
            .take(balls.size.toLong())
            .concatMap { ball ->
                Observable.interval(200, TimeUnit.MILLISECONDS)
                    .map { "$ball ◇" }
                    .take(2)
            }

        source.subscribe { data -> Log.it(data) }
        CommonUtils.sleep(2000)
    }

    fun interleaving() {
        CommonUtils.start()

        val balls = arrayOf("1", "3", "5")
        val source = Observable.interval(100, TimeUnit.MILLISECONDS)
            .map { idx -> balls[idx.toInt()] }
            .take(balls.size.toLong())
            .flatMap { ball ->
                Observable.interval(200, TimeUnit.MILLISECONDS)
                    .map { "$ball ◇" }
                    .take(2)
            }

        source.subscribe { data -> Log.it(data) }
        CommonUtils.sleep(2000)
    }
}

fun main() {
    val demo = ConcatExample()
    demo.marbleDiagram()
    demo.interleaving()
}