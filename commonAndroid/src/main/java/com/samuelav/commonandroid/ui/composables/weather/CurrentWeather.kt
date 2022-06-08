package com.samuelav.commonandroid.ui.composables.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
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
import com.samuelav.commonandroid.R
import com.samuelav.commonandroid.extensions.icon
import com.samuelav.commonandroid.ui.composables.base.BodyLargeBold
import com.samuelav.commonandroid.ui.composables.base.BodyLargeRegular
import com.samuelav.commonandroid.ui.composables.base.BodyMediumRegular
import com.samuelav.commonandroid.ui.theme.AppTheme
import com.samuelav.commonandroid.ui.theme.AppTheme.colors
import com.samuelav.commonandroid.ui.theme.AppTheme.icons
import com.samuelav.commonandroid.ui.theme.AppTheme.spacing
import com.samuelav.data.model.weather.CurrentWeatherBO
import com.samuelav.data.model.weather.WeatherUnit
import com.samuelav.data.model.weather.mock.weatherOneCallBOMock

@Composable
fun CurrentWeather(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    location: String,
    weatherUnit: WeatherUnit,
    currentWeather: CurrentWeatherBO?
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .placeholder(
                visible = isLoading,
                color = colors.primaryVariant,
                shape = shapes.large,
                highlight = PlaceholderHighlight.shimmer(highlightColor = colors.background)),
        shape = shapes.large,
        backgroundColor = colors.primaryVariant
    ) {
        Column(modifier = Modifier.padding(spacing.l)) {
            BodyMediumRegular(
                text = currentWeather?.dateTime?.format(
                    pattern = "EEEE, dd MMMM yyyy", capitalize = true) ?: "",
                color = colors.onPrimary)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.size(80.dp).padding(end = spacing.m),
                    painter = currentWeather?.state?.icon()?.painter ?: icons.weatherCloudy.painter,
                    contentDescription = null)
                Column {
                    BodyLargeRegular(
                        text = (currentWeather?.temp?.toInt()?.toString() ?: "") + " " + weatherUnit.temperatureUnit,
                        color = colors.onPrimary)
                    BodyLargeBold(text = location, color = colors.onPrimary)
                }
            }
            BodyMediumRegular(
                text =
                    stringResource(R.string.last_update) + " " +
                        (currentWeather?.dateTime?.format("HH:mm") ?: "-"),
                color = colors.onPrimary)
        }
    }
}

@Preview
@Composable
private fun CurrentWeatherPreview() {
    AppTheme {
        CurrentWeather(
            isLoading = false,
            location = weatherOneCallBOMock.location,
            weatherUnit = weatherOneCallBOMock.weatherUnit,
            currentWeather = weatherOneCallBOMock.current
        )
    }
}