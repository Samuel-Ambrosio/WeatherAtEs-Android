package com.samuelav.features.home.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.app.navigation.Feature
import com.samuelav.commonandroid.extensions.slideIn
import com.samuelav.commonandroid.extensions.slideOutPop
import com.samuelav.feature.details.ui.daily.DailyWeatherDetails
import com.samuelav.feature.details.ui.hourly.HourlyWeatherDetails
import com.samuelav.features.home.ui.HomeDestination.Companion.ARG_POSITION

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.homeNavigation(appState: AppState) {
    navigation(
        startDestination = HomeDestination.Main.route,
        route = Feature.Home.rootRoute
    ) {
        composable(
            route = HomeDestination.Main.route,

        ) {
            MainScreen(
                appState = appState,
                onHourlyWeatherClick = { position ->
                    appState.navController.navigate(
                        route = HomeDestination.HourlyDetails.createRoute(args = listOf(position)))
                },
                onDailyWeatherClick = { position ->
                    appState.navController.navigate(
                        route = HomeDestination.DailyDetails.createRoute(args = listOf(position)))
                },
            )
        }

        composable(
            route = HomeDestination.HourlyDetails.route,
            arguments = HomeDestination.HourlyDetails.arguments,
            enterTransition = { slideIn },
            popExitTransition = { slideOutPop },
        ) {
            val initialPage =
                appState.navController.currentBackStackEntry?.arguments?.getInt(ARG_POSITION) ?: 0

            HourlyWeatherDetails(
                appState = appState,
                onBackClick = { appState.navController.popBackStack() },
                isSearch = false,
                initialPage = initialPage
            )
        }

        composable(
            route = HomeDestination.DailyDetails.route,
            arguments = HomeDestination.DailyDetails.arguments,
            enterTransition = { slideIn },
            popExitTransition = { slideOutPop },
        ) {
            val initialPage =
                appState.navController.currentBackStackEntry?.arguments?.getInt(ARG_POSITION) ?: 0

            DailyWeatherDetails(
                appState = appState,
                onBackClick = { appState.navController.popBackStack() },
                isSearch = false,
                initialPage = initialPage
            )
        }
    }
}

private sealed class HomeDestination(
    val rootRoute: String = Feature.Home.rootRoute,
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

    object Main: HomeDestination(childRoute = "main")
    object HourlyDetails: HomeDestination(
        childRoute = "hourlyDetails",
        arguments = listOf(navArgument(ARG_POSITION) { type = NavType.IntType }))
    object DailyDetails: HomeDestination(
        childRoute = "dailyDetails",
        arguments = listOf(navArgument(ARG_POSITION) { type = NavType.IntType }))

    companion object {
        internal const val ARG_POSITION = "position"
    }
}