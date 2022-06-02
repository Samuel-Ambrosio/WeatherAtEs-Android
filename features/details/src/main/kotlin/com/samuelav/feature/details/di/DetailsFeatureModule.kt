package com.samuelav.feature.details.di

import com.samuelav.feature.details.ui.daily.DailyWeatherDetailsViewModel
import com.samuelav.feature.details.ui.hourly.HourlyWeatherDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsFeatureModule = module {
    viewModel { params ->
        HourlyWeatherDetailsViewModel(
            isSearch = params.get(),
            getWeatherUseCase = get(),
            getSearchedWeatherUseCase = get())
    }
    viewModel { params ->
        DailyWeatherDetailsViewModel(
            isSearch = params.get(),
            getWeatherUseCase = get(),
            getSearchedWeatherUseCase = get())
    }
}