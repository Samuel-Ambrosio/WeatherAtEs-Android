package com.samuelav.feature.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samuelav.commonandroid.extensions.cardinalDirection
import com.samuelav.commonandroid.ui.composables.base.BodyMediumBold
import com.samuelav.commonandroid.ui.composables.base.BodySmallRegular
import com.samuelav.commonandroid.ui.theme.AppTheme
import com.samuelav.commonandroid.ui.theme.AppTheme.colors
import com.samuelav.commonandroid.ui.theme.AppTheme.icons
import com.samuelav.commonandroid.ui.theme.AppTheme.spacing
import com.samuelav.commonandroid.ui.theme.Icon
import com.samuelav.data.model.weather.mock.weatherOneCallBOMock
import com.samuelav.feature.details.R

@Composable
fun WeatherElement(
    modifier: Modifier = Modifier,
    icon: Icon,
    value: String,
    type: String
) {
    Row(
        modifier = modifier
            .background(color = colors.surface, shape = shapes.large)
            .padding(spacing.s),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(48.dp).padding(end = spacing.xs),
            painter = icon.painter,
            contentDescription = null,
            tint = colors.onSurface)
        Column {
            BodyMediumBold(text = value)
            BodySmallRegular(text = type)
        }
    }
}

@Preview
@Composable
private fun WeatherElementPreview() {
    AppTheme {
        WeatherElement(
            modifier = Modifier.padding(spacing.xs),
            icon = icons.wind,
            value = weatherOneCallBOMock.current.windDeg.cardinalDirection(),
            type = stringResource(R.string.weather_wind_dir),
        )
    }
}