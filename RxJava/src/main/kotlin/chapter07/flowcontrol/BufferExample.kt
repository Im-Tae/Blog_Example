package chapter07.flowcontrol

import common.CommonUtils
import common.Log
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

class BufferExample {

    fun marbleDiagram() {
        val data = arrayOf("1", "2", "3", "4", "5", "6")

        CommonUtils.start()

        val earlySource = Observable.fromArray(*data)
            .take(3)
            .zipWith(Observable.interval(100, TimeUnit.MILLISECONDS), BiFunction{ a : String, _ : Any  -> a })

        val middleSource = Observable.just(data[3])
            .zipWith(Observable.timer(300, TimeUnit.MILLISECONDS), BiFunction{ a : String, _ : Any -> a })

        val lateSource = Observable.just(data[4], data[5])
            .zipWith(Observable.interval(100, TimeUnit.MILLISECONDS), BiFunction{ a : String, _ : Any -> a })

        val source = Observable.concat(earlySource, middleSource,lateSource)
            .buffer(3)

        source.subscribe { value -> Log.it(value) }

        CommonUtils.sleep(1000)
    }

    fun bufferSkip() {
        val data = arrayOf("1", "2", "3", "4", "5", "6")

        CommonUtils.start()

        val earlySource = Observable.fromArray(*data)
            .take(3)
            .zipWith(Observable.interval(100, TimeUnit.MILLISECONDS), BiFunction{ a : String, _ : Any  -> a })

        val middleSource = Observable.just(data[3])
            .zipWith(Observable.timer(300, TimeUnit.MILLISECONDS), BiFunction{ a : String, _ : Any -> a })

        val lateSource = Observable.just(data[4], data[5])
            .zipWith(Observable.interval(100, TimeUnit.MILLISECONDS), BiFunction{ a : String, _ : Any -> a })

        // 3개 모아서 한꺼번에 발행
        val source = Observable.concat(earlySource, middleSource, lateSource)
            .buffer(2, 3)

        source.subscribe { value -> Log.it(value) }

        CommonUtils.sleep(1000)
    }
}

fun main() {
    val demo = BufferExample()
    demo.marbleDiagram()
    demo.bufferSkip()
}