package chapter04.combine

import common.CommonUtils
import common.Log
import common.Shape
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import java.lang.Math.max
import java.lang.Math.min
import java.lang.StringBuilder
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit

class ZipExample {
    fun marbleDiagram() {
        val shapes = arrayOf("BALL", "PENTAGON", "STAR")
        val coloredTriangles = arrayOf("2-T", "6-T", "4-T")

        val source = Observable.zip(
            Observable.fromArray(*shapes).map(Shape()::getSuffix),
            Observable.fromArray(*coloredTriangles).map(Shape()::getColor),
            BiFunction { suffix: String, color: String -> "$color$suffix" }
        )

        source.subscribe { data -> Log.it(data) }
    }

    fun zipNumbers() {
        val source = Observable.zip(
            Observable.just(100, 200, 300),
            Observable.just(10, 20, 30),
            Observable.just(1, 2, 3),
            Function3 { num1: Int, num2: Int, num3: Int -> num1 + num2 + num3 }
        )

        source.subscribe { data -> Log.it(data) }
    }

    fun zipInterval() {
        val source = Observable.zip(
            Observable.just("RED", "GREEN", "BLUE"),
            Observable.interval(200, TimeUnit.MILLISECONDS),
            BiFunction { value: String, _: Long -> value }
        )

        CommonUtils.start()
        source.subscribe { data -> Log.it(data) }
        CommonUtils.sleep(1000)
    }

    fun electricBillV1() {

        var index: Int = 0

        val data = arrayOf(
            "100",  //910 + 93.3 * 100 = 10,240원
            "300"   //1600 + 93.3 * 200 + 187.9 * 100 = 39,050원
        )

        val basePrice: Observable<Int> = Observable.fromArray(*data)
            .map { value -> Integer.parseInt(value) }
            .map { value ->
                if (value <= 200) return@map 910
                if (value <= 400) return@map 1600
                return@map 7300
            }

        val usagePrice = Observable.fromArray(*data)
            .map { value -> Integer.parseInt(value) }
            .map { value ->
                val series1 = min(200, value) * 93.3
                val series2 = min(200, max(value - 200, 0)) * 187.9
                val series3 = max(0, max(value - 400, 0)) * 280.65
                return@map (series1 + series2 + series3).toInt()
            }

        val source = Observable.zip(
            basePrice,
            usagePrice,
            BiFunction { v1: Int, v2: Int -> v1 + v2 }
        )

        source.map { value -> DecimalFormat("#,###").format(value) }
            .subscribe {value ->
                val sb = StringBuilder()
                sb.append("Usage: ${data[index]} kWh => ")
                sb.append("Price: ${value}원")
                Log.it(sb.toString())

                index++
            }
    }

    fun electricBillV2() {
        val data = arrayOf(
            "100",  //910 + 93.3 * 100 = 10,240원
            "300"   //1600 + 93.3 * 200 + 187.9 * 100 = 39,050원
        )

        val basePrice: Observable<Int> = Observable.fromArray(*data)
            .map { value -> Integer.parseInt(value) }
            .map { value ->
                if (value <= 200) return@map 910
                if (value <= 400) return@map 1600
                return@map 7300
            }

        val usagePrice = Observable.fromArray(*data)
            .map { value -> Integer.parseInt(value) }
            .map { value ->
                val series1 = min(200, value) * 93.3
                val series2 = min(200, max(value - 200, 0)) * 187.9
                val series3 = max(0, max(value - 400, 0)) * 280.65
                return@map (series1 + series2 + series3).toInt()
            }

        val source = Observable.zip(
            basePrice,
            usagePrice,
            Observable.fromArray(*data),
            Function3{ v1: Int, v2: Int, i: String -> Pair(i, v1 + v2) }
        )

        source.map { value -> Pair(value.first, DecimalFormat("#,###").format(value.second)) }
            .subscribe { value ->
                val sb = StringBuilder()
                sb.append("Usage: ${value.first} kWh => ")
                sb.append("Price: ${value.second}원")
                Log.it(sb.toString())
            }
    }

    fun zipWithNumbers() {
        val source = Observable.zip(
                Observable.just(1000, 200, 300),
                Observable.just(10, 20, 30),
                BiFunction { a: Int, b: Int -> a + b })
            .zipWith(Observable.just(1, 2, 3), BiFunction { ab: Int, c: Int -> ab + c })

        source.subscribe { data -> Log.it(data) }
    }
}

fun main() {
    val demo = ZipExample()
    demo.marbleDiagram()
    demo.zipNumbers()
    demo.zipInterval()
    demo.electricBillV1()
    demo.electricBillV2()
    demo.zipWithNumbers()
}