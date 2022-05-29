package com.samuelav.data.local.weather.dbo

import com.samuelav.data.model.weather.WeatherStateBO

data class WeatherStateDBO(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

internal fun WeatherStateBO.toDBO() =
    WeatherStateDBO(id = id, main = main, description = description, icon = icon)

internal fun WeatherStateDBO.toBO() =
    WeatherStateBO(id = id, main = main, description = description, icon = icon)