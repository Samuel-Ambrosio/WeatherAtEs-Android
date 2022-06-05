package com.samuelav.commonandroid.ui.composables.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.samuelav.common.extensions.format
import com.samuelav.commonandroid.R
import com.samuelav.commonandroid.extensions.icon
import com.samuelav.commonandroid.ui.composables.base.BodyLargeBold
import com.samuelav.commonandroid.ui.composables.base.BodyMediumBold
import com.samuelav.commonandroid.ui.composables.base.BodyMediumRegular
import com.samuelav.commonandroid.ui.theme.AppTheme.colors
import com.samuelav.commonandroid.ui.theme.AppTheme.spacing
import com.samuelav.data.model.weather.HourlyWeatherBO
import com.samuelav.data.model.weather.WeatherUnit

@Composable
fun HourlyWeather(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    weatherUnit: WeatherUnit,
    hourlyWeather: List<HourlyWeatherBO>,
    onHourlyWeatherClick: (Int) -> Unit,
) {
    Column(
        modifier = modifier.placeholder(
            visible = isLoading,
            color = colors.surface,
            shape = shapes.small,
            highlight = PlaceholderHighlight.shimmer(highlightColor = colors.background))
    ) {
        BodyLargeBold(
            modifier = Modifier.padding(bottom = spacing.xs),
            text = stringResource(R.string.weather_per_hour)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.xs)
        ) {
            items(hourlyWeather, key = { it.dateTime }) { hourly ->
                HourlyWeatherItem(
                    weatherUnit = weatherUnit,
                    hourlyWeather = hourly,
                    onClick = { onHourlyWeatherClick(hourlyWeather.indexOf(it)) })
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HourlyWeatherItem(
    weatherUnit: WeatherUnit,
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
                contentDescription = null
            )
            BodyMediumBold(
                text = hourlyWeather.temp.toInt().toString() + " " + weatherUnit.temperatureUnit)
            BodyMediumRegular(text = hourlyWeather.dateTime.format("HH:mm"))
        }
    }
}