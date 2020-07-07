package chapter05

import common.CommonUtils
import common.Log
import common.Shape
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class FlipExample {
    fun marbleDiagram() {
        val objs = arrayOf("1-S", "2-T", "3-P")

        val source = Observable.fromArray(*objs)
            .doOnNext { data -> Log.it("Original data = $data") }
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .map(Shape()::flip)

        source.subscribe { data -> Log.it(data) }
        CommonUtils.sleep(500)
    }

    fun observeOnRemoved() {
        val objs = arrayOf("1-S", "2-T", "3-P")

        val source = Observable.fromArray(*objs)
            .doOnNext { data -> Log.it("Original data = $data") }
            .subscribeOn(Schedulers.newThread())
            //.observeOn(Schedulers.newThread())
            .map(Shape()::flip)

        source.subscribe { data -> Log.it(data) }
        CommonUtils.sleep(500)
    }
}

fun main() {
    val demo = FlipExample()
    demo.marbleDiagram()
    demo.observeOnRemoved()
}