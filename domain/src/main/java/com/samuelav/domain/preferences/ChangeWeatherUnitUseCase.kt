package com.samuelav.domain.preferences

import com.samuelav.data.model.weather.WeatherUnit
import com.samuelav.data.repository.preferences.PreferencesRepository

class ChangeWeatherUnitUseCase(private val preferencesRepository: PreferencesRepository) {
    suspend operator fun invoke(weatherUnit: WeatherUnit) =
        preferencesRepository.changeWeatherUnit(weatherUnit = weatherUnit)
}