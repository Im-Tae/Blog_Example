package chapter04.create

import common.CommonUtils
import common.Log
import common.OkHttpHelper
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class RepeatExample {
    fun marbleDiagram() {
        val balls = arrayOf("1", "3", "5")
        val source = Observable.fromArray(*balls)
            .repeat(3)
        source.doOnComplete{ println("onComplete") }
            .subscribe { data -> Log.it(data) }
    }

    fun heartbeatV1() {
        CommonUtils.start()
        val serverUrl = "https://api.github.com/zen"

        // 2초 간격으로 서버에 ping 보내기
        Observable.timer(2, TimeUnit.SECONDS)
            .map { OkHttpHelper().get(serverUrl) }
            .repeat()
            .subscribe { data -> Log.it("Ping Result : $data") }
        CommonUtils.sleep(10000)
    }

    fun heartbeatV2() {
        CommonUtils.start()
        val serverUrl = "https://api.github.com/zen"

        // 2초 간격으로 서버에 ping 보내기
        Observable.interval(2, TimeUnit.SECONDS)
            .map { OkHttpHelper().get(serverUrl) }
            .take(3)
            .subscribe { data -> Log.it("Ping Result : $data") }
        CommonUtils.sleep(10000)
    }
}

fun main() {
    val demo = RepeatExample()
    demo.marbleDiagram()
    demo.heartbeatV1()
    demo.heartbeatV2()
}
