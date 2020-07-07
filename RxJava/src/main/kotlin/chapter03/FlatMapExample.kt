package chapter03

import io.reactivex.Observable
import io.reactivex.functions.Function

class FlatMapExample {
    fun marbleDiagram() {
        val getDoubleDiamonds = Function<String, Observable<String>> { ball -> Observable.just("$ball◇", "$ball◇") }

        val balls = arrayOf("1", "3", "5")
        val source = Observable.fromArray(*balls)
            .flatMap(getDoubleDiamonds)
        source.subscribe { data -> println(data) }
    }

    fun flatMapLamda() {
        val balls = arrayOf("1", "3", "5")
        val source = Observable.fromArray(*balls)
            .flatMap { ball -> Observable.just("$ball◇", "$ball◇") }
        source.subscribe { data -> println(data) }
    }
}

fun main() {
    val demo = FlatMapExample()
    demo.marbleDiagram()
    demo.flatMapLamda()
}