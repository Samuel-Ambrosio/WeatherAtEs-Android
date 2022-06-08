package com.samuelav.data.model.weather

import java.time.LocalDateTime

data class DailyWeatherBO(
    val dateTime: LocalDateTime,
    val sunriseDateTime: LocalDateTime,
    val sunsetDateTime: LocalDateTime,
    val moonriseDateTime: LocalDateTime,
    val moonsetDateTime: LocalDateTime,
    val moonPhase: Double,
    val temp: DailyWeatherTempBO,
    val feelsLike: DailyWeatherFeelsLikeBO,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Int,
    val windSpeed: Double,
    val windDeg: Double,
    val windGust: Double,
    val state: WeatherStateBO
)

data class DailyWeatherTempBO(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class DailyWeatherFeelsLikeBO(
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)