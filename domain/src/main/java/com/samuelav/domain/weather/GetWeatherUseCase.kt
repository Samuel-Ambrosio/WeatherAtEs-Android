package com.samuelav.domain.weather

import com.samuelav.common.Error
import com.samuelav.common.Result
import com.samuelav.data.model.weather.WeatherOneCallBO
import com.samuelav.data.repository.weather.WeatherRepository

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(): Result<Error, WeatherOneCallBO> =
        weatherRepository.getWeather()
}