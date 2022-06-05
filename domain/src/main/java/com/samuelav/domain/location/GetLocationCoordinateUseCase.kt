package com.samuelav.domain.location

import com.samuelav.common.app.AppCommonConfiguration
import com.samuelav.data.model.location.Coordinate
import com.samuelav.data.repository.location.LocationRepository
import com.samuelav.data.repository.preferences.PreferencesRepository
import kotlinx.coroutines.flow.firstOrNull

class GetLocationCoordinateUseCase(
    private val appCommonConfiguration: AppCommonConfiguration,
    private val locationRepository: LocationRepository,
    private val preferencesRepository: PreferencesRepository,
) {
    suspend operator fun invoke(): Coordinate {
        val defaultCoordinate =
            Coordinate(
                latitude = appCommonConfiguration.defaultLat,
                longitude = appCommonConfiguration.defaultLon)
        val savedCoordinate = preferencesRepository.getSavedLocationCoordinate().firstOrNull()
        val lastKnownLocation = locationRepository.getLastKnownLocation()

        val isLocationAsDefault = preferencesRepository.isLocationAsDefault().firstOrNull() ?: true

        return when {
            isLocationAsDefault -> lastKnownLocation ?: savedCoordinate ?: defaultCoordinate
            else -> savedCoordinate ?: defaultCoordinate
        }
    }
}