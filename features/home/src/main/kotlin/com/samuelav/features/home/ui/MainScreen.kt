package com.samuelav.features.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.samuelav.common.extensions.format
import com.samuelav.common.extensions.titleCase
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.extensions.icon
import com.samuelav.commonandroid.ui.composables.*
import com.samuelav.commonandroid.ui.theme.AppTheme.colors
import com.samuelav.commonandroid.ui.theme.AppTheme.icons
import com.samuelav.commonandroid.ui.theme.AppTheme.spacing
import com.samuelav.data.model.weather.DailyWeatherBO
import com.samuelav.data.model.weather.HourlyWeatherBO
import com.samuelav.data.model.weather.WeatherOneCallBO
import org.koin.androidx.compose.get


@Composable
internal fun MainScreen(appState: AppState) {
    Screen(appState = appState, isTopBarVisible = false) {
        val viewModel: MainViewModel = get()
        val state by viewModel.state.collectAsState()

        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.fetchWeatherInfo(refresh = true)  },
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier =
                    Modifier.padding(horizontal = spacing.s).verticalScroll(rememberScrollState())
            ) {
                when (state) {
                    is MainState.Loading ->
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    is MainState.Success -> {
                        ContentSuccess(
                            weatherInfo = (state as MainState.Success).weatherInfo,
                            onHourlyWeatherClick = {},
                            onDailyWeatherClick = {})
                    }
                    is MainState.Failure -> Heading1(text = "Error")
                }
            }
        }
    }
}

@Composable
private fun ContentSuccess(
    weatherInfo: WeatherOneCallBO,
    onHourlyWeatherClick: (HourlyWeatherBO) -> Unit,
    onDailyWeatherClick: (DailyWeatherBO) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Image(
            modifier =
                Modifier
                    .padding(spacing.s)
                    .size(width = 164.dp, height = 40.dp)
                    .align(Alignment.CenterHorizontally),
            painter = icons.logoTitle.painter, contentDescription = null)

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = shapes.large,
            backgroundColor = Color(0xFF4F7FFA)) {
            Column(modifier = Modifier.padding(spacing.s)) {
                BodyMediumRegular(
                    text = weatherInfo.current.dateTime.format(
                        pattern = "EEEE, dd MMMM yyyy", capitalize = true),
                    color = colors.onPrimary)
                Row {

                }
                BodyMediumRegular(
                    text = "Última actualización: " + weatherInfo.current.dateTime.format("HH:mm"),
                    color = colors.onPrimary)
            }
        }

        BodyLargeBold(
            modifier = Modifier.padding(top = spacing.s, bottom = spacing.xs),
            text = "Tiempo por horas")
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.xs)) {
            items(weatherInfo.hourly, key = { it.dateTime }) { hourlyWeather ->
                HourlyWeatherItem(hourlyWeather = hourlyWeather, onClick = onHourlyWeatherClick)
            }
        }

        BodyLargeBold(
            modifier = Modifier.padding(top = spacing.s, bottom = spacing.xs),
            text = "Diario")
        Column(
            modifier = Modifier.padding(bottom = spacing.xs),
            verticalArrangement = Arrangement.spacedBy(spacing.xs)) {
            weatherInfo.daily.forEach { dailyWeather ->
                DailyWeatherItem(dailyWeather = dailyWeather, onClick = onDailyWeatherClick)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HourlyWeatherItem(
    hourlyWeather: HourlyWeatherBO,
    onClick: (HourlyWeatherBO) -> Unit
) {
    Card(
        onClick = { onClick(hourlyWeather) },
        shape = shapes.small,
        backgroundColor = colors.surface
    ) {
        Column(
            modifier = Modifier.padding(spacing.s),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacing.xs)
        ) {
            Image(
                modifier = Modifier.size(40.dp),
                painter = hourlyWeather.state.icon().painter,
                contentDescription = null)
            BodyMediumBold(text = hourlyWeather.temp.toInt().toString() + " ºC")
            BodyMediumRegular(text = hourlyWeather.dateTime.format("HH:mm"))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DailyWeatherItem(
    dailyWeather: DailyWeatherBO,
    onClick: (DailyWeatherBO) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onClick(dailyWeather) },
        shape = shapes.large,
        backgroundColor = colors.secondary
    ) {
        Row(
            modifier = Modifier.padding(spacing.s),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Image(
                    modifier = Modifier.size(40.dp),
                    painter = dailyWeather.state.icon().painter,
                    contentDescription = null)
                Column {
                    BodyMediumBold(
                        text = dailyWeather.dateTime.format("EEEE, dd", capitalize = true))
                    BodyMediumRegular(
                        text = dailyWeather.state.description.titleCase())
                }
            }
            Row {
                BodyMediumBold(text = dailyWeather.temp.day.toInt().toString() + " ºC")
                Icon(
                    painter = icons.chevronRight.painter,
                    contentDescription = null,
                    tint = colors.onSurface)
            }
        }
    }
}