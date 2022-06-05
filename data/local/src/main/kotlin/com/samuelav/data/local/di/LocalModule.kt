package com.samuelav.data.local.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.samuelav.data.local.AppRoomDatabase
import com.samuelav.data.local.preferences.PreferencesLocalDataSourceImpl
import com.samuelav.data.local.weather.WeatherLocalDataSourceImpl
import com.samuelav.data.source.preferences.PreferencesLocalDataSource
import com.samuelav.data.source.weather.WeatherLocalDataSource
import org.koin.dsl.module

val localModule = module {
    single {
        preferencesDataStore(name = "data-store").getValue(thisRef = get(), property = String::javaClass)
    }
    single<PreferencesLocalDataSource> { PreferencesLocalDataSourceImpl(dataStore = get()) }
    single { appRoomDatabaseProvider(get()) }
    single{ get<AppRoomDatabase>().getWeatherDao() }
    single<WeatherLocalDataSource> { WeatherLocalDataSourceImpl(get()) }
}

private fun appRoomDatabaseProvider(context: Context) = AppRoomDatabase.buildDatabase(context)