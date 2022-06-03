package com.samuelav.domain.location

import com.samuelav.data.repository.location.LocationRepository

class GetLastKnownLocationUseCase(private val locationRepository: LocationRepository) {
    operator fun invoke() = locationRepository.getLastKnownLocation()
}