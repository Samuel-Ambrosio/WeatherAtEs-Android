package com.samuelav.features.settings.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.app.navigation.Feature

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.settingsNavigation(appState: AppState) {
    navigation(
        startDestination = SettingsDestination.Main.route,
        route = Feature.Settings.rootRoute
    ) {
        composable(route = SettingsDestination.Main.route) {
            SettingsScreen(appState = appState)
        }
    }
}

private sealed class SettingsDestination(
    val rootRoute: String = Feature.Settings.rootRoute,
    val childRoute: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    fun createRoute(args: List<Any> = emptyList()) =
        listOf(rootRoute)
            .asSequence()
            .plus(childRoute)
            .plus(args)
            .map { it.toString() }
            .filterNot { it.isBlank() }
            .joinToString("/")

    val route =
        listOf(rootRoute)
            .asSequence()
            .plus(childRoute)
            .plus(arguments.map { "{${it.name}}" })
            .joinToString("/")

    object Main: SettingsDestination(childRoute = "main")
}
