package com.samuelav.data.model.weather

sealed class WeatherUnit(
    val system: String,
    val temperatureUnit: String,
    val windUnit: String,
) {
    object Metric: WeatherUnit(system = "metric", temperatureUnit = "ºC", windUnit = "m/s")
    object Imperial: WeatherUnit(system = "imperial", temperatureUnit = "ºF", windUnit = "mph")
    object Standard: WeatherUnit(system = "standard", temperatureUnit = "K", windUnit = "m/s")
}

fun String.toWeatherUnit() =
    when (this) {
        WeatherUnit.Metric.system -> WeatherUnit.Metric
        WeatherUnit.Imperial.system -> WeatherUnit.Imperial
        WeatherUnit.Standard.system -> WeatherUnit.Standard
        else -> WeatherUnit.Metric
    }