package com.samuelav.features.home.ui

import androidx.lifecycle.viewModelScope
import com.samuelav.common.utils.fold
import com.samuelav.commonandroid.ui.base.BaseViewModel
import com.samuelav.data.model.weather.WeatherOneCallBO
import com.samuelav.domain.location.GetLastKnownLocationUseCase
import com.samuelav.domain.weather.GetWeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class MainViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getLastKnownLocationUseCase: GetLastKnownLocationUseCase
): BaseViewModel<MainState, Unit>(MainState()) {

    fun fetchWeatherInfo(refresh: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            emitState(state.value.copy(isLoading = true))
            val lastKnownLocation = getLastKnownLocationUseCase()
            getWeatherUseCase(
                lat = lastKnownLocation?.latitude,
                lon = lastKnownLocation?.longitude,
                refresh = refresh
            ).fold(
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