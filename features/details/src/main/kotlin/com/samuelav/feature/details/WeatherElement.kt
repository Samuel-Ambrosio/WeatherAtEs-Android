package com.samuelav.feature.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.samuelav.commonandroid.ui.composables.base.BodyMediumBold
import com.samuelav.commonandroid.ui.composables.base.BodyMediumRegular
import com.samuelav.commonandroid.ui.theme.AppTheme.colors
import com.samuelav.commonandroid.ui.theme.AppTheme.spacing
import com.samuelav.commonandroid.ui.theme.Icon

@Composable
fun WeatherElement(
    modifier: Modifier = Modifier,
    icon: Icon,
    value: String,
    type: String
) {
    Row(
        modifier = modifier
            .background(color = colors.surface, shape = shapes.small)
            .padding(spacing.l)
    ) {
        Icon(
            modifier = Modifier.size(24.dp).padding(end = spacing.xs),
            painter = icon.painter,
            contentDescription = null)
        Column {
            BodyMediumBold(text = value)
            BodyMediumRegular(text = type)
        }
    }
}