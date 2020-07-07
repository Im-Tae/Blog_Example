package chapter03

import io.reactivex.Observable
import io.reactivex.Single

class FilterExample {
    fun marbleDiagram() {
        val objs = arrayOf("1 CIRCLE", "2 DIAMOND", "3 TRIANGLE", "4 DIAMOND", "5 CIRCLE", "6 HEXAGON")
        val source = Observable.fromArray(*objs)
            .filter { obj -> obj.endsWith("CIRCLE") }
        source.subscribe { data -> println(data) }
    }

    fun evenNumbers() {
        val data = arrayOf(100, 34, 27, 99, 50)
        val source = Observable.fromArray(*data)
            .filter { number -> number % 2 == 0 }
        source.subscribe { data -> println(data) }
    }

    fun otherFilters() {
        val numbers = arrayOf(100, 200, 300, 400, 500)
        var single: Single<Int>
        var source: Observable<Int>

        // 1. first
        single = Observable.fromArray(*numbers).first(-1)
        single.subscribe { data -> println("first() value = $data") }

        // 2. last
        single = Observable.fromArray(*numbers).last(999)
        single.subscribe { data -> println("last() value = $data") }

        // 3. take(N)
        source = Observable.fromArray(*numbers).take(3)
        source.subscribe { data -> println("takeLast(3) value = $data") }

        // 4. takeLast(N)
        source = Observable.fromArray(*numbers).takeLast(3)
        source.subscribe { data -> println("takeLast(3) value = $data") }

        // 5. skip(N)
        source = Observable.fromArray(*numbers).skip(2)
        source.subscribe { data -> println("skip(2) value = $data") }

        // 6. skipLast(N)
        source = Observable.fromArray(*numbers).skipLast(2)
        source.subscribe { data -> println("skipLast(2) value = $data") }
    }
}

fun main() {
    val demo = FilterExample()
    demo.marbleDiagram()
    demo.evenNumbers()
    demo.otherFilters()
}