package com.samuelav.data.remote.weather.dto

import com.google.gson.annotations.SerializedName
import com.samuelav.data.model.weather.WeatherOneCallBO

data class WeatherOneCallDTO(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("timezone")
    val timeZone: String,
    @SerializedName("timezone_offset")
    val timeZoneOffset: Int,
    @SerializedName("current")
    val current: CurrentWeatherDTO,
    @SerializedName("hourly")
    val hourly: List<HourlyWeatherDTO>,
    @SerializedName("daily")
    val daily: List<DailyWeatherDTO>
)

fun WeatherOneCallDTO.toBO() =
    WeatherOneCallBO(
        lat = lat,
        lon = lon,
        timeZone = timeZone,
        timeZoneOffset = timeZoneOffset,
        current = current.toBO(),
        hourly = hourly.map { it.toBO() },
        daily = daily.map { it.toBO() }
    )