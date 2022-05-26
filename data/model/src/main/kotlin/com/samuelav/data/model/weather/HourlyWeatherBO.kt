package com.samuelav.data.model.weather

import java.time.LocalDateTime

data class HourlyWeatherBO(
    val dateTime: LocalDateTime,
    val temp: Double,
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int,
    val windSpeed: Double,
    val windDeg: Double,
    val windGust: Double,
    val state: WeatherStateBO
)
