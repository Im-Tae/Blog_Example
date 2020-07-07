package chapter07

import common.Log
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.schedulers.Schedulers

class ExceptionHandling {
    fun cannotCatch() {
        val source = Observable.create{ emitter: ObservableEmitter<String> ->
            emitter.onNext("1")
            emitter.onError(Exception("Some Error"))
            emitter.onNext("3")
            emitter.onComplete()
        }

        try {
            source.subscribe { data -> Log.it(data) }
        } catch (e: Exception) {
            Log.it(e.message.toString())
        }
    }

    fun onErrorReturn() {
        val grades = arrayOf("70", "88", "$100", "93", "83")

        val source = Observable.fromArray(*grades)
            .map { data -> Integer.parseInt(data) }
            .onErrorReturn { -1 }

        source.subscribe { data ->
            if (data < 0) {
                Log.it("Wrong Data Found")
                return@subscribe
            }

            Log.it("Grade is $data")
        }
    }

    fun onError() {
        val grades = arrayOf("70", "88", "$100", "93", "83")

        val source = Observable.fromArray(*grades)
            .map { data -> Integer.parseInt(data) }

        source.subscribe(
            { data -> Log.it("Grade is $data") }
        )
        { Log.it("Wrong Data found!!") }
    }

    fun onErrorReturnItem() {
        val grades = arrayOf("70", "88", "$100", "93", "83")

        val source = Observable.fromArray(*grades)
            .map { data -> Integer.parseInt(data) }
            .onErrorReturnItem(-1)

        source.subscribe { data ->
            if (data < 0) {
                Log.it("Wrong Data Found")
                return@subscribe
            }

            Log.it("Grade is $data")
        }
    }

    fun onErrorResumeNext() {
        val salesData = arrayOf("100", "200", "A300")

        val onParseError = Observable.defer {
            Log.d("send email to administrator")
            Observable.just(-1)
        }.subscribeOn(Schedulers.io())

        val source = Observable.fromArray(*salesData)
            .map { data -> Integer.parseInt(data) }
            .onErrorResumeNext(onParseError)

        source.subscribe { data ->
            if (data < 0) {
                Log.it("Wrong Data Found")
                return@subscribe
            }

            Log.it("Sales data : $data")
        }
    }
}

fun main() {
    val demo = ExceptionHandling()
    demo.cannotCatch()
    demo.onErrorReturn()
    demo.onError()
    demo.onErrorReturnItem()
    demo.onErrorResumeNext()
}