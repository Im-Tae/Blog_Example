package chapter03

import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class ReduceExample {
    fun marbleDiagram() {
        val balls = arrayOf("1", "3", "5")
        val source = Observable.fromArray(*balls)
            .reduce{ ball1, ball2 -> "$ball2($ball1)"}
        source.subscribe { data -> println(data) }
    }

    fun biFunction() {
        val mergeBalls = BiFunction<String, String, String> { ball1, ball2 -> "$ball2($ball1)" }
        val balls = arrayOf("1", "3", "5")
        val source = Observable.fromArray(*balls)
            .reduce(mergeBalls)
        source.subscribe { data -> println(data) }
    }
}

fun main() {
    val demo = ReduceExample()
    demo.marbleDiagram()
    demo.biFunction()
}