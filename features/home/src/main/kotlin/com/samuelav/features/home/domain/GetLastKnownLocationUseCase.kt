package com.samuelav.features.home.domain

import com.samuelav.commonandroid.utils.LocationRepository

class GetLastKnownLocationUseCase(private val locationRepository: LocationRepository) {
    operator fun invoke() = locationRepository.getLastKnownLocation()
}