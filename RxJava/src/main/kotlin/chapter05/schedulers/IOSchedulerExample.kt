package chapter05.schedulers

import common.CommonUtils
import common.Log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.File

class IOSchedulerExample {
    fun emit() {
        val root = "c:\\"
        val files = File(root).listFiles()

        val source = Observable.fromArray(*files)
            .filter { f -> !f.isDirectory }
            .map { f -> f.absolutePath }
            .subscribeOn(Schedulers.io())

        source.subscribe { data -> Log.it(data) }
        CommonUtils.sleep(500)
    }
}

fun main() {
    val demo = IOSchedulerExample()
    demo.emit()
}