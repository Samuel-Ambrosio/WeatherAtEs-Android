package com.samuelav.features.home.ui

import androidx.lifecycle.viewModelScope
import com.samuelav.common.utils.fold
import com.samuelav.commonandroid.ui.base.BaseViewModel
import com.samuelav.data.model.weather.WeatherOneCallBO
import com.samuelav.domain.weather.GetWeatherUseCase
import kotlinx.coroutines.launch

internal class MainViewModel(
    private val getWeatherUseCase: GetWeatherUseCase
): BaseViewModel<MainState, Unit>(MainState.Loading) {
    init {
        fetchWeatherInfo()
    }

    fun fetchWeatherInfo(refresh: Boolean = false) {
        viewModelScope.launch {
            emitState(MainState.Loading)
            getWeatherUseCase(refresh = refresh).fold(
                isLoading = { emitState(MainState.Loading) },
                isSuccess = { emitState(MainState.Success(it)) },
                isFailure = { emitState(MainState.Failure) }
            )
        }
    }
}

internal sealed class MainState {
    object Loading: MainState()
    data class Success(val weatherInfo: WeatherOneCallBO): MainState()
    object Failure: MainState()
}