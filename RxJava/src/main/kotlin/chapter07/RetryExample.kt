package chapter07

import common.CommonUtils
import common.Log
import common.OkHttpHelper
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class RetryExample {
    fun try5() {

        CommonUtils.start()

        val url = "https://api.github.com/zen"

        val source = Observable.just(url)
            .map(OkHttpHelper()::get)
            .retry(5)
            .onErrorReturnItem("-500")

        source.subscribe { data -> Log.it("result : $data") }
    }

    fun retryWithDelay() {

        val RETRY_MAX = 5
        val RETRY_DELAY = 1000L

        CommonUtils.start()

        val url = "https://api.github.com/zen"

        val source = Observable.just(url)
            .map(OkHttpHelper()::get)
            .retry{
                retryCnt : Int, _ : Throwable ->
                 Log.it("retryCnt = $retryCnt")
                CommonUtils.sleep(RETRY_DELAY)

                retryCnt < RETRY_MAX
            }
            .onErrorReturnItem("-500")

        source.subscribe { data -> Log.it("result : $data") }
    }

    fun retryUntil() {

        CommonUtils.start()

        val url = "https://api.github.com/zen"

        val source = Observable.just(url)
            .map(OkHttpHelper()::get)
            .subscribeOn(Schedulers.io())
            .retryUntil {
                if (CommonUtils.isNetworkAvailable())
                    true
                CommonUtils.sleep(1000)
                false
            }

        source.subscribe { data -> Log.it(data) }

        CommonUtils.sleep(5000)
    }

    fun retryWhen() {

        Observable.create { emitter: ObservableEmitter<String?> ->
            println("subscribing")
            emitter.onError(RuntimeException("always fails"))
        }
            .retryWhen { attempts: Observable<Throwable?> ->
                attempts.zipWith(
                    Observable.range(1, 3),
                    BiFunction { n: Throwable?, i: Int -> i }
                ).flatMap { i: Int ->
                    println("delay retry by $i second(s)")
                    Observable.timer(i.toLong(), TimeUnit.SECONDS)
                }
            }.blockingForEach { x: String? -> println(x) }

        Observable.create{ emitter: ObservableEmitter<String> ->
            emitter.onError(RuntimeException("always fails"))
        }.retryWhen { attemps : Observable<Throwable> ->
            attemps.zipWith(
                Observable.range(1, 3),
                BiFunction { _: Throwable, i: Int -> i }
            ).flatMap { i ->
                Log.it("delay retry by $i second(s)")
                Observable.timer(i.toLong(), TimeUnit.SECONDS)
            }
        }.blockingForEach { data -> Log.it(data) }
    }
}

fun main() {
    val demo = RetryExample()
    //demo.try5()
    //demo.retryWithDelay()
    demo.retryUntil()
    //demo.retryWhen()
}