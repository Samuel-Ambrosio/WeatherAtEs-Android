package com.samuelav.features.home.ui

import androidx.lifecycle.viewModelScope
import com.samuelav.common.utils.fold
import com.samuelav.commonandroid.ui.base.BaseViewModel
import com.samuelav.data.model.weather.WeatherOneCallBO
import com.samuelav.domain.location.GetLocationCoordinateUseCase
import com.samuelav.domain.preferences.ConfigurationChangesAppliedUseCase
import com.samuelav.domain.preferences.IsConfigurationChangedUseCase
import com.samuelav.domain.weather.GetWeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class MainViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getLocationCoordinateUseCase: GetLocationCoordinateUseCase,
    isConfigurationChangedUseCase: IsConfigurationChangedUseCase,
    configurationChangesAppliedUseCase: ConfigurationChangesAppliedUseCase,
): BaseViewModel<MainState, Unit>(MainState()) {

    init {
        isConfigurationChangedUseCase().onEach { isChanged ->
            if (isChanged) {
                fetchWeatherInfo(refresh = true)
                viewModelScope.launch { configurationChangesAppliedUseCase() }
            }
        }
    }

    fun fetchWeatherInfo(refresh: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            emitState(state.value.copy(isLoading = true))
            val coordinate = getLocationCoordinateUseCase()

            getWeatherUseCase(
                lat = coordinate.latitude,
                lon = coordinate.longitude,
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