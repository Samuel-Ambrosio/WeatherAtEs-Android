package com.samuelav.feature.details.ui.hourly

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.samuelav.common.extensions.format
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.extensions.icon
import com.samuelav.commonandroid.ui.composables.base.BodyLargeBold
import com.samuelav.commonandroid.ui.composables.base.BodyLargeRegular
import com.samuelav.commonandroid.ui.composables.base.Screen
import com.samuelav.commonandroid.ui.theme.AppTheme.colors
import com.samuelav.commonandroid.ui.theme.AppTheme.spacing
import com.samuelav.data.model.weather.HourlyWeatherBO
import com.samuelav.data.model.weather.WeatherOneCallBO
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

        val pagerState = rememberPagerState(initialPage = initialPage)

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
                        weatherInfo = weatherInfo,
                        position = currentPage)
                }
            }
        }
    }
}

@Composable
private fun HourlyWeatherDetailsContent(
    weatherInfo: WeatherOneCallBO,
    position: Int
) {
    Column(
        modifier = Modifier.padding(horizontal = spacing.s).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        HourlyWeatherDetailsHeader(
            location = weatherInfo.location,
            hourlyWeather = weatherInfo.hourly[position])
    }
}

@Composable
private fun HourlyWeatherDetailsHeader(
    modifier: Modifier = Modifier,
    location: String,
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
                text = (hourlyWeather.temp.toInt().toString()) + " ºC", //TODO
                color = colors.onPrimary)

            BodyLargeBold(text = location, color = colors.onPrimary)
        }
    }
}