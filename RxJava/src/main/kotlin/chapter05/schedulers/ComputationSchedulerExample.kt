package chapter05.schedulers

import common.CommonUtils
import common.Log
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ComputationSchedulerExample {
    fun emit() {
        val orgs = arrayOf("1", "3", "5")

        val source = Observable.fromArray(*orgs)
            .zipWith(
                Observable.interval(100, TimeUnit.MILLISECONDS),
                BiFunction { a: String, _: Long -> a }
            )

        // 구독 #1
        source.map { item -> "<<$item>>" }
            .subscribeOn(Schedulers.computation())
            .subscribe { data -> Log.it(data) }

        // 구독 #2
        source.map { item -> "##$item##" }
            .subscribeOn(Schedulers.computation())
            .subscribe { data -> Log.it(data) }
        CommonUtils.sleep(1000)
    }
}

fun main() {
    val demo = ComputationSchedulerExample()
    demo.emit()
}