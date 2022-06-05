package com.samuelav.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.samuelav.data.model.location.Coordinate
import com.samuelav.data.model.weather.WeatherUnit
import com.samuelav.data.model.weather.toWeatherUnit
import com.samuelav.data.source.preferences.PreferencesLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesLocalDataSourceImpl(
    private val dataStore: DataStore<Preferences>
): PreferencesLocalDataSource {
    companion object {
        private val PREFERENCES_KEY_SAVED_LATITUDE = doublePreferencesKey("saved_latitude")
        private val PREFERENCES_KEY_SAVED_LONGITUDE = doublePreferencesKey("saved_longitude")
        private val PREFERENCES_KEY_LOCATION_AS_DEFAULT = booleanPreferencesKey("location_as_default")
        private val PREFERENCES_KEY_WEATHER_UNIT = stringPreferencesKey("weather_unit")
        private val PREFERENCES_KEY_CONFIGURATION_CHANGED = booleanPreferencesKey("configuration_changed")
    }

    override suspend fun saveLocationCoordinate(coordinate: Coordinate) {
        dataStore.edit { prefs ->
            prefs[PREFERENCES_KEY_SAVED_LATITUDE] = coordinate.latitude
            prefs[PREFERENCES_KEY_SAVED_LONGITUDE] = coordinate.longitude
            prefs[PREFERENCES_KEY_CONFIGURATION_CHANGED] = true
        }
    }

    override fun getSavedLocationCoordinate(): Flow<Coordinate?> =
        dataStore.data.map { prefs ->
            prefs[PREFERENCES_KEY_SAVED_LATITUDE]?.let { latitude ->
                prefs[PREFERENCES_KEY_SAVED_LONGITUDE]?.let { longitude ->
                    Coordinate(latitude = latitude, longitude = longitude)
                }
            }
        }

    override suspend fun changeLocationAsDefault() {
        dataStore.edit { prefs ->
            prefs[PREFERENCES_KEY_LOCATION_AS_DEFAULT] =
                !(prefs[PREFERENCES_KEY_LOCATION_AS_DEFAULT] ?: true)
            prefs[PREFERENCES_KEY_CONFIGURATION_CHANGED] = true
        }
    }

    override fun isLocationAsDefault(): Flow<Boolean> =
        dataStore.data.map { prefs ->
            prefs[PREFERENCES_KEY_LOCATION_AS_DEFAULT] ?: true
        }

    override suspend fun changeWeatherUnit(weatherUnit: WeatherUnit) {
        dataStore.edit { prefs ->
            prefs[PREFERENCES_KEY_WEATHER_UNIT] = weatherUnit.system
            prefs[PREFERENCES_KEY_CONFIGURATION_CHANGED] = true
        }
    }

    override fun getWeatherUnit(): Flow<WeatherUnit> =
        dataStore.data.map { prefs ->
            (prefs[PREFERENCES_KEY_WEATHER_UNIT] ?: WeatherUnit.Metric.system).toWeatherUnit()
        }

    override suspend fun configurationChangesApplied() {
        dataStore.edit { prefs ->
            prefs[PREFERENCES_KEY_CONFIGURATION_CHANGED] = false
        }
    }

    override fun isConfigurationChanged(): Flow<Boolean> =
        dataStore.data.map { prefs -> prefs[PREFERENCES_KEY_CONFIGURATION_CHANGED] ?: false }
}