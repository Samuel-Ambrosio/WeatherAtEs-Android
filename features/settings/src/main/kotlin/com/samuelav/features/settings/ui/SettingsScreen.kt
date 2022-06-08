package com.samuelav.features.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.extensions.textName
import com.samuelav.commonandroid.ui.composables.base.BodyMediumBold
import com.samuelav.commonandroid.ui.composables.base.BodyMediumRegular
import com.samuelav.commonandroid.ui.composables.base.BodySmallRegular
import com.samuelav.commonandroid.ui.composables.base.Screen
import com.samuelav.commonandroid.ui.composables.components.HorizontalSeparator
import com.samuelav.commonandroid.ui.theme.AppTheme
import com.samuelav.commonandroid.ui.theme.AppTheme.colors
import com.samuelav.commonandroid.ui.theme.AppTheme.icons
import com.samuelav.commonandroid.ui.theme.AppTheme.spacing
import com.samuelav.commonandroid.ui.theme.AppTheme.typography
import com.samuelav.data.model.weather.WeatherUnit
import com.samuelav.data.model.weather.mock.weatherOneCallBOMock
import com.samuelav.features.settings.R
import org.koin.androidx.compose.getViewModel

@Composable
internal fun SettingsScreen(appState: AppState) {
    Screen(
        appState = appState,
        titleTopBar = stringResource(id = R.string.nav_item_settings),
    ) {
        val viewModel: SettingsViewModel = getViewModel()
        val state by viewModel.state.collectAsState()

        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (settings, footer) = createRefs()

            ConfigurationList(
                modifier = Modifier.constrainAs(settings) {
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    top.linkTo(parent.top, margin = 16.dp)
                    width = Dimension.fillToConstraints
                },
                state = state,
                onLocationByDefaultChange = viewModel::changeLocationAsDefault,
                onWeatherUnitSelected = viewModel::changeWeatherUnit
            )

            Footer(
                modifier = Modifier.constrainAs(footer) {
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                })
        }
    }
}

@Composable
private fun ConfigurationList(
    modifier: Modifier,
    state: SettingsState,
    onLocationByDefaultChange: () -> Unit,
    onWeatherUnitSelected: (WeatherUnit) -> Unit,
) {
    var isDropdownMenuExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .background(color = colors.surface, shape = shapes.large)
            .padding(spacing.m),
        verticalArrangement = Arrangement.spacedBy(spacing.xxs)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BodyMediumRegular(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.use_always_location_config))
            Switch(
                checked = state.isLocationByDefault,
                onCheckedChange = { onLocationByDefaultChange() },
                colors = SwitchDefaults.colors(checkedThumbColor = colors.primary)
            )
        }

        HorizontalSeparator()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BodyMediumRegular(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.unit_measurement_config))

            Row {
                Row(
                    modifier = Modifier.clickable {
                        isDropdownMenuExpanded = !isDropdownMenuExpanded
                    },
                    horizontalArrangement = Arrangement.spacedBy(spacing.xxs),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BodyMediumBold(text = state.weatherUnit.textName())
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter =
                            if (isDropdownMenuExpanded) icons.chevronUp.painter
                            else icons.chevronDown.painter,
                        contentDescription = null)
                }

                DropdownMenu(
                    expanded = isDropdownMenuExpanded,
                    onDismissRequest = { isDropdownMenuExpanded = false }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            isDropdownMenuExpanded = !isDropdownMenuExpanded
                            onWeatherUnitSelected(WeatherUnit.Metric)
                        }
                    ) {
                        BodySmallRegular(
                            text = stringResource(id = R.string.unit_measurement_config_metric))
                    }
                    DropdownMenuItem(
                        onClick = {
                            isDropdownMenuExpanded = !isDropdownMenuExpanded
                            onWeatherUnitSelected(WeatherUnit.Imperial)
                        }
                    ) {
                        BodySmallRegular(
                            text = stringResource(id = R.string.unit_measurement_config_imperial))
                    }
                    DropdownMenuItem(
                        onClick = {
                            isDropdownMenuExpanded = !isDropdownMenuExpanded
                            onWeatherUnitSelected(WeatherUnit.Standard)
                        }
                    ) {
                        BodySmallRegular(
                            text = stringResource(id = R.string.unit_measurement_config_standard))
                    }
                }
            }
        }
    }
}

@Composable
private fun Footer(
    modifier: Modifier
) {
    val uriHandler = LocalUriHandler.current
    val dataProviderText = buildAnnotatedString {
        append(stringResource(id = R.string.data_provider_text) + " ")
        pushStringAnnotation(tag = "link", annotation = stringResource(id = R.string.data_provider_link))
        withStyle(style = SpanStyle(color = colors.primary)) {
            append(stringResource(id = R.string.data_provider_text_link))
        }
        pop()
    }
    val developedByText = buildAnnotatedString {
        append(stringResource(id = R.string.developed_by_text) + " ")
        pushStringAnnotation(tag = "link", annotation = stringResource(id = R.string.developed_by_link))
        withStyle(style = SpanStyle(color = colors.primary)) {
            append(stringResource(id = R.string.developed_by_text_link))
        }
        pop()
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing.xs)
    ) {
        ClickableText(
            text = dataProviderText,
            style = typography.bodySmallRegular
        ) { offset ->
            dataProviderText
                .getStringAnnotations(tag = "link", start = offset, end = offset)
                .firstOrNull()?.let { uriHandler.openUri(it.item) }
        }
        ClickableText(
            text = developedByText,
            style = typography.bodySmallRegular
        ) { offset ->
            developedByText
                .getStringAnnotations(tag = "link", start = offset, end = offset)
                .firstOrNull()?.let { uriHandler.openUri(it.item) }
        }
    }
}

@Preview
@Composable
private fun ConfigurationListPreview() {
    AppTheme {
        ConfigurationList(
            modifier = Modifier.fillMaxWidth(),
            state =
                SettingsState(
                    isLocationByDefault = true,
                    weatherUnit = weatherOneCallBOMock.weatherUnit),
            onLocationByDefaultChange = {},
            onWeatherUnitSelected = {}
        )
    }
}

@Preview
@Composable
private fun FooterPreview() {
    AppTheme {
        Footer(modifier = Modifier.fillMaxWidth())
    }
}