package chapter04.combine

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.functions.BiFunction
import io.reactivex.observables.ConnectableObservable
import java.util.*

class ReactiveSum {
    fun run() {
        val source: ConnectableObservable<String> = userInput()

        val a = source
            .filter { str -> str.startsWith("a:") }
            .map { str -> str.replace("a:", "") }
            .map { str -> Integer.parseInt(str) }

        val b = source
            .filter { str -> str.startsWith("b:") }
            .map { str -> str.replace("b:", "") }
            .map { str -> Integer.parseInt(str) }

        Observable.combineLatest(
            a.startWith(0),
            b.startWith(0),
            BiFunction { x: Int, y: Int -> x + y }
        ).subscribe { res -> println("Result: $res") }

        source.connect()
    }

    private fun userInput(): ConnectableObservable<String> {
        return Observable.create { emitter: ObservableEmitter<String> ->
            val scanner = Scanner(System.`in`)
            while (true) {
                println("Input: ")
                val line = scanner.nextLine()
                emitter.onNext(line)

                if (line.indexOf("Exit") >= 0) {
                    scanner.close()
                    break;
                }
            }
        }.publish()
    }
}

fun main() {
    ReactiveSum().run()
}