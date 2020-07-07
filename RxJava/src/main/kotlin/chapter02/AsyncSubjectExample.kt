package chapter02

import io.reactivex.Observable
import io.reactivex.subjects.AsyncSubject

class AsyncSubjectExample {
    fun marbleDiagram() {
        val subject = AsyncSubject.create<String>()
        subject.subscribe { data -> println("Subscriber #1 => $data") }
        subject.onNext("1")
        subject.onNext("3")
        subject.subscribe { data -> println("Subscriber #1 => $data") }
        subject.onNext("5")
        subject.onComplete()
    }

    fun asSubscriber() {
        val temperature = arrayOf(10.1f, 13.4f, 125f)
        val source = Observable.fromArray(*temperature)

        val subject = AsyncSubject.create<Float>()
        subject.subscribe { data -> println("Subscriber #1 => $data") }

        source.subscribe(subject)
    }

    fun afterComplete() {
        val subject = AsyncSubject.create<Int>()
        subject.onNext(10)
        subject.onNext(11)
        subject.subscribe { data -> println("Subscriber #1 => $data") }
        subject.onNext(12)
        subject.onComplete()
        subject.onNext(13)
        subject.subscribe { data -> println("Subscriber #2 => $data") }
        subject.subscribe { data -> println("Subscriber #3 => $data") }
    }
}

fun main() {
    val demo = AsyncSubjectExample()
    demo.marbleDiagram()
    demo.asSubscriber()
    demo.afterComplete()
}