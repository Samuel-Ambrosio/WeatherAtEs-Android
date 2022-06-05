package com.samuelav.data.repository.di

import com.samuelav.data.repository.location.LocationRepository
import com.samuelav.data.repository.location.LocationRepositoryImpl
import com.samuelav.data.repository.preferences.PreferencesRepository
import com.samuelav.data.repository.preferences.PreferencesRepositoryImpl
import com.samuelav.data.repository.search.SearchLocationRepository
import com.samuelav.data.repository.search.SearchLocationRepositoryImpl
import com.samuelav.data.repository.weather.WeatherRepository
import com.samuelav.data.repository.weather.WeatherRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<WeatherRepository> {
        WeatherRepositoryImpl(
            appCommonConfiguration = get(),
            weatherLocalDataSource = get(),
            weatherRemoteDataSource = get(),
            preferencesLocalDataSource = get())
    }
    single<SearchLocationRepository> {
        SearchLocationRepositoryImpl(searchLocationRemoteDataSource = get())
    }
    single<LocationRepository> { LocationRepositoryImpl(locationDataSource = get()) }
    single<PreferencesRepository> { PreferencesRepositoryImpl(preferencesLocalDataSource = get()) }
}