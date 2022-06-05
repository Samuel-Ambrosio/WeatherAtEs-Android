package com.samuelav.data.source.preferences

import com.samuelav.data.model.location.Coordinate
import com.samuelav.data.model.weather.WeatherUnit
import kotlinx.coroutines.flow.Flow

interface PreferencesLocalDataSource {
    suspend fun saveLocationCoordinate(coordinate: Coordinate)
    fun getSavedLocationCoordinate(): Flow<Coordinate?>
    suspend fun changeLocationAsDefault()
    fun isLocationAsDefault(): Flow<Boolean>
    suspend fun changeWeatherUnit(weatherUnit: WeatherUnit)
    fun getWeatherUnit(): Flow<WeatherUnit>
    suspend fun configurationChangesApplied()
    fun isConfigurationChanged(): Flow<Boolean>
}