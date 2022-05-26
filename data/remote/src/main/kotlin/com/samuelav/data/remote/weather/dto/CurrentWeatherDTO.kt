package com.samuelav.data.remote.weather.dto

import com.google.gson.annotations.SerializedName
import com.samuelav.data.model.weather.CurrentWeatherBO
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

data class CurrentWeatherDTO(
    @SerializedName("dt")
    val dateTime: Long,
    @SerializedName("sunrise")
    val sunriseDateTime: Long,
    @SerializedName("sunset")
    val sunsetDateTime: Long,
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
    @SerializedName("weather")
    val state: List<WeatherStateDTO>
)

fun CurrentWeatherDTO.toBO() =
    CurrentWeatherBO(
        dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(dateTime), ZoneId.systemDefault()),
        sunriseDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(sunriseDateTime), ZoneId.systemDefault()),
        sunsetDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(sunsetDateTime), ZoneId.systemDefault()),
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
        state = state.first().toBO()
    )