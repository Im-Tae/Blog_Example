package chapter04.create

import io.reactivex.Observable

class DeferExample {

    private var name =  "Im"

    fun marbleDiagram() {

        val source1 = Observable.just(name)
        source1.subscribe { data -> println(data) }

        name = "Leaf"

        val source2 = Observable.defer { Observable.just(name) }
        source2.subscribe { data -> println(data) }

        source1.subscribe { data -> println(data) }
    }
}

fun main() {
    val demo = DeferExample()
    demo.marbleDiagram()
}