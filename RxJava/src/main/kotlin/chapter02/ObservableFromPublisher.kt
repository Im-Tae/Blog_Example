package chapter02

import io.reactivex.Observable
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber


class ObservableFromPublisher {
    fun emit() {
        val publisher = Publisher { s: Subscriber<in String?> ->
            s.onNext("Hello Observable.fromPublisher()")
            s.onComplete()
        }

        val source = Observable.fromPublisher(publisher)
        source.subscribe(System.out::println)
    }
}

fun main() {
    val demo = ObservableFromPublisher()
    demo.emit()
}