package com.samuelav.features.search.ui.searched

import androidx.lifecycle.viewModelScope
import com.samuelav.common.utils.fold
import com.samuelav.commonandroid.ui.base.BaseViewModel
import com.samuelav.data.model.weather.WeatherOneCallBO
import com.samuelav.domain.weather.SearchWeatherUseCase
import kotlinx.coroutines.launch

internal class SearchedLocationViewModel(
    lat: Double,
    lon: Double,
    private val searchWeatherUseCase: SearchWeatherUseCase
): BaseViewModel<SearchedLocationState, Unit>(SearchedLocationState()) {
    init {
        viewModelScope.launch {
            searchWeatherUseCase(lat = lat, lon = lon, refresh = true).fold(
                isLoading = { emitState(state.value.copy(isLoading = true)) },
                isSuccess = { emitState(SearchedLocationState(isLoading = false, weatherInfo = it)) },
                isFailure = {
                    // Emit command
                }
            )
        }
    }
}

internal data class SearchedLocationState(
    val isLoading: Boolean = true,
    val weatherInfo: WeatherOneCallBO? = null
)