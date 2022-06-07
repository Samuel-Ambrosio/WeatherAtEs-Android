package com.samuelav.domain.di

import com.samuelav.domain.location.GetLocationCoordinateUseCase
import com.samuelav.domain.preferences.*
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
    single { ChangeLocationAsDefaultUseCase(preferencesRepository = get()) }
    single { ChangeWeatherUnitUseCase(preferencesRepository = get()) }
    single { GetWeatherUnitUseCase(preferencesRepository = get()) }
    single { IsLocationAsDefaultUseCase(preferencesRepository = get()) }
}