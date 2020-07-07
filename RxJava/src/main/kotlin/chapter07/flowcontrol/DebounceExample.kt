package chapter07.flowcontrol

import common.CommonUtils
import common.Log
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class DebounceExample {

    fun marbleDiagram() {
        val data = arrayOf("1", "2", "3", "5")

        val source = Observable.concat(
            Observable.timer(100, TimeUnit.MILLISECONDS).map { _ -> data[0] },
            Observable.timer(300, TimeUnit.MILLISECONDS).map { _ -> data[1] },
            Observable.timer(100, TimeUnit.MILLISECONDS).map { _ -> data[2] },
            Observable.timer(300, TimeUnit.MILLISECONDS).map { _ -> data[3] }
        ).debounce(200, TimeUnit.MILLISECONDS)

        source.subscribe { value -> Log.it(value) }

        CommonUtils.sleep(1000)
    }
}


fun main() {
    val demo = DebounceExample()
    demo.marbleDiagram()
}