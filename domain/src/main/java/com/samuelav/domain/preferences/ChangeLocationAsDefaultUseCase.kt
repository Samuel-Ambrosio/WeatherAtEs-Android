package com.samuelav.domain.preferences

import com.samuelav.data.repository.preferences.PreferencesRepository

class ChangeLocationAsDefaultUseCase(private val preferencesRepository: PreferencesRepository) {
    suspend operator fun invoke() = preferencesRepository.changeLocationAsDefault()
}