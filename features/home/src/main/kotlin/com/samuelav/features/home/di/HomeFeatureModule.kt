package com.samuelav.features.home.di

import com.samuelav.features.home.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeFeatureModule = module {
    viewModel {
        MainViewModel(
            getWeatherUseCase = get(),
            getLocationCoordinateUseCase = get(),
            isConfigurationChangedUseCase = get(),
            configurationChangesAppliedUseCase = get())
    }
}