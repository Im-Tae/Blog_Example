package chapter02

import io.reactivex.Observable

class FirstExample {
    fun emit() {
        Observable.just(1, 2, 3, 4, 5, 6)
            .subscribe(System.out::println)
    }
}

fun main() {
    val demo = FirstExample()
    demo.emit()
}