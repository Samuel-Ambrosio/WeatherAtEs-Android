package com.samuelav.data.source.location

import com.samuelav.data.model.location.Coordinate

interface LocationDataSource {
    fun getLastKnownLocation(): Coordinate?
}