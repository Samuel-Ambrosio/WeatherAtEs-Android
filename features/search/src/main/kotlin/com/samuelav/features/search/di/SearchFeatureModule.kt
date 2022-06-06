package com.samuelav.features.search.di

import com.samuelav.features.search.ui.main.SearchScreenViewModel
import com.samuelav.features.search.ui.searched.SearchedLocationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchFeatureModule = module {
    viewModel { SearchScreenViewModel(get()) }
    viewModel { params ->
        SearchedLocationViewModel(
            lat = params[0],
            lon = params[1],
            searchWeatherUseCase = get(),
            saveLocationCoordinateUseCase = get())
    }
}