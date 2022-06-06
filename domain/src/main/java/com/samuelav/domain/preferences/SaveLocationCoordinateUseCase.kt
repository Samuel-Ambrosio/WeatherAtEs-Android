package com.samuelav.domain.preferences

import com.samuelav.data.model.location.Coordinate
import com.samuelav.data.repository.preferences.PreferencesRepository

class SaveLocationCoordinateUseCase(private val preferencesRepository: PreferencesRepository) {
    suspend operator fun invoke(coordinate: Coordinate) {
        preferencesRepository.saveLocationCoordinate(coordinate = coordinate)
    }
}