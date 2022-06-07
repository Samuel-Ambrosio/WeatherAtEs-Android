package com.samuelav.features.settings.ui

import androidx.lifecycle.viewModelScope
import com.samuelav.commonandroid.ui.base.BaseViewModel
import com.samuelav.data.model.weather.WeatherUnit
import com.samuelav.domain.preferences.ChangeLocationAsDefaultUseCase
import com.samuelav.domain.preferences.ChangeWeatherUnitUseCase
import com.samuelav.domain.preferences.GetWeatherUnitUseCase
import com.samuelav.domain.preferences.IsLocationAsDefaultUseCase
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class SettingsViewModel(
    private val changeLocationAsDefaultUseCase: ChangeLocationAsDefaultUseCase,
    private val changeWeatherUnitUseCase: ChangeWeatherUnitUseCase,
    isLocationAsDefaultUseCase: IsLocationAsDefaultUseCase,
    getWeatherUnitUseCase: GetWeatherUnitUseCase,
): BaseViewModel<SettingsState, Unit>(SettingsState()) {

    init {
        isLocationAsDefaultUseCase()
            .combine(getWeatherUnitUseCase()) { isLocationAsDefault, weatherUnit ->
                Pair(isLocationAsDefault, weatherUnit)
            }.onEach {
                emitState(SettingsState(isLocationByDefault = it.first, weatherUnit = it.second))
            }.launchIn(viewModelScope)
    }

    fun changeLocationAsDefault() = viewModelScope.launch { changeLocationAsDefaultUseCase() }
    fun changeWeatherUnit(weatherUnit: WeatherUnit) = viewModelScope.launch {
        changeWeatherUnitUseCase(weatherUnit = weatherUnit)
    }
}

internal data class SettingsState(
    val isLocationByDefault: Boolean = true,
    val weatherUnit: WeatherUnit = WeatherUnit.Metric
)