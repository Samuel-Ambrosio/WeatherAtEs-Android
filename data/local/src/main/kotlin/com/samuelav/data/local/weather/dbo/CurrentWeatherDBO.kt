package com.samuelav.data.local.weather.dbo

import com.samuelav.data.model.weather.CurrentWeatherBO
import java.time.LocalDateTime

data class CurrentWeatherDBO(
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
    val state: WeatherStateDBO
)

internal fun CurrentWeatherBO.toDBO() =
    CurrentWeatherDBO(
        dateTime = dateTime,
        sunriseDateTime = sunriseDateTime,
        sunsetDateTime = sunsetDateTime,
        temp = temp,
        feelsLike = feelsLike,
        pressure = pressure,
        humidity = humidity,
        dewPoint = dewPoint,
        uvi = uvi,
        clouds = clouds,
        visibility = visibility,
        windSpeed = windSpeed,
        windDeg = windDeg,
        state = state.toDBO()
    )

internal fun CurrentWeatherDBO.toBO() =
    CurrentWeatherBO(
        dateTime = dateTime,
        sunriseDateTime = sunriseDateTime,
        sunsetDateTime = sunsetDateTime,
        temp = temp,
        feelsLike = feelsLike,
        pressure = pressure,
        humidity = humidity,
        dewPoint = dewPoint,
        uvi = uvi,
        clouds = clouds,
        visibility = visibility,
        windSpeed = windSpeed,
        windDeg = windDeg,
        state = state.toBO()
    )