package com.samuelav.data.remote.weather.dto

import com.google.gson.annotations.SerializedName
import com.samuelav.data.model.weather.HourlyWeatherBO
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

data class HourlyWeatherDTO(
    @SerializedName("dt")
    val dateTime: Long,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
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
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("wind_deg")
    val windDeg: Double,
    @SerializedName("wind_gust")
    val windGust: Double,
    @SerializedName("weather")
    val state: List<WeatherStateDTO>
)

fun HourlyWeatherDTO.toBO() =
    HourlyWeatherBO(
        dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(dateTime), ZoneId.systemDefault()),
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
        state = state.first().toBO()
    )