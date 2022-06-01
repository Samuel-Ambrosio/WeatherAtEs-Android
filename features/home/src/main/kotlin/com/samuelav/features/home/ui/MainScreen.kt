package com.samuelav.features.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.ui.composables.base.Screen
import com.samuelav.commonandroid.ui.composables.weather.CurrentWeather
import com.samuelav.commonandroid.ui.composables.weather.DailyWeather
import com.samuelav.commonandroid.ui.composables.weather.HourlyWeather
import com.samuelav.commonandroid.ui.theme.AppTheme.icons
import com.samuelav.commonandroid.ui.theme.AppTheme.spacing
import com.samuelav.data.model.weather.WeatherOneCallBO
import org.koin.androidx.compose.getViewModel


@Composable
internal fun MainScreen(
    appState: AppState,
    onHourlyWeatherClick: (Int) -> Unit,
    onDailyWeatherClick: (Int) -> Unit
) {
    Screen(appState = appState, titleTopBarContent = {
        Image(
            modifier = Modifier.size(width = 164.dp, height = 40.dp),
            painter = icons.logoTitle.painter, contentDescription = null
        )
    }) {
        val viewModel: MainViewModel = getViewModel()
        val state by viewModel.state.collectAsState()

        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

        SwipeRefresh(
            modifier = Modifier.fillMaxSize(),
            state = swipeRefreshState,
            onRefresh = { viewModel.fetchWeatherInfo(refresh = true) },
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = spacing.s)
                    .verticalScroll(rememberScrollState())
            ) {
                MainScreenContent(
                    isLoading = state.isLoading,
                    weatherInfo = state.weatherInfo,
                    onHourlyWeatherClick = onHourlyWeatherClick,
                    onDailyWeatherClick = onDailyWeatherClick)
            }
        }
    }
}

@Composable
private fun MainScreenContent(
    isLoading: Boolean,
    weatherInfo: WeatherOneCallBO?,
    onHourlyWeatherClick: (Int) -> Unit,
    onDailyWeatherClick: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        CurrentWeather(
            isLoading = isLoading,
            location = weatherInfo?.location ?: "",
            currentWeather = weatherInfo?.current)

        HourlyWeather(
            modifier = Modifier.fillMaxWidth().padding(top = spacing.s),
            isLoading = isLoading,
            hourlyWeather = weatherInfo?.hourly ?: emptyList(),
            onHourlyWeatherClick = onHourlyWeatherClick)

        DailyWeather(
            modifier = Modifier.fillMaxWidth().padding(top = spacing.s),
            isLoading = isLoading,
            dailyWeather = weatherInfo?.daily ?: emptyList(),
            onDailyWeatherClick = onDailyWeatherClick)
    }
}