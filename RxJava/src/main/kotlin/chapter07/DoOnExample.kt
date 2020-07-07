package chapter07

import common.CommonUtils
import common.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

class DoOnExample {
    fun basic() {
        val orgs = arrayOf("1", "2", "3")
        val source = Observable.fromArray(*orgs)

        source.doOnNext{ data -> Log.d(data) }
            .doOnComplete{ Log.d("onComplete()") }
            .doOnError{ e -> Log.d(e.message.toString()) }
            .subscribe { data -> Log.it(data) }
    }

    fun withError() {
        val divider = arrayOf(10, 5, 0)

        Observable.fromArray(*divider)
            .map { div -> 1000 / div }
            .doOnNext { data -> Log.d(data) }
            .doOnComplete { Log.d("onComplete()") }
            .doOnError{ e -> Log.d(e.message.toString()) }
            .subscribe { data -> Log.it(data) }
    }

    fun doOnEach() {
        val data = arrayOf("ONE", "TWO", "THREE")
        val source = Observable.fromArray(*data)

        source.doOnEach{noti ->
            if(noti.isOnNext) Log.d(noti.value.toString())
            if(noti.isOnComplete) Log.d("onComplete()")
            if (noti.isOnError) Log.d(noti.error?.message.toString())
        }
            .subscribe { data -> Log.it(data) }
    }

    fun doOnEachObserver() {
        val orgs = arrayOf("1", "3", "5")
        val source = Observable.fromArray(*orgs)

        source.doOnEach(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                // doOnEach에서는 onSubscribe 함수가 호출되지 않는다.
            }

            override fun onNext(value: String) {
                Log.d(value)
            }

            override fun onError(e: Throwable) {
                Log.d(e.message.toString())
            }

            override fun onComplete() {
                Log.d("onComplete()")
            }
        }).subscribe { data -> Log.it(data) }
    }

    fun doOnSubscribeAndDispose() {
        val orgs = arrayOf("1", "3", "5", "2", "6")
        val source = Observable.fromArray(*orgs)
            .zipWith(Observable.interval(100, TimeUnit.MILLISECONDS), BiFunction { a: Any, _:Any -> a })
            .doOnSubscribe { _ -> Log.d("onSubscribe()") }
            .doOnDispose{ Log.d("onDispose()") }

        val d = source.subscribe { data -> Log.it(data) }
        CommonUtils.sleep(200)
        d.dispose()
        CommonUtils.sleep(300)
    }

    fun doOnLifecycle() {
        val orgs = arrayOf("1", "3", "5", "2", "6")
        val source = Observable.fromArray(*orgs)
            .zipWith(Observable.interval(100, TimeUnit.MILLISECONDS), BiFunction { a: Any, _:Any -> a })
            .doOnLifecycle( { _ -> Log.d("onSubscribe()") }, { Log.d("onDispose()") })

        val d = source.subscribe { data -> Log.it(data) }
        CommonUtils.sleep(200)
        d.dispose()
        CommonUtils.sleep(300)
    }

    fun doOnTerminate() {
        val orgs = arrayOf("1", "3", "5")
        val source = Observable.fromArray(*orgs)

        source.doOnTerminate { Log.d("onTerminate()") }
            .doOnComplete { Log.d("onComplete") }
            .doOnError{ e -> Log.d(e.message.toString()) }
            .subscribe { data -> Log.it(data) }
    }

    fun doFinally() {
        val orgs = arrayOf("1", "3", "5")
        val source = Observable.fromArray(*orgs)

        source.doFinally { Log.d("doFinally()") }
            .doOnComplete { Log.d("onComplete") }
            .doOnError{ e -> Log.d(e.message.toString()) }
            .subscribe { data -> Log.it(data) }
    }
}

fun main() {
    val demo = DoOnExample()
    demo.basic()
    demo.withError()
    demo.doOnEach()
    demo.doOnEachObserver()
    demo.doOnSubscribeAndDispose()
    demo.doOnLifecycle()
    demo.doOnTerminate()
    demo.doFinally()
}