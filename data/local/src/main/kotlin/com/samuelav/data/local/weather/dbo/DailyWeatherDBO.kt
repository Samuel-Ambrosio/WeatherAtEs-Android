package com.samuelav.data.local.weather.dbo

import com.samuelav.data.model.weather.DailyWeatherBO
import com.samuelav.data.model.weather.DailyWeatherFeelsLikeBO
import com.samuelav.data.model.weather.DailyWeatherTempBO
import java.time.LocalDateTime

data class DailyWeatherDBO(
    val dateTime: LocalDateTime,
    val sunriseDateTime: LocalDateTime,
    val sunsetDateTime: LocalDateTime,
    val moonriseDateTime: LocalDateTime,
    val moonsetDateTime: LocalDateTime,
    val moonPhase: Double,
    val temp: DailyWeatherTempDBO,
    val feelsLike: DailyWeatherFeelsLikeDBO,
    val pressure: Int,
    val humidity: Int,
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Int,
    val windSpeed: Double,
    val windDeg: Double,
    val windGust: Double,
    val state: WeatherStateDBO
)

data class DailyWeatherTempDBO(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class DailyWeatherFeelsLikeDBO(
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

internal fun DailyWeatherTempBO.toDBO() =
    DailyWeatherTempDBO(
        day = day,
        min = min,
        max = max,
        night = night,
        eve = eve,
        morn = morn)

internal fun DailyWeatherFeelsLikeBO.toDBO() =
    DailyWeatherFeelsLikeDBO(
        day = day,
        night = night,
        eve = eve,
        morn = morn)

internal fun DailyWeatherBO.toDBO() =
    DailyWeatherDBO(
        dateTime = dateTime,
        sunriseDateTime = sunriseDateTime,
        sunsetDateTime = sunsetDateTime,
        moonriseDateTime = moonriseDateTime,
        moonsetDateTime = moonsetDateTime,
        moonPhase = moonPhase,
        temp = temp.toDBO(),
        feelsLike = feelsLike.toDBO(),
        pressure = pressure,
        humidity = humidity,
        dewPoint = dewPoint,
        uvi = uvi,
        clouds = clouds,
        windSpeed = windSpeed,
        windDeg = windDeg,
        windGust = windGust,
        state = state.toDBO()
    )

internal fun DailyWeatherTempDBO.toBO() =
    DailyWeatherTempBO(
        day = day,
        min = min,
        max = max,
        night = night,
        eve = eve,
        morn = morn)

internal fun DailyWeatherFeelsLikeDBO.toBO() =
    DailyWeatherFeelsLikeBO(
        day = day,
        night = night,
        eve = eve,
        morn = morn)

internal fun DailyWeatherDBO.toBO() =
    DailyWeatherBO(
        dateTime = dateTime,
        sunriseDateTime = sunriseDateTime,
        sunsetDateTime = sunsetDateTime,
        moonriseDateTime = moonriseDateTime,
        moonsetDateTime = moonsetDateTime,
        moonPhase = moonPhase,
        temp = temp.toBO(),
        feelsLike = feelsLike.toBO(),
        pressure = pressure,
        humidity = humidity,
        dewPoint = dewPoint,
        uvi = uvi,
        clouds = clouds,
        windSpeed = windSpeed,
        windDeg = windDeg,
        windGust = windGust,
        state = state.toBO()
    )