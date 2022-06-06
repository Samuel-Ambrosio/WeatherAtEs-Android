package com.samuelav.domain.di

import com.samuelav.domain.location.GetLocationCoordinateUseCase
import com.samuelav.domain.preferences.ConfigurationChangesAppliedUseCase
import com.samuelav.domain.preferences.IsConfigurationChangedUseCase
import com.samuelav.domain.preferences.SaveLocationCoordinateUseCase
import com.samuelav.domain.search.SearchLocationUseCase
import com.samuelav.domain.weather.GetSearchedWeatherUseCase
import com.samuelav.domain.weather.GetWeatherUseCase
import com.samuelav.domain.weather.SearchWeatherUseCase
import org.koin.dsl.module

val domainModule = module {
    single { GetWeatherUseCase(weatherRepository = get()) }
    single { SearchWeatherUseCase(weatherRepository = get()) }
    single { SearchLocationUseCase(searchLocationRepository = get()) }
    single { GetSearchedWeatherUseCase(weatherRepository = get()) }
    single {
        GetLocationCoordinateUseCase(
            appCommonConfiguration = get(),
            locationRepository = get(),
            preferencesRepository = get())
    }
    single { IsConfigurationChangedUseCase(preferencesRepository = get()) }
    single { ConfigurationChangesAppliedUseCase(preferencesRepository = get()) }
    single { SaveLocationCoordinateUseCase(preferencesRepository = get()) }
}