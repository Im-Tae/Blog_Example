package chapter02

import common.Order
import io.reactivex.Observable
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue

class ObservableFromIterable {
    fun listExample() {
        val names: MutableList<String> = ArrayList()
        names.add("Jerry")
        names.add("William")
        names.add("Bob")

        val source = Observable.fromIterable(names)
        source.subscribe(System.out::println)
    }

    fun setExample() {
        val cities : MutableSet<String> = HashSet()
        cities.add("Seoul")
        cities.add("London")
        cities.add("Paris")

        val source = Observable.fromIterable(cities)
        source.subscribe(System.out::println)
    }

    fun blockingQueueExample() {
        val orderQueue : BlockingQueue<Order> = ArrayBlockingQueue(100)
        orderQueue.add(Order("ORD-1"))
        orderQueue.add(Order("ORD-2"))
        orderQueue.add(Order("ORD-3"))

        val source = Observable.fromIterable(orderQueue)
        source.subscribe { order -> println(order.getId())}
    }
}

fun main() {
    val demo = ObservableFromIterable()
    demo.listExample()
    demo.setExample()
    demo.blockingQueueExample()
}