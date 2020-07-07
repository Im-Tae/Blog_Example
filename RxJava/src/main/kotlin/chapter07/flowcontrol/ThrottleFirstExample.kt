package chapter07.flowcontrol

import common.CommonUtils
import common.Log
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

class ThrottleFirstExample {

    fun marbleDiagram() {
        val data = arrayOf("1", "2", "3", "4", "5", "6")

        CommonUtils.start()

        val earlySource = Observable.just(data[0])
            .zipWith(Observable.interval(100, TimeUnit.MILLISECONDS), BiFunction{ a : String, _ : Any  -> a })

        val middleSource = Observable.just(data[1])
            .zipWith(Observable.timer(300, TimeUnit.MILLISECONDS), BiFunction{ a : String, _ : Any -> a })

        val lateSource = Observable.just(data[2], data[3], data[4], data[5])
            .zipWith(Observable.interval(100, TimeUnit.MILLISECONDS), BiFunction{ a : String, _ : Any -> a })
            .doOnNext { log -> Log.d(log) }

        // 200ms 간격으로 throttleFirst 실행
        val source = Observable.concat(earlySource, middleSource, lateSource)
            .throttleFirst(200, TimeUnit.MILLISECONDS)

        source.subscribe { value -> Log.it(value) }

        CommonUtils.sleep(1000)
    }
}


fun main() {
    val demo = ThrottleFirstExample()
    demo.marbleDiagram()
}