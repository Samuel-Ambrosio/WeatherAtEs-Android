package com.samuelav.data.source.weather

import com.samuelav.common.utils.Error
import com.samuelav.common.utils.Result
import com.samuelav.data.model.weather.WeatherOneCallBO

interface WeatherRemoteDataSource {
    suspend fun getWeather(
        lat: Double,
        lon: Double,
        units: String,
    ): Result<Error, WeatherOneCallBO>
}