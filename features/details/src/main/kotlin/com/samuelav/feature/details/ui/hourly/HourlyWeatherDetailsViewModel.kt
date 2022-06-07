package com.samuelav.feature.details.ui.hourly

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.samuelav.common.utils.fold
import com.samuelav.commonandroid.extensions.handleErrorMessage
import com.samuelav.commonandroid.ui.base.BaseViewModel
import com.samuelav.data.model.weather.WeatherOneCallBO
import com.samuelav.domain.weather.GetSearchedWeatherUseCase
import com.samuelav.domain.weather.GetWeatherUseCase
import kotlinx.coroutines.launch

internal class HourlyWeatherDetailsViewModel(
    isSearch: Boolean,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getSearchedWeatherUseCase: GetSearchedWeatherUseCase
): BaseViewModel<HourlyWeatherDetailsState, HourlyWeatherDetailsCommand>(HourlyWeatherDetailsState.Loading) {
    init {
        viewModelScope.launch {
            if (isSearch) {
                getSearchedWeatherUseCase().fold(
                    isLoading = { emitState(HourlyWeatherDetailsState.Loading) },
                    isSuccess = { emitState(HourlyWeatherDetailsState.Success(weatherInfo = it)) },
                    isFailure = {
                        emitCommand(HourlyWeatherDetailsCommand.Failure(it.handleErrorMessage()))
                    }
                )
            } else {
                getWeatherUseCase().fold(
                    isLoading = { emitState(HourlyWeatherDetailsState.Loading) },
                    isSuccess = { emitState(HourlyWeatherDetailsState.Success(weatherInfo = it)) },
                    isFailure = {
                        emitCommand(HourlyWeatherDetailsCommand.Failure(it.handleErrorMessage()))
                    }
                )
            }
        }
    }
}

internal sealed class HourlyWeatherDetailsState {
    object Loading: HourlyWeatherDetailsState()
    data class Success(val weatherInfo: WeatherOneCallBO): HourlyWeatherDetailsState()
}

internal sealed class HourlyWeatherDetailsCommand {
    data class Failure(@StringRes val messageRes: Int): HourlyWeatherDetailsCommand()
}