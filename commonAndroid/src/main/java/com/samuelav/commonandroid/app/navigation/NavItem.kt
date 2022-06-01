package com.samuelav.commonandroid.app.navigation

import androidx.annotation.StringRes
import com.samuelav.commonandroid.R
import com.samuelav.commonandroid.ui.theme.AppIconography
import com.samuelav.commonandroid.ui.theme.Icon

sealed class NavItem(
    val route: String,
    val icon: Icon,
    @StringRes val title: Int,
    @StringRes val contentDescription: Int,
) {
    object Home: NavItem(route = Feature.Home.rootRoute, icon = AppIconography.home, title = R.string.nav_item_home, contentDescription = R.string.nav_item_home)
    object Search: NavItem(route = Feature.Search.rootRoute, icon = AppIconography.search, title = R.string.nav_item_search, contentDescription = R.string.nav_item_search)
    object Settings: NavItem(route = Feature.Settings.rootRoute, icon = AppIconography.settings, title = R.string.nav_item_settings, contentDescription = R.string.nav_item_settings)
}