package com.samuelav.features.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.ui.composables.Heading1
import com.samuelav.commonandroid.ui.composables.Screen
import com.samuelav.data.model.weather.WeatherOneCallBO
import com.samuelav.features.home.R
import org.koin.androidx.compose.get

@Composable
internal fun MainScreen(appState: AppState) {
    Screen(appState = appState, titleTopBar = stringResource(id = R.string.nav_item_home)) {
        val viewModel: MainViewModel = get()
        val state by viewModel.state.collectAsState()

        when (state) {
            is MainState.Loading -> LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            is MainState.Success -> {
                ContentSuccess(
                    weatherInfo = (state as MainState.Success).weatherInfo,
                    onRefresh = { viewModel.fetchWeatherInfo(refresh = true)  }
                )
            }
            is MainState.Failure -> Heading1(text = "Error")
        }
    }
}

@Composable
private fun ContentSuccess(
    weatherInfo: WeatherOneCallBO,
    onRefresh: () -> Unit
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = onRefresh,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Heading1(text = weatherInfo.current.dateTime.toString())
        }
    }
}