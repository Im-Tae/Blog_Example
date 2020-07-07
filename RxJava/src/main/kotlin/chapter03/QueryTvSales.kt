package chapter03

import io.reactivex.Observable
import kotlin.collections.ArrayList


class QueryTvSales {
    fun run() {
        
        // 1. 데이터 입력
        val sales: MutableList<Pair<String, Int>> = ArrayList()
        sales.add(Pair("TV", 2500))
        sales.add(Pair("Camera", 300))
        sales.add(Pair("TV", 1600))
        sales.add(Pair("Phone", 800))

        val tvSales = Observable.fromIterable(sales)

            // 2. TV 매출 필터링
            .filter { sale -> "TV" == sale.first }
            .map { sale -> sale.second }

            // 3. TV 매출의 합
            .reduce { sale1, sale2 -> sale1 + sale2 }
        tvSales.subscribe { total -> println("TV Sales : $$total") }
    }
}

fun main() {
    val demo = QueryTvSales()
    demo.run()
}