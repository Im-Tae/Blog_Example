package chapter05.examples

import common.CommonUtils
import common.Log
import common.OkHttpHelper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.regex.Pattern

class OpenWeatherMapV1 {
    private val URL = "http://api.openweathermap.org/data/2.5/weather?q=London&appid="
    private val API_KEY = "ea9463cc69d79542047ba01dae512a5e"

    fun run() {
        val source = Observable.just(URL + API_KEY)
            .map(OkHttpHelper()::getWithLog)
            .subscribeOn(Schedulers.io())

        val temperature = source.map(this::parseTemperature)
        val city = source.map(this::parseCityName)
        val country = source.map(this::parseCountry)

        CommonUtils.start()

        Observable.concat(temperature, city, country)
            .observeOn(Schedulers.newThread())
            .subscribe { data -> Log.it(data) }
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
    val demo = OpenWeatherMapV1()
    demo.run()
}