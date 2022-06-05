package com.samuelav.data.repository.preferences

import com.samuelav.data.model.location.Coordinate
import com.samuelav.data.model.weather.WeatherUnit
import com.samuelav.data.source.preferences.PreferencesLocalDataSource
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    suspend fun saveLocationCoordinate(coordinate: Coordinate)
    fun getSavedLocationCoordinate(): Flow<Coordinate?>
    suspend fun changeLocationAsDefault()
    fun isLocationAsDefault(): Flow<Boolean>
    suspend fun changeWeatherUnit(weatherUnit: WeatherUnit)
    suspend fun configurationChangesApplied()
    fun isConfigurationChanged(): Flow<Boolean>
}

class PreferencesRepositoryImpl(
    private val preferencesLocalDataSource: PreferencesLocalDataSource
): PreferencesRepository {
    override suspend fun saveLocationCoordinate(coordinate: Coordinate) {
        preferencesLocalDataSource.saveLocationCoordinate(coordinate = coordinate)
    }

    override fun getSavedLocationCoordinate(): Flow<Coordinate?> =
        preferencesLocalDataSource.getSavedLocationCoordinate()

    override suspend fun changeLocationAsDefault() {
        preferencesLocalDataSource.changeLocationAsDefault()
    }

    override fun isLocationAsDefault(): Flow<Boolean> =
        preferencesLocalDataSource.isLocationAsDefault()

    override suspend fun changeWeatherUnit(weatherUnit: WeatherUnit) {
        preferencesLocalDataSource.changeWeatherUnit(weatherUnit = weatherUnit)
    }

    override suspend fun configurationChangesApplied() {
        preferencesLocalDataSource.configurationChangesApplied()
    }

    override fun isConfigurationChanged(): Flow<Boolean> =
        preferencesLocalDataSource.isConfigurationChanged()
}