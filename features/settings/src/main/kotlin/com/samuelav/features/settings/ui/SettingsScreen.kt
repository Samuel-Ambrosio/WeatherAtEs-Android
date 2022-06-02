package com.samuelav.features.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.ui.composables.base.Heading1
import com.samuelav.commonandroid.ui.composables.base.Screen
import com.samuelav.commonandroid.ui.theme.AppTheme
import com.samuelav.features.settings.R

@Composable
internal fun SettingsScreen(appState: AppState) {
    Screen(
        appState = appState,
        titleTopBar = stringResource(id = R.string.nav_item_settings),
        topBarActions = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {}) {
                    Icon(
                        painter = AppTheme.icons.menu.painter,
                        tint = AppTheme.colors.onSurface,
                        contentDescription = null)
                }
                IconButton(onClick = {}) {
                    Icon(
                        painter = AppTheme.icons.close.painter,
                        tint = AppTheme.colors.onSurface,
                        contentDescription = null)
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Heading1(text = "Settings")
        }
    }
}