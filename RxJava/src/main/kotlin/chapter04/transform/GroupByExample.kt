package chapter04.transform

import common.Shape
import io.reactivex.Observable
import io.reactivex.observables.GroupedObservable

class GroupByExample {
    fun marbleDiagram() {
        val objs = arrayOf("6", "4", "2-T", "2", "6-T", "4-T")
        val source : Observable<GroupedObservable<String, String>> = Observable.fromArray(*objs).groupBy(Shape()::getShape)

        source.subscribe { obj -> obj.subscribe { data -> println("GROUP: ${obj.key} \t Value: $data") } }
    }

    fun filterBallGroup() {
        val objs = arrayOf("6", "4", "2-T", "2", "6-T", "4-T")
        val source : Observable<GroupedObservable<String, String>> = Observable.fromArray(*objs).groupBy(Shape()::getShape)

        source.subscribe { obj ->
            obj.filter { obj.key.equals("BALL") }
                .subscribe { data -> println("GROUP: ${obj.key} \t Value: $data") }
        }
    }
}

fun main() {
    val demo = GroupByExample()
    //demo.marbleDiagram()
    demo.filterBallGroup()
}