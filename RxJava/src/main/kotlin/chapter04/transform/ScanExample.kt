package chapter04.transform

import common.Log
import io.reactivex.Observable

class ScanExample {
    fun marbleDiagram() {
        val balls = arrayOf("1", "3", "5")
        val source = Observable.fromArray(*balls)
            .scan { ball1, ball2 -> "$ball2($ball1)" }
        source.subscribe { data -> Log.it(data) }
    }
}

fun main() {
    val demo = ScanExample()
    demo.marbleDiagram()
}