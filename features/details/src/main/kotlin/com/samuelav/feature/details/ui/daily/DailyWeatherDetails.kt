package com.samuelav.feature.details.ui.daily

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
import com.samuelav.data.model.weather.DailyWeatherBO
import com.samuelav.data.model.weather.WeatherOneCallBO
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
                        weatherInfo = weatherInfo,
                        position = currentPage
                    )
                }
            }
        }
    }
}

@Composable
private fun DailyWeatherDetailsContent(
    weatherInfo: WeatherOneCallBO,
    position: Int
) {
    Column(
        modifier = Modifier.padding(horizontal = spacing.s).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        DailyWeatherDetailsHeader(
            location = weatherInfo.location,
            dailyWeather = weatherInfo.daily[position])
    }
}

@Composable
private fun DailyWeatherDetailsHeader(
    modifier: Modifier = Modifier,
    location: String,
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
                text = (dailyWeather.temp.day.toInt().toString()) + " ºC", //TODO
                color = colors.onPrimary)

            BodyLargeBold(text = location, color = colors.onPrimary)
        }
    }
}