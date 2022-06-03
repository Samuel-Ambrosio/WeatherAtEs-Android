package com.samuelav.domain.di

import com.samuelav.domain.location.GetLastKnownLocationUseCase
import com.samuelav.domain.search.SearchLocationUseCase
import com.samuelav.domain.weather.GetSearchedWeatherUseCase
import com.samuelav.domain.weather.GetWeatherUseCase
import com.samuelav.domain.weather.SearchWeatherUseCase
import org.koin.dsl.module

val domainModule = module {
    single { GetWeatherUseCase(get()) }
    single { SearchWeatherUseCase(get()) }
    single { SearchLocationUseCase(get()) }
    single { GetSearchedWeatherUseCase(get()) }
    single { GetLastKnownLocationUseCase(get()) }
}