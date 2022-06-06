package com.samuelav.features.search.ui.searched

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.ui.base.CommandHandler
import com.samuelav.commonandroid.ui.composables.base.Screen
import com.samuelav.commonandroid.ui.composables.weather.CurrentWeather
import com.samuelav.commonandroid.ui.composables.weather.DailyWeather
import com.samuelav.commonandroid.ui.composables.weather.HourlyWeather
import com.samuelav.commonandroid.ui.theme.AppTheme.colors
import com.samuelav.commonandroid.ui.theme.AppTheme.icons
import com.samuelav.commonandroid.ui.theme.AppTheme.spacing
import com.samuelav.data.model.weather.WeatherUnit
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
    val viewModel: SearchedLocationViewModel = getViewModel { parametersOf(lat, lon) }
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    CommandHandler(viewModel = viewModel) { command ->
        when (command) {
            is SearchedLocationCommand.Failure ->
                appState.scaffoldState.snackbarHostState
                    .showSnackbar(message = context.getString(command.messageRes))
            is SearchedLocationCommand.LocationCoordinateSaved ->
                appState.scaffoldState.snackbarHostState
                    .showSnackbar(message = context.getString(command.messageRes))
        }
    }

    Screen(
        appState = appState,
        titleTopBar = stringResource(R.string.nav_item_search),
        onBackClick = onBackClick,
        topBarActions = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.saveLocationCoordinate() }) {
                    Icon(
                        painter = icons.saveLocation.painter,
                        tint = colors.onSurface,
                        contentDescription = null)
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = spacing.s)
                .verticalScroll(rememberScrollState())
        ) {
            CurrentWeather(
                isLoading = state.isLoading,
                location = state.weatherInfo?.location ?: "",
                weatherUnit = state.weatherInfo?.weatherUnit ?: WeatherUnit.Metric,
                currentWeather = state.weatherInfo?.current)

            HourlyWeather(
                modifier = Modifier.fillMaxWidth().padding(top = spacing.s),
                isLoading = state.isLoading,
                weatherUnit = state.weatherInfo?.weatherUnit ?: WeatherUnit.Metric,
                hourlyWeather = state.weatherInfo?.hourly ?: emptyList(),
                onHourlyWeatherClick = onHourlyWeatherClick)

            DailyWeather(
                modifier = Modifier.fillMaxWidth().padding(top = spacing.s),
                isLoading = state.isLoading,
                weatherUnit = state.weatherInfo?.weatherUnit ?: WeatherUnit.Metric,
                dailyWeather = state.weatherInfo?.daily ?: emptyList(),
                onDailyWeatherClick = onDailyWeatherClick)
        }
    }
}