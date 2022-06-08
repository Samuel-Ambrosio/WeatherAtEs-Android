package com.samuelav.commonandroid.ui.composables.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.samuelav.common.extensions.format
import com.samuelav.common.extensions.titleCase
import com.samuelav.commonandroid.R
import com.samuelav.commonandroid.extensions.icon
import com.samuelav.commonandroid.ui.composables.base.BodyLargeBold
import com.samuelav.commonandroid.ui.composables.base.BodyMediumBold
import com.samuelav.commonandroid.ui.composables.base.BodyMediumRegular
import com.samuelav.commonandroid.ui.theme.AppTheme
import com.samuelav.commonandroid.ui.theme.AppTheme.colors
import com.samuelav.commonandroid.ui.theme.AppTheme.icons
import com.samuelav.commonandroid.ui.theme.AppTheme.spacing
import com.samuelav.data.model.weather.DailyWeatherBO
import com.samuelav.data.model.weather.WeatherUnit
import com.samuelav.data.model.weather.mock.weatherOneCallBOMock

@Composable
fun DailyWeather(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    weatherUnit: WeatherUnit,
    dailyWeather: List<DailyWeatherBO>,
    onDailyWeatherClick: (Int) -> Unit,
) {
    Column(
        modifier = modifier.placeholder(
            visible = isLoading,
            color = colors.secondary,
            shape = shapes.large,
            highlight = PlaceholderHighlight.shimmer(highlightColor = colors.background))
    ) {
        BodyLargeBold(
            modifier = Modifier.padding(bottom = spacing.xs),
            text = stringResource(R.string.weather_per_day))
        Column(
            modifier = Modifier.padding(bottom = spacing.xs),
            verticalArrangement = Arrangement.spacedBy(spacing.xs)) {
            dailyWeather.forEach { daily ->
                DailyWeatherItem(
                    weatherUnit = weatherUnit,
                    dailyWeather = daily,
                    onClick = { onDailyWeatherClick(dailyWeather.indexOf(it)) })
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DailyWeatherItem(
    weatherUnit: WeatherUnit,
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
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = spacing.xs),
                    painter = dailyWeather.state.icon().painter,
                    contentDescription = null
                )
                Column {
                    BodyMediumBold(
                        text = dailyWeather.dateTime.format("EEEE, dd", capitalize = true)
                    )
                    BodyMediumRegular(
                        text = dailyWeather.state.description.titleCase()
                    )
                }
            }
            Row {
                BodyMediumBold(
                    text = dailyWeather.temp.day.toInt().toString() + " " + weatherUnit.temperatureUnit)
                Icon(
                    painter = icons.chevronRight.painter,
                    contentDescription = null,
                    tint = colors.onSurface
                )
            }
        }
    }
}

@Preview
@Composable
private fun DailyWeatherPreview() {
    AppTheme {
        DailyWeather(
            isLoading = false,
            weatherUnit = weatherOneCallBOMock.weatherUnit,
            dailyWeather = weatherOneCallBOMock.daily,
            onDailyWeatherClick = {}
        )
    }
}