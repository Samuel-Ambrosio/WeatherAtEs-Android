package com.samuelav.domain.weather

import com.samuelav.data.repository.weather.WeatherRepository

class GetSearchedWeatherUseCase(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke() = weatherRepository.getSearchedWeather()
}