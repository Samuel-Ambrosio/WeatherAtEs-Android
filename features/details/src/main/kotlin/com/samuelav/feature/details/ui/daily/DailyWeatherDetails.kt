package com.samuelav.feature.details.ui.daily

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.samuelav.common.extensions.format
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.extensions.cardinalDirection
import com.samuelav.commonandroid.extensions.icon
import com.samuelav.commonandroid.ui.composables.base.BodyLargeBold
import com.samuelav.commonandroid.ui.composables.base.BodyLargeRegular
import com.samuelav.commonandroid.ui.composables.base.BodySmallRegular
import com.samuelav.commonandroid.ui.composables.base.Screen
import com.samuelav.commonandroid.ui.theme.AppTheme.colors
import com.samuelav.commonandroid.ui.theme.AppTheme.icons
import com.samuelav.commonandroid.ui.theme.AppTheme.spacing
import com.samuelav.data.model.weather.DailyWeatherBO
import com.samuelav.data.model.weather.WeatherUnit
import com.samuelav.feature.details.R
import com.samuelav.feature.details.ui.WeatherElement
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DailyWeatherDetails(
    appState: AppState,
    onBackClick: () -> Unit,
    isSearch: Boolean,
    initialPage: Int
) {
    var titleTopBar by remember { mutableStateOf("") }

    Screen(appState = appState, titleTopBar = titleTopBar, onBackClick = onBackClick) {
        val viewModel: DailyWeatherDetailsViewModel = getViewModel { parametersOf(isSearch) }
        val state by viewModel.state.collectAsState()

        val pagerState = rememberPagerState(initialPage = initialPage)

        when (state) {
            is DailyWeatherDetailsState.Loading ->
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            is DailyWeatherDetailsState.Success -> {
                val weatherInfo = (state as DailyWeatherDetailsState.Success).weatherInfo

                HorizontalPager(
                    count = weatherInfo.daily.size,
                    state = pagerState
                ) {
                    titleTopBar = weatherInfo.daily[currentPage].dateTime.format(
                        pattern = "EEEE, dd MMMM",
                        capitalize = true)

                    DailyWeatherDetailsContent(
                        location = weatherInfo.location,
                        weatherUnit = weatherInfo.weatherUnit,
                        dailyWeather = weatherInfo.daily[currentPage]
                    )
                }
            }
        }
    }
}

@Composable
private fun DailyWeatherDetailsContent(
    modifier: Modifier = Modifier,
    location: String,
    weatherUnit: WeatherUnit,
    dailyWeather: DailyWeatherBO
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth().padding(horizontal = spacing.s),
        columns = GridCells.Adaptive(140.dp)
    ) {
        item(span = { GridItemSpan(Int.MAX_VALUE) }) {
            DailyWeatherDetailsHeader(
                location = location,
                weatherUnit = weatherUnit,
                dailyWeather = dailyWeather)
        }

        item(span = { GridItemSpan(Int.MAX_VALUE) }) {
            BodyLargeBold(
                modifier = Modifier.fillMaxWidth().padding(vertical = spacing.m),
                text = stringResource(id = R.string.weather_detail_title),
                textAlign = TextAlign.Center
            )
        }

        weatherElements(
            weatherUnit = weatherUnit,
            dailyWeather = dailyWeather)
    }
}

@Composable
private fun DailyWeatherDetailsHeader(
    modifier: Modifier = Modifier,
    location: String,
    weatherUnit: WeatherUnit,
    dailyWeather: DailyWeatherBO
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = shapes.large,
        backgroundColor = colors.primaryVariant
    ) {
        Column(
            modifier = Modifier.padding(spacing.l),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(80.dp),
                painter = dailyWeather.state.icon().painter,
                contentDescription = null)

            BodyLargeRegular(
                text = (dailyWeather.temp.day.toInt().toString()) + " " + weatherUnit.temperatureUnit,
                color = colors.onPrimary)
            Row(
                horizontalArrangement = Arrangement.spacedBy(spacing.xs),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BodySmallRegular(
                    text = stringResource(id = R.string.weather_min_temp) + ": " + dailyWeather.temp.min.toInt().toString() + " " + weatherUnit.temperatureUnit,
                    color = colors.onPrimary)
                BodySmallRegular(
                    text = stringResource(id = R.string.weather_max_temp) + ": " + dailyWeather.temp.max.toInt().toString() + " " + weatherUnit.temperatureUnit,
                    color = colors.onPrimary)
            }

            BodyLargeBold(
                modifier = Modifier.padding(top = spacing.xs),
                text = location,
                color = colors.onPrimary)
        }
    }
}

private fun LazyGridScope.weatherElements(
    weatherUnit: WeatherUnit,
    dailyWeather: DailyWeatherBO
) {
    item {
        WeatherElement(
            modifier = Modifier.padding(spacing.xs),
            icon = icons.sunriseSunset,
            value = dailyWeather.sunriseDateTime.format("HH:mm"),
            type = stringResource(R.string.weather_sunrise),
        )
    }
    item {
        WeatherElement(
            modifier = Modifier.padding(spacing.xs),
            icon = icons.sunriseSunset,
            value = dailyWeather.sunsetDateTime.format("HH:mm"),
            type = stringResource(R.string.weather_sunset),
        )
    }
    item {
        WeatherElement(
            modifier = Modifier.padding(spacing.xs),
            icon = icons.temperature,
            value = dailyWeather.feelsLike.day.toInt().toString() + " " + weatherUnit.temperatureUnit,
            type = stringResource(R.string.weather_feels_like),
        )
    }
    item {
        WeatherElement(
            modifier = Modifier.padding(spacing.xs),
            icon = icons.airPressure,
            value = dailyWeather.pressure.toString() + " hPa",
            type = stringResource(R.string.weather_pressure),
        )
    }
    item {
        WeatherElement(
            modifier = Modifier.padding(spacing.xs),
            icon = icons.humidity,
            value = dailyWeather.humidity.toString() + " %",
            type = stringResource(R.string.weather_humidity),
        )
    }
    item {
        WeatherElement(
            modifier = Modifier.padding(spacing.xs),
            icon = icons.cloudiness,
            value = dailyWeather.clouds.toString() + " %",
            type = stringResource(R.string.weather_cloudiness),
        )
    }
    item {
        WeatherElement(
            modifier = Modifier.padding(spacing.xs),
            icon = icons.wind,
            value = dailyWeather.windSpeed.toString() + " " + weatherUnit.windUnit,
            type = stringResource(R.string.weather_wind),
        )
    }
    item {
        WeatherElement(
            modifier = Modifier.padding(spacing.xs),
            icon = icons.wind,
            value = dailyWeather.windGust.toString() + " " + weatherUnit.windUnit,
            type = stringResource(R.string.weather_wind_gust),
        )
    }
    item {
        WeatherElement(
            modifier = Modifier.padding(spacing.xs),
            icon = icons.wind,
            value = dailyWeather.windDeg.cardinalDirection(),
            type = stringResource(R.string.weather_wind_dir),
        )
    }
}