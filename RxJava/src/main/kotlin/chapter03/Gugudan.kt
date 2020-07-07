package chapter03

import io.reactivex.Observable
import io.reactivex.functions.Function
import java.util.*


class Gugudan {
    fun plainKotlin() {
        val scanner = Scanner(System.`in`)
        print("Gugudan Input : ")
        val dan = scanner.nextInt()

        for (row in 1..9) println("$dan * $row = ${dan * row}")

        scanner.close()
    }

    fun reactiveV1() {
        val scanner = Scanner(System.`in`)
        print("Gugudan Input : ")
        val dan = scanner.nextInt()

        val source = Observable.range(1, 9)
        source.subscribe { row -> println("$dan * $row = ${dan * row}") }

        scanner.close()
    }

    fun reactiveV2() {
        val scanner = Scanner(System.`in`)
        print("Gugudan Input : ")
        val dan = scanner.nextInt()

        val gugudan = Function<Int, Observable<String>> { num ->
            Observable.range(1, 9)
                .map { row: Int -> "$num * $row = ${num * row}" }
        }

        val source = Observable.just(dan).flatMap(gugudan)
        source.subscribe { data -> println(data) }

        scanner.close()
    }

    fun reactiveV3() {
        val scanner = Scanner(System.`in`)
        print("Gugudan Input : ")
        val dan = scanner.nextInt()

        val source = Observable.just(dan)
            .flatMap { num -> Observable.range(1, 9) }
            .map { row -> println("$dan * $row = ${dan * row}") }

        source.subscribe()
        scanner.close()
    }

    fun usingResultSelector() {
        val scanner = Scanner(System.`in`)
        print("Gugudan Input : ")
        val dan = scanner.nextInt()

        val source = Observable.just(dan)
            .flatMap({ gugu -> Observable.range(1, 9) }) { gugu, i ->
                "$gugu * $i = ${gugu * i}"
            }

        source.subscribe{ data -> println(data) }
    }
}

fun main() {
    val demo = Gugudan()
    demo.plainKotlin()
    demo.reactiveV1()
    demo.reactiveV2()
    demo.reactiveV3()
    demo.usingResultSelector()
}