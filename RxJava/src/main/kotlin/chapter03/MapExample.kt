package chapter03

import io.reactivex.Observable
import io.reactivex.functions.Function

class MapExample {
    fun marbleDiagram() {
        val balls = arrayOf("1", "2", "3", "5")

        val source = Observable.fromArray(*balls)
            .map { ball -> "$ball◇" }
        source.subscribe { data -> println(data) }
    }

    fun mapFunction() {
        val getDiamond = Function<String, String> { ball -> "$ball◇" }

        val balls = arrayOf("1", "2", "3", "5")
        val source = Observable.fromArray(*balls)
            .map(getDiamond)
        source.subscribe { data -> println(data) }
    }
}

fun main() {
    val demo = MapExample()
    demo.marbleDiagram()
    demo.mapFunction()
}