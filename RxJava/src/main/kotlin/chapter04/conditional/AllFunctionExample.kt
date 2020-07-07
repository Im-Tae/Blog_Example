package chapter04.conditional

import common.Log
import common.Shape
import io.reactivex.Observable

class AllFunctionExample {
    fun marbleDiagram() {
        val data = arrayOf("1", "2", "3", "4")

        val source = Observable.fromArray(*data)
            .map(Shape()::getShape)
            .all(Shape().BALL::equals)
            // .all { value -> Shape().BALL == Shape().getShape(value) }

        source.subscribe { value -> Log.it(value) }
    }
}

fun main() {
    val demo = AllFunctionExample()
    demo.marbleDiagram()
}