package chapter05.schedulers

import common.CommonUtils
import common.Log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class TrampolineSchedulerExample {
    fun emit() {
        val orgs = arrayOf("1", "3", "5")

        val source = Observable.fromArray(*orgs)

        // 구독 #1
        source.subscribeOn(Schedulers.trampoline())
            .map { data -> "<<$data>>" }
            .subscribe { data -> Log.it(data) }

        // 구독 #2
        source.subscribeOn(Schedulers.trampoline())
            .map { data -> "##$data##" }
            .subscribe { data -> Log.it(data) }
        CommonUtils.sleep(500)
    }
}

fun main() {
    val demo = TrampolineSchedulerExample()
    demo.emit()
}