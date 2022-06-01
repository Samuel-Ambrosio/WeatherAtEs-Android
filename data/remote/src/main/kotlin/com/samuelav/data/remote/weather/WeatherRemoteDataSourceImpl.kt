package com.samuelav.data.remote.weather

import android.location.Geocoder
import com.samuelav.common.utils.Error
import com.samuelav.common.utils.Result
import com.samuelav.common.utils.mapSuccess
import com.samuelav.data.model.weather.WeatherOneCallBO
import com.samuelav.data.remote.weather.dto.toBO
import com.samuelav.data.source.weather.WeatherRemoteDataSource

class WeatherRemoteDataSourceImpl(
    private val weatherWs: WeatherWs,
    private val geocoder: Geocoder
): WeatherRemoteDataSource {
    override suspend fun getWeather(
        lat: Double,
        lon: Double,
        units: String,
        lang: String
    ): Result<Error, WeatherOneCallBO> =
        weatherWs.getWeatherOneCall(lat = lat, lon = lon, units = units, lang = lang)
            .mapSuccess {
                val address = geocoder.getFromLocation(lat, lon, 1).firstOrNull()
                it.toBO(location = address?.locality ?: "")
            }
}