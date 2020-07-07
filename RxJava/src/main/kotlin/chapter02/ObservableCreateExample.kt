package chapter02

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.functions.Consumer

class ObservableCreateExample {
    fun emit() {
        val source = Observable.create { emitter: ObservableEmitter<Int> ->
            emitter.onNext(100)
            emitter.onNext(200)
            emitter.onNext(300)
            emitter.onComplete()
        }
        source.subscribe(System.out::println)
    }

    fun noSubscribed() {
        val source = Observable.create { emitter: ObservableEmitter<Int> ->
            emitter.onNext(100)
            emitter.onNext(200)
            emitter.onNext(300)
            emitter.onComplete()
        }
    }

    fun subscribeLamda() {
        val source = Observable.create { emitter: ObservableEmitter<Int> ->
            emitter.onNext(100)
            emitter.onNext(200)
            emitter.onNext(300)
            emitter.onComplete()
        }
        source.subscribe { data -> println("Result : $data") }
    }

    fun subscribeAnonymously() {
        val source = Observable.create { emitter: ObservableEmitter<Int> ->
            emitter.onNext(100)
            emitter.onNext(200)
            emitter.onNext(300)
            emitter.onComplete()
        }

        source.subscribe(Consumer<Int> { data -> println("Result : $data") })
    }
}

fun main() {
    val demo = ObservableCreateExample()
    demo.emit()
    demo.noSubscribed()
    demo.subscribeLamda()
    demo.subscribeAnonymously()
}