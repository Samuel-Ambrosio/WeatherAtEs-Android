package com.samuelav.data.model.weather

import java.time.LocalDateTime

data class CurrentWeatherBO(
    val dateTime: LocalDateTime,
    val sunriseDateTime: LocalDateTime,
    val sunsetDateTime: LocalDateTime,
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
    val state: WeatherStateBO
)
