package com.samuelav.features.home.ui

import androidx.lifecycle.viewModelScope
import com.samuelav.common.utils.fold
import com.samuelav.commonandroid.ui.base.BaseViewModel
import com.samuelav.data.model.weather.WeatherOneCallBO
import com.samuelav.domain.weather.GetWeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class MainViewModel(
    private val getWeatherUseCase: GetWeatherUseCase
): BaseViewModel<MainState, Unit>(MainState()) {

    init {
        fetchWeatherInfo()
    }

    fun fetchWeatherInfo(refresh: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            emitState(state.value.copy(isLoading = true))
            getWeatherUseCase(refresh = refresh).fold(
                isLoading = { emitState(state.value.copy(isLoading = true)) },
                isSuccess = { emitState(MainState(isLoading = false, weatherInfo = it)) },
                isFailure = {
                    emitState(state.value.copy(isLoading = false))
                    // Emit command
                }
            )
        }
    }
}

internal data class MainState(
    val isLoading: Boolean = true,
    val weatherInfo: WeatherOneCallBO? = null)