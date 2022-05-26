package com.samuelav.data.source.weather

import com.samuelav.common.Error
import com.samuelav.common.Result
import com.samuelav.data.model.weather.WeatherOneCallBO

interface WeatherRemoteDataSource {
    suspend fun getWeather(
        lat: Double,
        lon: Double,
        units: String,
        lang: String
    ): Result<Error, WeatherOneCallBO>
}