package com.samuelav.features.search.ui.searched

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.samuelav.common.utils.fold
import com.samuelav.commonandroid.extensions.handleErrorMessage
import com.samuelav.commonandroid.ui.base.BaseViewModel
import com.samuelav.data.model.location.Coordinate
import com.samuelav.data.model.weather.WeatherOneCallBO
import com.samuelav.domain.preferences.SaveLocationCoordinateUseCase
import com.samuelav.domain.weather.SearchWeatherUseCase
import com.samuelav.features.search.R
import kotlinx.coroutines.launch

internal class SearchedLocationViewModel(
    private val lat: Double,
    private val lon: Double,
    private val searchWeatherUseCase: SearchWeatherUseCase,
    private val saveLocationCoordinateUseCase: SaveLocationCoordinateUseCase,
): BaseViewModel<SearchedLocationState, SearchedLocationCommand>(SearchedLocationState()) {
    init {
        viewModelScope.launch {
            searchWeatherUseCase(lat = lat, lon = lon, refresh = true).fold(
                isLoading = { emitState(state.value.copy(isLoading = true)) },
                isSuccess = { emitState(SearchedLocationState(isLoading = false, weatherInfo = it)) },
                isFailure = {
                    emitState(state.value.copy(isLoading = false))
                    emitCommand(SearchedLocationCommand.Failure(it.handleErrorMessage()))
                }
            )
        }
    }

    fun saveLocationCoordinate() {
        viewModelScope.launch {
            saveLocationCoordinateUseCase(coordinate = Coordinate(latitude = lat, longitude = lon))
            emitCommand(SearchedLocationCommand.LocationCoordinateSaved())
        }
    }
}

internal data class SearchedLocationState(
    val isLoading: Boolean = true,
    val weatherInfo: WeatherOneCallBO? = null
)

internal sealed class SearchedLocationCommand {
    data class Failure(@StringRes val messageRes: Int): SearchedLocationCommand()
    data class LocationCoordinateSaved(
        @StringRes val messageRes: Int = R.string.location_changed_message
    ): SearchedLocationCommand()
}