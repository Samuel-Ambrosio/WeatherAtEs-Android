package com.samuelav.data.local.weather.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.samuelav.data.model.weather.WeatherOneCallBO

@Entity
data class WeatherOneCallDBO(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val location: String,
    val lat: Double,
    val lon: Double,
    val timeZone: String,
    val timeZoneOffset: Int,
    val current: CurrentWeatherDBO,
    val hourly: List<HourlyWeatherDBO>,
    val daily: List<DailyWeatherDBO>
)

internal fun WeatherOneCallBO.toDBO() =
    WeatherOneCallDBO(
        location = location,
        lat = lat,
        lon = lon,
        timeZone = timeZone,
        timeZoneOffset = timeZoneOffset,
        current = current.toDBO(),
        hourly = hourly.map { it.toDBO() },
        daily = daily.map { it.toDBO() }
    )

internal fun WeatherOneCallDBO.toBO() =
    WeatherOneCallBO(
        location = location,
        lat = lat,
        lon = lon,
        timeZone = timeZone,
        timeZoneOffset = timeZoneOffset,
        current = current.toBO(),
        hourly = hourly.map { it.toBO() },
        daily = daily.map { it.toBO() }
    )