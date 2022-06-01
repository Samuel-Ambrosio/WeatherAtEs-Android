package com.samuelav.feature.details.ui.hourly

import androidx.lifecycle.viewModelScope
import com.samuelav.common.utils.fold
import com.samuelav.commonandroid.ui.base.BaseViewModel
import com.samuelav.data.model.weather.WeatherOneCallBO
import com.samuelav.domain.weather.GetWeatherUseCase
import kotlinx.coroutines.launch

internal class HourlyWeatherDetailsViewModel(
    isSearch: Boolean,
    private val getWeatherUseCase: GetWeatherUseCase
): BaseViewModel<HourlyWeatherDetailsState, Unit>(HourlyWeatherDetailsState.Loading) {
    init {
        viewModelScope.launch {
            getWeatherUseCase(refresh = false).fold(
                isLoading = { emitState(HourlyWeatherDetailsState.Loading) },
                isSuccess = { emitState(HourlyWeatherDetailsState.Success(weatherInfo = it)) },
                isFailure = {
                    // Emit command
                }
            )
        }
    }
}

internal sealed class HourlyWeatherDetailsState {
    object Loading: HourlyWeatherDetailsState()
    data class Success(val weatherInfo: WeatherOneCallBO): HourlyWeatherDetailsState()
}