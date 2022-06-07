package com.samuelav.features.home.ui

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.ui.base.CommandHandler
import com.samuelav.commonandroid.ui.composables.base.BodyLargeBold
import com.samuelav.commonandroid.ui.composables.base.BodyMediumRegular
import com.samuelav.commonandroid.ui.composables.base.ButtonText
import com.samuelav.commonandroid.ui.composables.base.Screen
import com.samuelav.commonandroid.ui.composables.weather.CurrentWeather
import com.samuelav.commonandroid.ui.composables.weather.DailyWeather
import com.samuelav.commonandroid.ui.composables.weather.HourlyWeather
import com.samuelav.commonandroid.ui.theme.AppTheme.colors
import com.samuelav.commonandroid.ui.theme.AppTheme.icons
import com.samuelav.commonandroid.ui.theme.AppTheme.spacing
import com.samuelav.data.model.weather.WeatherOneCallBO
import com.samuelav.data.model.weather.WeatherUnit
import com.samuelav.features.home.R
import org.koin.androidx.compose.getViewModel

@Composable
internal fun MainScreen(
    appState: AppState,
    onHourlyWeatherClick: (Int) -> Unit,
    onDailyWeatherClick: (Int) -> Unit
) {
    Screen(appState = appState, titleTopBarContent = {
        Image(
            modifier = Modifier.size(width = 164.dp, height = 40.dp),
            painter = icons.logoTitle.painter, contentDescription = null
        )
    }) {
        val viewModel: MainViewModel = getViewModel()
        val state by viewModel.state.collectAsState()
        val context = LocalContext.current

        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

        CommandHandler(viewModel = viewModel) { command ->
            when (command) {
                is MainCommand.Failure ->
                    appState.scaffoldState.snackbarHostState
                        .showSnackbar(message = context.getString(command.messageRes))
            }
        }

        LocationPermissionManagement(
            onFetchWeatherInfo = { viewModel.fetchWeatherInfo(refresh = it) })

        SwipeRefresh(
            modifier = Modifier.fillMaxSize(),
            state = swipeRefreshState,
            onRefresh = { viewModel.fetchWeatherInfo(refresh = true) },
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = spacing.s)
                    .verticalScroll(rememberScrollState())
            ) {
                MainScreenContent(
                    isLoading = state.isLoading,
                    weatherInfo = state.weatherInfo,
                    onHourlyWeatherClick = onHourlyWeatherClick,
                    onDailyWeatherClick = onDailyWeatherClick)
            }
        }
    }
}

@Composable
private fun MainScreenContent(
    isLoading: Boolean,
    weatherInfo: WeatherOneCallBO?,
    onHourlyWeatherClick: (Int) -> Unit,
    onDailyWeatherClick: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        CurrentWeather(
            isLoading = isLoading,
            location = weatherInfo?.location ?: "",
            weatherUnit = weatherInfo?.weatherUnit ?: WeatherUnit.Metric,
            currentWeather = weatherInfo?.current)

        HourlyWeather(
            modifier = Modifier.fillMaxWidth().padding(top = spacing.s),
            isLoading = isLoading,
            weatherUnit = weatherInfo?.weatherUnit ?: WeatherUnit.Metric,
            hourlyWeather = weatherInfo?.hourly ?: emptyList(),
            onHourlyWeatherClick = onHourlyWeatherClick)

        DailyWeather(
            modifier = Modifier.fillMaxWidth().padding(top = spacing.s),
            isLoading = isLoading,
            weatherUnit = weatherInfo?.weatherUnit ?: WeatherUnit.Metric,
            dailyWeather = weatherInfo?.daily ?: emptyList(),
            onDailyWeatherClick = onDailyWeatherClick)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun LocationPermissionManagement(
    onFetchWeatherInfo: (refresh: Boolean) -> Unit
) {
    val locationPermissionState =
        rememberMultiplePermissionsState(listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ))
    var showLocationRationaleDialog by rememberSaveable { mutableStateOf(false) }
    var permissionsRequestLaunched by rememberSaveable { mutableStateOf(false) }
    var weatherFetched by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(
        key1 = locationPermissionState.permissions[0].status,
        key2 = locationPermissionState.permissions[1].status
    ) {
        when {
            locationPermissionState.allPermissionsGranted -> {
                if (!weatherFetched) {
                    onFetchWeatherInfo(permissionsRequestLaunched)
                    weatherFetched = true
                }
            }
            locationPermissionState.shouldShowRationale -> showLocationRationaleDialog = true
            else -> {
                permissionsRequestLaunched = true
                onFetchWeatherInfo(false)
                locationPermissionState.launchMultiplePermissionRequest()
            }
        }
    }

    if (showLocationRationaleDialog) {
        AlertDialog(
            onDismissRequest = { showLocationRationaleDialog = false },
            buttons = {
                Row(
                    modifier = Modifier.padding(start = spacing.l, end = spacing.l, bottom = spacing.m),
                    horizontalArrangement = Arrangement.spacedBy(spacing.xs),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            showLocationRationaleDialog = false
                            if (!permissionsRequestLaunched) onFetchWeatherInfo(false)
                        },
                        shape = shapes.large,
                    ) {
                        ButtonText(
                            text = stringResource(id = R.string.activate_location_rationale_cancel))
                    }
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            locationPermissionState.launchMultiplePermissionRequest()
                            showLocationRationaleDialog = false
                        },
                        shape = shapes.large
                    ) {
                        ButtonText(
                            text = stringResource(id = R.string.activate_location_rationale_confirm),
                            color = colors.onPrimary)
                    }
                }
            },
            title = {
                BodyLargeBold(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.activate_location_rationale_title),
                    textAlign = TextAlign.Center)
            },
            text = {
                BodyMediumRegular(
                    text = stringResource(id = R.string.activate_location_rationale_description))
            },
            shape = shapes.large,
            backgroundColor = colors.surface
        )
    }
}