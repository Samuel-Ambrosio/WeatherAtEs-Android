package com.samuelav.domain.weather

import com.samuelav.common.utils.Error
import com.samuelav.common.utils.Result
import com.samuelav.data.model.weather.WeatherOneCallBO
import com.samuelav.data.repository.weather.WeatherRepository

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(
        lat: Double?,
        lon: Double?,
        refresh: Boolean
    ): Result<Error, WeatherOneCallBO> =
        weatherRepository.getWeather(lat = lat, lon = lon, refresh = refresh)
}