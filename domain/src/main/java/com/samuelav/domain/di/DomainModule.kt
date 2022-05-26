package com.samuelav.domain.di

import com.samuelav.domain.weather.GetWeatherUseCase
import org.koin.dsl.module

val domainModule = module {
    single { GetWeatherUseCase(get()) }
}