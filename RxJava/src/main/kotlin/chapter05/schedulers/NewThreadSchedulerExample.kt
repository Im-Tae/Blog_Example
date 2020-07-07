package chapter05.schedulers

import common.CommonUtils
import common.Log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class NewThreadSchedulerExample {
    fun emit() {
        val orgs = arrayOf("1", "3", "5")

        Observable.fromArray(*orgs)
            .doOnNext { data -> Log.it("Original data : $data") }
            .map { data -> "<<$data>>" }
            .subscribeOn(Schedulers.newThread())
            .subscribe { data -> Log.it(data) }
        CommonUtils.sleep(500)

        Observable.fromArray(*orgs)
            .doOnNext { data -> Log.it("Original data : $data") }
            .map { data -> "##$data##" }
            .subscribeOn(Schedulers.newThread())
            .subscribe { data -> Log.it(data) }
        CommonUtils.sleep(500)

    }
}

fun main() {
    val demo = NewThreadSchedulerExample()
    demo.emit()
}