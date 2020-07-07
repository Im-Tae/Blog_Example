package chapter02

import io.reactivex.subjects.BehaviorSubject

class BehaviorSubjectExample {
    fun marbleDiagram() {
        val subject = BehaviorSubject.createDefault("6")
        subject.subscribe { data -> println("Subscriber #1 => $data") }
        subject.onNext("1")
        subject.onNext("3")
        subject.subscribe { data -> println("Subscriber #2 => $data") }
        subject.onNext("5")
        subject.onComplete()
    }
}

fun main() {
    val demo = BehaviorSubjectExample()
    demo.marbleDiagram()
}