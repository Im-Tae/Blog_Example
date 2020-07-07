package chapter05.schedulers

import common.CommonUtils
import common.Log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

class ExecutorSchedulerExample {
    fun emit() {
        val THREAD_NUM = 10

        val data = arrayOf("1", "3", "5")

        val source = Observable.fromArray(*data)
        val executor = Executors.newFixedThreadPool(THREAD_NUM)

        source.subscribeOn(Schedulers.from(executor))
            .subscribe { value -> Log.it(value) }

        source.subscribeOn(Schedulers.from(executor))
            .subscribe { value -> Log.it(value) }
        CommonUtils.sleep(500)
    }
}

fun main() {
    val demo = ExecutorSchedulerExample()
    demo.emit()
}