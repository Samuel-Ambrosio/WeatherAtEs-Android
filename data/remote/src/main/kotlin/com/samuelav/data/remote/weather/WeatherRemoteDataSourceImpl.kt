package com.samuelav.data.remote.weather

import com.samuelav.common.Error
import com.samuelav.common.Result
import com.samuelav.common.mapSuccess
import com.samuelav.data.model.weather.WeatherOneCallBO
import com.samuelav.data.remote.weather.dto.toBO
import com.samuelav.data.source.weather.WeatherRemoteDataSource

class WeatherRemoteDataSourceImpl(private val weatherWs: WeatherWs): WeatherRemoteDataSource {
    override suspend fun getWeather(
        lat: Double,
        lon: Double,
        units: String,
        lang: String
    ): Result<Error, WeatherOneCallBO> =
        weatherWs.getWeatherOneCall(lat = lat, lon = lon, units = units, lang = lang).mapSuccess {
            it.toBO()
        }
}