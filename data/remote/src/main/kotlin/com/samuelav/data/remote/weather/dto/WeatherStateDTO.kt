package com.samuelav.data.remote.weather.dto

import com.google.gson.annotations.SerializedName
import com.samuelav.data.model.weather.WeatherStateBO

data class WeatherStateDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)

fun WeatherStateDTO.toBO() =
    WeatherStateBO(
        id = id,
        main = main,
        description = description,
        icon = icon
    )