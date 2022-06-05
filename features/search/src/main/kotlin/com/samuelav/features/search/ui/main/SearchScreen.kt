package com.samuelav.features.search.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.extensions.clearFocusOnClick
import com.samuelav.commonandroid.ui.composables.base.BodyMediumRegular
import com.samuelav.commonandroid.ui.composables.base.Screen
import com.samuelav.commonandroid.ui.theme.AppTheme.colors
import com.samuelav.commonandroid.ui.theme.AppTheme.icons
import com.samuelav.commonandroid.ui.theme.AppTheme.spacing
import com.samuelav.commonandroid.ui.theme.AppTheme.typography
import com.samuelav.features.search.R
import org.koin.androidx.compose.getViewModel

@Composable
internal fun SearchScreen(
    appState: AppState,
    onSearchLocationClick: (Double, Double) -> Unit
) {
    Screen(appState = appState, titleTopBar = stringResource(R.string.nav_item_search)) {
        Column(
            modifier = Modifier.fillMaxSize().clearFocusOnClick().padding(spacing.m),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val viewModel: SearchScreenViewModel = getViewModel()
            val state by viewModel.state.collectAsState()
            var inputSearch by rememberSaveable { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = inputSearch,
                onValueChange = {
                    inputSearch = it
                    if (it.length >= 3) {
                        viewModel.search(query = it)
                    } else {
                        viewModel.resetSearch()
                    }
                },
                textStyle = typography.bodyMediumRegular,
                placeholder = {
                    BodyMediumRegular(
                        text = stringResource(id = R.string.search_bar_placeholder),
                        color = colors.onSurface.copy(ContentAlpha.medium))
                },
                trailingIcon = {
                    when {
                        state.isLoading -> CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        inputSearch.isNotBlank() || state.results.isNotEmpty() -> {
                            IconButton(onClick = {
                                inputSearch = ""
                                viewModel.resetSearch()
                            }) {
                                Icon(painter = icons.close.painter, contentDescription = null)
                            }
                        }
                        else -> {
                            Icon(painter = icons.search.painter, contentDescription = null)
                        }
                    }
                },
                shape = shapes.large
            )

            Column(modifier = Modifier.padding(horizontal = spacing.m)) {
                if (state.results.isEmpty()) {
                    BodyMediumRegular(
                        modifier = Modifier.padding(top = spacing.m),
                        text = stringResource(id = R.string.search_help_text),
                        color = colors.onSurface.copy(ContentAlpha.high),
                        textAlign = TextAlign.Center
                    )
                }

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(state.results) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSearchLocationClick(it.lat, it.lon) }
                                .background(colors.surface)
                                .padding(spacing.s)
                        ) {
                            BodyMediumRegular(text = it.location)
                        }
                    }
                }
            }
        }
    }
}