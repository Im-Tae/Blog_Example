package chapter02

import io.reactivex.Observable

class ObservableFromArray {
    fun integerArray() {
        val arr = arrayOf(100, 200, 300)

        val source = Observable.fromArray(*arr)
        source.subscribe(System.out::println)
    }
}

fun main() {
    val demo = ObservableFromArray()
    demo.integerArray()
}