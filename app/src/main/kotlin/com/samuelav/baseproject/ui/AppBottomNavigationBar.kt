package com.samuelav.baseproject.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.extensions.navigatePoppingUpToStartDestination
import com.samuelav.commonandroid.ui.theme.AppTheme.colors

@Composable
fun AppBottomNavigationBar(appState: AppState) {
    BottomNavigation(
        backgroundColor = colors.surface,
        contentColor = colors.primary
    ) {
        val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        appState.appConfiguration.navItems.forEach { navItem ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = navItem.icon.painter,
                        contentDescription = stringResource(id = navItem.contentDescription))
                },
                selected = currentRoute?.contains(navItem.route) == true,
                onClick = {
                    appState.navController.navigatePoppingUpToStartDestination(route = navItem.route)
                }
            )
        }
    }
}