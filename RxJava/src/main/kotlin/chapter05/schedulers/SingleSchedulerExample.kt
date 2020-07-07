package chapter05.schedulers

import common.CommonUtils
import common.Log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class SingleSchedulerExample {
    fun emit() {
        val numbers = Observable.range(100, 5)
        val chars = Observable.range(0, 5)
            .map(CommonUtils()::numberToAlphabet)

        numbers.subscribeOn(Schedulers.single())
            .subscribe { data -> Log.it(data) }

        chars.subscribeOn(Schedulers.single())
            .subscribe { data -> Log.it(data) }
        CommonUtils.sleep(500)
    }
}

fun main() {
    val demo = SingleSchedulerExample()
    demo.emit()
}