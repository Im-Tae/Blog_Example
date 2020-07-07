package chapter05

import common.Log
import io.reactivex.Observable

class HelloRxJava2V2 {
    fun emit() {
        Observable.just("Hello", "RxJava2!!")
            .subscribe { data -> Log.it(data) }
    }
}

fun main() {
    val demo = HelloRxJava2V2()
    demo.emit()
}