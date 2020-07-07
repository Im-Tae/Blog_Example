package chapter04.create

import common.Log
import io.reactivex.Observable

class RangeExample {
    fun forLoop() {
        val source = Observable.range(1, 10)
            .filter { number -> number % 2 == 0 }
        source.subscribe { data -> Log.it(data) }
    }
}

fun main() {
    val demo = RangeExample()
    demo.forLoop()
}