package com.samuelav.domain.weather

import com.samuelav.common.utils.Error
import com.samuelav.common.utils.Result
import com.samuelav.data.model.weather.WeatherOneCallBO
import com.samuelav.data.repository.weather.WeatherRepository

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(refresh: Boolean): Result<Error, WeatherOneCallBO> =
        weatherRepository.getWeather(refresh = refresh)
}