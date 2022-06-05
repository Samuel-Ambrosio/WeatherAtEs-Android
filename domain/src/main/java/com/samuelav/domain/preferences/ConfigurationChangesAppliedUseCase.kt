package com.samuelav.domain.preferences

import com.samuelav.data.repository.preferences.PreferencesRepository

class ConfigurationChangesAppliedUseCase(private val preferencesRepository: PreferencesRepository) {
    suspend operator fun invoke() = preferencesRepository.configurationChangesApplied()
}