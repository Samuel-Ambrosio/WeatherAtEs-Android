package com.samuelav.features.search.ui

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
import com.samuelav.commonandroid.extensions.slideInPop
import com.samuelav.commonandroid.extensions.slideOut
import com.samuelav.commonandroid.extensions.slideOutPop
import com.samuelav.feature.details.ui.daily.DailyWeatherDetails
import com.samuelav.feature.details.ui.hourly.HourlyWeatherDetails
import com.samuelav.features.search.ui.SearchDestination.Companion.ARG_LAT
import com.samuelav.features.search.ui.SearchDestination.Companion.ARG_LON
import com.samuelav.features.search.ui.SearchDestination.Companion.ARG_POSITION
import com.samuelav.features.search.ui.main.SearchScreen
import com.samuelav.features.search.ui.searched.SearchedLocationScreen

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.searchNavigation(appState: AppState) {
    navigation(
        startDestination = SearchDestination.Main.route,
        route = Feature.Search.rootRoute
    ) {
        composable(route = SearchDestination.Main.route,) {
            SearchScreen(
                appState = appState,
                onSearchLocationClick = { lat, lon ->
                    appState.navController.navigate(
                        route = SearchDestination.SearchedLocation.createRoute(
                            args = listOf(lat.toFloat(), lon.toFloat())))
                }
            )
        }

        composable(
            route = SearchDestination.SearchedLocation.route,
            arguments = SearchDestination.SearchedLocation.arguments,
            enterTransition = { slideIn },
            exitTransition = { slideOut },
            popEnterTransition = { slideInPop },
            popExitTransition = { slideOutPop },
        ) {
            val lat =
                appState.navController.currentBackStackEntry?.arguments?.getFloat(ARG_LAT) ?: 0.0
            val lon =
                appState.navController.currentBackStackEntry?.arguments?.getFloat(ARG_LON) ?: 0.0

            SearchedLocationScreen(
                appState = appState,
                lat = lat.toDouble(),
                lon = lon.toDouble(),
                onBackClick = {
                    appState.navController.popBackStack()
                },
                onHourlyWeatherClick = { position ->
                    appState.navController.navigate(
                        route = SearchDestination.SearchedWeatherHourlyDetails.createRoute(
                            args = listOf(position)))
                },
                onDailyWeatherClick = { position ->
                    appState.navController.navigate(
                        route = SearchDestination.SearchedWeatherDailyDetails.createRoute(
                            args = listOf(position)))
                },
            )
        }

        composable(
            route = SearchDestination.SearchedWeatherHourlyDetails.route,
            arguments = SearchDestination.SearchedWeatherHourlyDetails.arguments,
            enterTransition = { slideIn },
            popExitTransition = { slideOutPop },
        ) {
            val initialPage =
                appState.navController.currentBackStackEntry?.arguments?.getInt(ARG_POSITION) ?: 0

            HourlyWeatherDetails(
                appState = appState,
                onBackClick = { appState.navController.popBackStack() },
                isSearch = true,
                initialPage = initialPage
            )
        }

        composable(
            route = SearchDestination.SearchedWeatherDailyDetails.route,
            arguments = SearchDestination.SearchedWeatherDailyDetails.arguments,
            enterTransition = { slideIn },
            popExitTransition = { slideOutPop },
        ) {
            val initialPage =
                appState.navController.currentBackStackEntry?.arguments?.getInt(ARG_POSITION) ?: 0

            DailyWeatherDetails(
                appState = appState,
                onBackClick = { appState.navController.popBackStack() },
                isSearch = true,
                initialPage = initialPage
            )
        }
    }
}

private sealed class SearchDestination(
    val rootRoute: String = Feature.Search.rootRoute,
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

    object Main: SearchDestination(childRoute = "main")
    object SearchedLocation: SearchDestination(
        childRoute = "searchedLocation",
        arguments = listOf(
            navArgument(ARG_LAT) { type = NavType.FloatType },
            navArgument(ARG_LON) { type = NavType.FloatType },
        ))
    object SearchedWeatherHourlyDetails: SearchDestination(
        childRoute = "searchedLocation/hourlyDetails",
        arguments = listOf(navArgument(ARG_POSITION) { type = NavType.IntType }))
    object SearchedWeatherDailyDetails: SearchDestination(
        childRoute = "searchedLocation/dailyDetails",
        arguments = listOf(navArgument(ARG_POSITION) { type = NavType.IntType }))

    companion object {
        internal const val ARG_LAT = "lat"
        internal const val ARG_LON = "lon"
        internal const val ARG_POSITION = "position"
    }
}