package com.samuelav.feature.details.ui.daily

import androidx.lifecycle.viewModelScope
import com.samuelav.common.utils.fold
import com.samuelav.commonandroid.ui.base.BaseViewModel
import com.samuelav.data.model.weather.WeatherOneCallBO
import com.samuelav.domain.weather.GetWeatherUseCase
import kotlinx.coroutines.launch

internal class DailyWeatherDetailsViewModel(
    isSearch: Boolean,
    private val getWeatherUseCase: GetWeatherUseCase
): BaseViewModel<DailyWeatherDetailsState, Unit>(DailyWeatherDetailsState.Loading) {
    init {
        viewModelScope.launch {
            getWeatherUseCase(refresh = false).fold(
                isLoading = { emitState(DailyWeatherDetailsState.Loading) },
                isSuccess = { emitState(DailyWeatherDetailsState.Success(weatherInfo = it)) },
                isFailure = {
                    // Emit command
                }
            )
        }
    }
}

internal sealed class DailyWeatherDetailsState {
    object Loading: DailyWeatherDetailsState()
    data class Success(val weatherInfo: WeatherOneCallBO): DailyWeatherDetailsState()
}