package com.samuelav.features.search.ui.searched

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.ui.composables.base.Screen
import com.samuelav.commonandroid.ui.composables.weather.CurrentWeather
import com.samuelav.commonandroid.ui.composables.weather.DailyWeather
import com.samuelav.commonandroid.ui.composables.weather.HourlyWeather
import com.samuelav.commonandroid.ui.theme.AppTheme
import com.samuelav.features.search.R
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun SearchedLocationScreen(
    appState: AppState,
    lat: Double,
    lon: Double,
    onBackClick: () -> Unit,
    onHourlyWeatherClick: (Int) -> Unit,
    onDailyWeatherClick: (Int) -> Unit
) {
    Screen(
        appState = appState,
        titleTopBar = stringResource(R.string.nav_item_search),
        onBackClick = onBackClick
    ) {
        val viewModel: SearchedLocationViewModel = getViewModel { parametersOf(lat, lon) }
        val state by viewModel.state.collectAsState()

        Column(
            modifier = Modifier
                .padding(horizontal = AppTheme.spacing.s)
                .verticalScroll(rememberScrollState())
        ) {
            CurrentWeather(
                isLoading = state.isLoading,
                location = state.weatherInfo?.location ?: "",
                currentWeather = state.weatherInfo?.current)

            HourlyWeather(
                modifier = Modifier.fillMaxWidth().padding(top = AppTheme.spacing.s),
                isLoading = state.isLoading,
                hourlyWeather = state.weatherInfo?.hourly ?: emptyList(),
                onHourlyWeatherClick = onHourlyWeatherClick)

            DailyWeather(
                modifier = Modifier.fillMaxWidth().padding(top = AppTheme.spacing.s),
                isLoading = state.isLoading,
                dailyWeather = state.weatherInfo?.daily ?: emptyList(),
                onDailyWeatherClick = onDailyWeatherClick)
        }
    }
}