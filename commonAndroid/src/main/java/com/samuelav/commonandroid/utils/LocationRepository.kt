package com.samuelav.commonandroid.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.samuelav.data.model.location.Coordinate

interface LocationRepository {
    fun getLastKnownLocation(): Coordinate?
}

class LocationRepositoryImpl(
    private val context: Context,
    private val locationManager: LocationManager,
): LocationRepository {
    @SuppressLint("MissingPermission")
    override fun getLastKnownLocation(): Coordinate? =
        if (hasLocationPermissions()) {
            val gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val networkLocation =
                locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            val passiveLocation =
                locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
            setOfNotNull(gpsLocation, networkLocation, passiveLocation)
                .maxByOrNull { it.time }
                ?.run { Coordinate(latitude = latitude, longitude = longitude) }
        } else {
            null
        }

    private fun hasLocationPermissions(): Boolean =
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION)
            .map { ContextCompat.checkSelfPermission(context, it) }
            .any { it == PackageManager.PERMISSION_GRANTED }
}