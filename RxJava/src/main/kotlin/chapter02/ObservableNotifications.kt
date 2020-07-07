package chapter02

import io.reactivex.Observable

class ObservableNotifications {
    fun emit() {
        val source = Observable.just("RED", "GREEN", "YELLOW")

        val d = source.subscribe(
            { v -> println("onNext() : value : $v") },
            { err -> println("onError() : err : ${err.message}") },
            { println("onComplete()") }
        )

        println("isDisposed() : " + d.isDisposed)
    }
}


fun main() {
    val demo = ObservableNotifications()
    demo.emit()
}