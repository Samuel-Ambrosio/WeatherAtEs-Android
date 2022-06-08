package com.samuelav.feature.details.ui.hourly

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.samuelav.common.extensions.format
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.extensions.cardinalDirection
import com.samuelav.commonandroid.extensions.icon
import com.samuelav.commonandroid.ui.base.CommandHandler
import com.samuelav.commonandroid.ui.composables.base.BodyLargeBold
import com.samuelav.commonandroid.ui.composables.base.BodyLargeRegular
import com.samuelav.commonandroid.ui.composables.base.Screen
import com.samuelav.commonandroid.ui.theme.AppTheme
import com.samuelav.commonandroid.ui.theme.AppTheme.colors
import com.samuelav.commonandroid.ui.theme.AppTheme.icons
import com.samuelav.commonandroid.ui.theme.AppTheme.spacing
import com.samuelav.data.model.weather.HourlyWeatherBO
import com.samuelav.data.model.weather.WeatherUnit
import com.samuelav.data.model.weather.mock.weatherOneCallBOMock
import com.samuelav.feature.details.R
import com.samuelav.feature.details.ui.WeatherElement
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HourlyWeatherDetails(
    appState: AppState,
    onBackClick: () -> Unit,
    isSearch: Boolean,
    initialPage: Int
) {
    var titleTopBar by remember { mutableStateOf("") }

    Screen(appState = appState, titleTopBar = titleTopBar, onBackClick = onBackClick) {
        val viewModel: HourlyWeatherDetailsViewModel = getViewModel { parametersOf(isSearch) }
        val state by viewModel.state.collectAsState()
        val context = LocalContext.current

        val pagerState = rememberPagerState(initialPage = initialPage)

        CommandHandler(viewModel = viewModel) { command ->
            when (command) {
                is HourlyWeatherDetailsCommand.Failure ->
                    appState.scaffoldState.snackbarHostState
                        .showSnackbar(message = context.getString(command.messageRes))
            }
        }

        when (state) {
            is HourlyWeatherDetailsState.Loading ->
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            is HourlyWeatherDetailsState.Success -> {
                val weatherInfo = (state as HourlyWeatherDetailsState.Success).weatherInfo

                HorizontalPager(
                    count = weatherInfo.hourly.size,
                    state = pagerState
                ) {
                    titleTopBar = weatherInfo.hourly[currentPage].dateTime.format(
                        pattern = "EEEE, dd MMMM - HH:mm",
                        capitalize = true)

                    HourlyWeatherDetailsContent(
                        location = weatherInfo.location,
                        weatherUnit = weatherInfo.weatherUnit,
                        hourlyWeather = weatherInfo.hourly[currentPage])
                }
            }
        }
    }
}

@Composable
private fun HourlyWeatherDetailsContent(
    modifier: Modifier = Modifier,
    location: String,
    weatherUnit: WeatherUnit,
    hourlyWeather: HourlyWeatherBO
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth().padding(horizontal = spacing.s),
        columns = GridCells.Adaptive(140.dp)
    ) {
        item(span = { GridItemSpan(Int.MAX_VALUE) }) {
            HourlyWeatherDetailsHeader(
                location = location,
                weatherUnit = weatherUnit,
                hourlyWeather = hourlyWeather)
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
            hourlyWeather = hourlyWeather)
    }
}

@Composable
private fun HourlyWeatherDetailsHeader(
    modifier: Modifier = Modifier,
    location: String,
    weatherUnit: WeatherUnit,
    hourlyWeather: HourlyWeatherBO
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
                painter = hourlyWeather.state.icon().painter,
                contentDescription = null)

            BodyLargeRegular(
                text = (hourlyWeather.temp.toInt().toString()) + " " + weatherUnit.temperatureUnit,
                color = colors.onPrimary)

            BodyLargeBold(text = location, color = colors.onPrimary)
        }
    }
}

private fun LazyGridScope.weatherElements(
    weatherUnit: WeatherUnit,
    hourlyWeather: HourlyWeatherBO
) {
    item {
        WeatherElement(
            modifier = Modifier.padding(spacing.xs),
            icon = icons.temperature,
            value = hourlyWeather.feelsLike.toInt().toString() + " " + weatherUnit.temperatureUnit,
            type = stringResource(R.string.weather_feels_like),
        )
    }
    item {
        WeatherElement(
            modifier = Modifier.padding(spacing.xs),
            icon = icons.airPressure,
            value = hourlyWeather.pressure.toString() + " hPa",
            type = stringResource(R.string.weather_pressure),
        )
    }
    item {
        WeatherElement(
            modifier = Modifier.padding(spacing.xs),
            icon = icons.humidity,
            value = hourlyWeather.humidity.toString() + " %",
            type = stringResource(R.string.weather_humidity),
        )
    }
    item {
        WeatherElement(
            modifier = Modifier.padding(spacing.xs),
            icon = icons.cloudiness,
            value = hourlyWeather.clouds.toString() + " %",
            type = stringResource(R.string.weather_cloudiness),
        )
    }
    item {
        WeatherElement(
            modifier = Modifier.padding(spacing.xs),
            icon = icons.wind,
            value = hourlyWeather.windSpeed.toString() + " " + weatherUnit.windUnit,
            type = stringResource(R.string.weather_wind),
        )
    }
    item {
        WeatherElement(
            modifier = Modifier.padding(spacing.xs),
            icon = icons.wind,
            value = hourlyWeather.windGust.toString() + " " + weatherUnit.windUnit,
            type = stringResource(R.string.weather_wind_gust),
        )
    }
    item {
        WeatherElement(
            modifier = Modifier.padding(spacing.xs),
            icon = icons.wind,
            value = hourlyWeather.windDeg.cardinalDirection(),
            type = stringResource(R.string.weather_wind_dir),
        )
    }
    item {
        WeatherElement(
            modifier = Modifier.padding(spacing.xs),
            icon = icons.visibility,
            value = (hourlyWeather.visibility/1000).toString() + " km",
            type = stringResource(R.string.weather_visibility),
        )
    }
}

@Preview
@Composable
private fun HourlyWeatherDetailsContentPreview() {
    AppTheme {
        HourlyWeatherDetailsContent(
            location = weatherOneCallBOMock.location,
            weatherUnit = weatherOneCallBOMock.weatherUnit,
            hourlyWeather = weatherOneCallBOMock.hourly.first()
        )
    }
}