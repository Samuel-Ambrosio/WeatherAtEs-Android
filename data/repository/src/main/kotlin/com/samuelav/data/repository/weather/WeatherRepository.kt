package com.samuelav.data.repository.weather

import com.samuelav.common.Error
import com.samuelav.common.Result
import com.samuelav.common.safeExecuteDataSource
import com.samuelav.data.model.weather.WeatherOneCallBO
import com.samuelav.data.source.weather.WeatherRemoteDataSource

interface WeatherRepository {
    suspend fun getWeather(): Result<Error, WeatherOneCallBO>
}

class WeatherRepositoryImpl(
    private val weatherRemoteDataSource: WeatherRemoteDataSource
): WeatherRepository {
    override suspend fun getWeather(): Result<Error, WeatherOneCallBO> =
        safeExecuteDataSource {
            weatherRemoteDataSource.getWeather(
                lat = 40.416729,
                lon = -3.703339,
                units = "metric",
                lang = "es")
        }
}