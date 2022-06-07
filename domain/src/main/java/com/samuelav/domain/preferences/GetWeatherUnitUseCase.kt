package com.samuelav.domain.preferences

import com.samuelav.data.repository.preferences.PreferencesRepository

class GetWeatherUnitUseCase(private val preferencesRepository: PreferencesRepository) {
    operator fun invoke() = preferencesRepository.getWeatherUnit()
}