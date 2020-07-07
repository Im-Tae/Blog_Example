package chapter02

import io.reactivex.subjects.ReplaySubject

class ReplaySubjectExample {
    fun marbleDiagram() {
        val subject = ReplaySubject.create<String>()
        subject.subscribe { data -> println("Subscriber #1 => $data") }
        subject.onNext("1")
        subject.onNext("3")
        subject.subscribe { data -> println("Subscriber #2 => $data") }
        subject.onNext("5")
        subject.onComplete()
    }
}

fun main() {
    val demo = ReplaySubjectExample()
    demo.marbleDiagram()
}