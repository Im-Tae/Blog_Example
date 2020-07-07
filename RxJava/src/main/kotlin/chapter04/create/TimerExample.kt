package chapter04.create

import common.CommonUtils
import common.Log
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

class TimerExample {
    fun showTime() {
        CommonUtils.start()
        val source = Observable.timer(500, TimeUnit.MILLISECONDS)
            .map { SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(System.currentTimeMillis()) }
        source.subscribe { data -> Log.it(data) }
        CommonUtils.sleep(1000)
    }
}

fun main() {
    val demo = TimerExample()
    demo.showTime()
}