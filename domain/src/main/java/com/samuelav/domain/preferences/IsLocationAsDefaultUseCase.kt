package com.samuelav.domain.preferences

import com.samuelav.data.repository.preferences.PreferencesRepository

class IsLocationAsDefaultUseCase(private val preferencesRepository: PreferencesRepository) {
    operator fun invoke() = preferencesRepository.isLocationAsDefault()
}