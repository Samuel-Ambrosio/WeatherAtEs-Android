package com.samuelav.feature.details.ui.daily

import androidx.lifecycle.viewModelScope
import com.samuelav.common.utils.fold
import com.samuelav.commonandroid.ui.base.BaseViewModel
import com.samuelav.data.model.weather.WeatherOneCallBO
import com.samuelav.domain.weather.GetSearchedWeatherUseCase
import com.samuelav.domain.weather.GetWeatherUseCase
import kotlinx.coroutines.launch

internal class DailyWeatherDetailsViewModel(
    isSearch: Boolean,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getSearchedWeatherUseCase: GetSearchedWeatherUseCase
): BaseViewModel<DailyWeatherDetailsState, Unit>(DailyWeatherDetailsState.Loading) {
    init {
        viewModelScope.launch {
            if (isSearch) {
                getSearchedWeatherUseCase().fold(
                    isLoading = { emitState(DailyWeatherDetailsState.Loading) },
                    isSuccess = { emitState(DailyWeatherDetailsState.Success(weatherInfo = it)) },
                    isFailure = {
                        // Emit command
                    }
                )
            } else {
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
}

internal sealed class DailyWeatherDetailsState {
    object Loading: DailyWeatherDetailsState()
    data class Success(val weatherInfo: WeatherOneCallBO): DailyWeatherDetailsState()
}