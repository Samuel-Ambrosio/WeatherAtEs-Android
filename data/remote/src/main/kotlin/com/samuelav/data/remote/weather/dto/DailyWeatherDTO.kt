package com.samuelav.data.remote.weather.dto

import com.google.gson.annotations.SerializedName
import com.samuelav.data.model.weather.DailyWeatherBO
import com.samuelav.data.model.weather.DailyWeatherFeelsLikeBO
import com.samuelav.data.model.weather.DailyWeatherTempBO
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

data class DailyWeatherDTO(
    @SerializedName("dt")
    val dateTime: Long,
    @SerializedName("sunrise")
    val sunriseDateTime: Long,
    @SerializedName("sunset")
    val sunsetDateTime: Long,
    @SerializedName("moonrise")
    val moonriseDateTime: Long,
    @SerializedName("moonset")
    val moonsetDateTime: Long,
    @SerializedName("moon_phase")
    val moonPhase: Double,
    @SerializedName("temp")
    val temp: DailyWeatherTempDTO,
    @SerializedName("feels_like")
    val feelsLike: DailyWeatherFeelsLikeDTO,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("dew_point")
    val dewPoint: Double,
    @SerializedName("uvi")
    val uvi: Double,
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("wind_deg")
    val windDeg: Double,
    @SerializedName("wind_gust")
    val windGust: Double,
    @SerializedName("weather")
    val state: List<WeatherStateDTO>
)

data class DailyWeatherTempDTO(
    @SerializedName("day")
    val day: Double,
    @SerializedName("min")
    val min: Double,
    @SerializedName("max")
    val max: Double,
    @SerializedName("night")
    val night: Double,
    @SerializedName("eve")
    val eve: Double,
    @SerializedName("morn")
    val morn: Double
)

data class DailyWeatherFeelsLikeDTO(
    @SerializedName("day")
    val day: Double,
    @SerializedName("night")
    val night: Double,
    @SerializedName("eve")
    val eve: Double,
    @SerializedName("morn")
    val morn: Double
)

fun DailyWeatherDTO.toBO() =
    DailyWeatherBO(
        dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(dateTime), ZoneId.systemDefault()),
        sunriseDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(sunriseDateTime), ZoneId.systemDefault()),
        sunsetDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(sunsetDateTime), ZoneId.systemDefault()),
        moonriseDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(moonriseDateTime), ZoneId.systemDefault()),
        moonsetDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(moonsetDateTime), ZoneId.systemDefault()),
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
        state = state.first().toBO()
    )

fun DailyWeatherTempDTO.toBO() =
    DailyWeatherTempBO(
        day = day,
        min = min,
        max = max,
        night = night,
        eve = eve,
        morn = morn
    )

fun DailyWeatherFeelsLikeDTO.toBO() =
    DailyWeatherFeelsLikeBO(
        day = day,
        night = night,
        eve = eve,
        morn = morn
    )