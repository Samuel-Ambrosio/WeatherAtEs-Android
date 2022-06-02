package com.samuelav.baseproject.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.app.navigation.NavItem
import com.samuelav.features.home.ui.homeNavigation
import com.samuelav.features.search.ui.searchNavigation
import com.samuelav.features.settings.ui.settingsNavigation

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(appState: AppState, modifier: Modifier) {
    AnimatedNavHost(
        navController = appState.navController,
        startDestination = NavItem.Home.route,
        modifier = modifier
    ) {
        homeNavigation(appState = appState)

        searchNavigation(appState = appState)

        settingsNavigation(appState = appState)
    }
}