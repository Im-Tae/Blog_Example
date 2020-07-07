package chapter05.examples

import common.CommonUtils
import common.Log
import common.OkHttpHelper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.regex.Pattern

class OpenWeatherMapV2 {
    private val URL = "http://api.openweathermap.org/data/2.5/weather?q=London&appid="
    private val API_KEY = "ea9463cc69d79542047ba01dae512a5e"

    fun run() {
        val source = Observable.just(URL + API_KEY)
            .map(OkHttpHelper()::getWithLog)
            .subscribeOn(Schedulers.io())
            .share()
            .observeOn(Schedulers.newThread())

        source.map(this::parseTemperature).subscribe { data -> Log.it(data) }
        source.map(this::parseCityName).subscribe { data -> Log.it(data) }
        source.map(this::parseCountry).subscribe { data -> Log.it(data) }

        CommonUtils.sleep(3000)
    }

    private fun parseTemperature(json: String): String = parse(json, "\"temp\":[0-9]*.[0-9]*")

    private fun parseCityName(json: String): String = parse(json, "\"name\":\"[a-zA-Z]*\"")

    private fun parseCountry(json: String): String = parse(json, "\"country\":\"[a-zA-Z]*\"")

    private fun parse(json: String, regex: String): String {
        val pattern = Pattern.compile(regex)
        val match = pattern.matcher(json)

        if (match.find()) return match.group()

        return "N/A"
    }
}

fun main() {
    val demo = OpenWeatherMapV2()
    demo.run()
}