package chapter02

import common.Order
import io.reactivex.Observable
import io.reactivex.Single

class SingleExample {
    fun just() {
        val source = Single.just("Hello Single")
        source.subscribe { x -> println(x) }
    }

    fun fromObservable() {

        // Observable에서 Single 객체로 변환
        val source: Observable<String> = Observable.just("Hello Single")
        Single.fromObservable<Any>(source)
            .subscribe { x -> println(x) }


        // single 함수 호출
        Observable.just("Hello Single")
            .single("default item")
            .subscribe { x -> println(x) }


        // first 함수 호출
        val colors = arrayOf("Red", "Blue", "Gold")
        Observable.fromArray(*colors)
            .first("default value")
            .subscribe { x -> println(x) }

        // empty Observable
        Observable.empty<String>()
            .single("default value")
            .subscribe { x -> println(x) }

        // take 함수
        Observable.just(Order("ORD-1"), Order("ORD-2"))
            .take(1)
            .single(Order("default order"))
            .subscribe { x -> println(x) }
    }

    fun errorCase() {
        val source = Observable.just("Hello Single", "Error").single("default item")
        source.subscribe { x -> println(x) }
    }
}

fun main() {
    val demo = SingleExample()
    demo.just()
    demo.fromObservable()
    demo.errorCase()
}