package chapter07.flowcontrol

import common.CommonUtils
import common.Log
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

class SampleExample {

    fun marbleDiagram() {
        val data = arrayOf("1", "7", "2", "3", "6")

        CommonUtils.start()

        // 100ms 간격으로 4개 발행
        val earlySource = Observable.fromArray(*data)
            .take(4)
            .zipWith(Observable.interval(100, TimeUnit.MILLISECONDS), BiFunction{ a : String, _ : Any  -> a })

        // 마지막 데이터는 300ms 후에 발행
        val lateSource = Observable.just(data[4])
            .zipWith(Observable.interval(300, TimeUnit.MILLISECONDS), BiFunction{ a : String, _ : Any -> a })

        // 2개의 Observable을 결합하고 300ms로 샘플링
        val source = Observable.concat(earlySource, lateSource)
            .sample(300, TimeUnit.MILLISECONDS)

        source.subscribe { value -> Log.it(value) }

        CommonUtils.sleep(1000)
    }

    fun emitLast() {
        val data = arrayOf("1", "7", "2", "3", "6")

        CommonUtils.start()

        val earlySource = Observable.fromArray(*data)
            .take(4)
            .zipWith(Observable.interval(100, TimeUnit.MILLISECONDS), BiFunction{ a : String, _ : Any  -> a })

        val lateSource = Observable.just(data[4])
            .zipWith(Observable.interval(300, TimeUnit.MILLISECONDS), BiFunction{ a : String, _ : Any -> a })

        val source = Observable.concat(earlySource, lateSource)
            .sample(300, TimeUnit.MILLISECONDS, true)

        source.subscribe { value -> Log.it(value) }

        CommonUtils.sleep(1000)
    }
}

fun main() {
    val demo = SampleExample()
    demo.marbleDiagram()
    demo.emitLast()
}