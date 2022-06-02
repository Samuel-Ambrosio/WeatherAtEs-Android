package com.samuelav.features.home.di

import android.content.Context
import android.location.LocationManager
import com.samuelav.commonandroid.utils.LocationRepository
import com.samuelav.commonandroid.utils.LocationRepositoryImpl
import com.samuelav.features.home.domain.GetLastKnownLocationUseCase
import com.samuelav.features.home.ui.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeFeatureModule = module {
    viewModel { MainViewModel(getWeatherUseCase = get(), getLastKnownLocationUseCase = get()) }
    single { androidApplication().getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    single<LocationRepository> { LocationRepositoryImpl(context = get(), locationManager = get()) }
    single { GetLastKnownLocationUseCase(locationRepository = get()) }
}