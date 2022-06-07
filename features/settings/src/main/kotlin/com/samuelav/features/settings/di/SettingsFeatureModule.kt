package com.samuelav.features.settings.di

import com.samuelav.features.settings.ui.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsFeatureModule = module {
    viewModel {
        SettingsViewModel(
            changeLocationAsDefaultUseCase = get(),
            changeWeatherUnitUseCase = get(),
            isLocationAsDefaultUseCase = get(),
            getWeatherUnitUseCase = get())
    }
}