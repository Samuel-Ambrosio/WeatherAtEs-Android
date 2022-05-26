package com.samuelav.data.repository.di

import com.samuelav.data.repository.weather.WeatherRepository
import com.samuelav.data.repository.weather.WeatherRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
}