package com.samuelav.data.local.weather.dbo

import com.samuelav.data.model.weather.HourlyWeatherBO
import java.time.LocalDateTime

data class HourlyWeatherDBO(
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
    val state: WeatherStateDBO
)

internal fun HourlyWeatherBO.toDBO() =
    HourlyWeatherDBO(
        dateTime = dateTime,
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
        windGust = windGust,
        state = state.toDBO()
    )

internal fun HourlyWeatherDBO.toBO() =
    HourlyWeatherBO(
        dateTime = dateTime,
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
        windGust = windGust,
        state = state.toBO()
    )
