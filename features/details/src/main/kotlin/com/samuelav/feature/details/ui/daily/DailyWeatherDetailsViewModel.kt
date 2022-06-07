package com.samuelav.feature.details.ui.daily

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.samuelav.common.utils.fold
import com.samuelav.commonandroid.extensions.handleErrorMessage
import com.samuelav.commonandroid.ui.base.BaseViewModel
import com.samuelav.data.model.weather.WeatherOneCallBO
import com.samuelav.domain.weather.GetSearchedWeatherUseCase
import com.samuelav.domain.weather.GetWeatherUseCase
import kotlinx.coroutines.launch

internal class DailyWeatherDetailsViewModel(
    isSearch: Boolean,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getSearchedWeatherUseCase: GetSearchedWeatherUseCase
): BaseViewModel<DailyWeatherDetailsState, DailyWeatherDetailsCommand>(DailyWeatherDetailsState.Loading) {
    init {
        viewModelScope.launch {
            if (isSearch) {
                getSearchedWeatherUseCase().fold(
                    isLoading = { emitState(DailyWeatherDetailsState.Loading) },
                    isSuccess = { emitState(DailyWeatherDetailsState.Success(weatherInfo = it)) },
                    isFailure = {
                        emitCommand(DailyWeatherDetailsCommand.Failure(it.handleErrorMessage()))
                    }
                )
            } else {
                getWeatherUseCase().fold(
                    isLoading = { emitState(DailyWeatherDetailsState.Loading) },
                    isSuccess = { emitState(DailyWeatherDetailsState.Success(weatherInfo = it)) },
                    isFailure = {
                        emitCommand(DailyWeatherDetailsCommand.Failure(it.handleErrorMessage()))
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

internal sealed class DailyWeatherDetailsCommand {
    data class Failure(@StringRes val messageRes: Int): DailyWeatherDetailsCommand()
}