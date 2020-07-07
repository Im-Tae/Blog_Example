package chapter02

import io.reactivex.Observable
import java.util.concurrent.Executors

class ObservableFromFuture {
    fun emit() {
        val future = Executors.newSingleThreadExecutor().submit<String> {
            Thread.sleep(1000)
            "Hello Future"
        }

        val source = Observable.fromFuture(future)
        source.subscribe(System.out::println)
    }
}

fun main() {
    val demo = ObservableFromFuture()
    demo.emit()
}