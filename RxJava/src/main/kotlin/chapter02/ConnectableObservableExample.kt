package chapter02

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

// ConnectableObservableExample.kt

class ConnectableObservableExample {
    fun emit() {
        val balls = Observable.just(1, 3, 5)

        val source = balls.publish()
        source.subscribe { data -> println("Subscriber #1 => $data") }
        source.subscribe { data -> println("Subscriber #2 => $data") }
        source.connect()

        source.subscribe { data -> println("Subscriber #3 => $data") }
    }

    fun marbleDiagram() {
        val dt = arrayOf("1", "3", "5")

        val balls = Observable.interval(100, TimeUnit.MILLISECONDS)
            .map { data -> data.toInt() }
            .map { i -> dt[i] }
            .take(dt.size.toLong())

        val source = balls.publish()
        source.subscribe { data -> println("Subscriber #1 => $data") }
        source.subscribe { data -> println("Subscriber #2 => $data") }
        source.connect()

        Thread.sleep(300)

        source.subscribe { data -> println("Subscriber #3 => $data") }

        Thread.sleep(300)
    }
}

fun main() {
    val demo = ConnectableObservableExample()
    demo.emit()
    demo.marbleDiagram()
}