package com.samuelav.data.repository.location

import com.samuelav.data.model.location.Coordinate
import com.samuelav.data.source.location.LocationDataSource

interface LocationRepository {
    fun getLastKnownLocation(): Coordinate?
}

class LocationRepositoryImpl(
    private val locationDataSource: LocationDataSource
): LocationRepository {
    override fun getLastKnownLocation(): Coordinate? = locationDataSource.getLastKnownLocation()
}