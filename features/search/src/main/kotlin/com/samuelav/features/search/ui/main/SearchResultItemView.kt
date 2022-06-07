package com.samuelav.features.search.ui.main

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AbstractComposeView
import com.samuelav.commonandroid.ui.composables.base.BodyMediumRegular
import com.samuelav.commonandroid.ui.composables.components.HorizontalSeparator
import com.samuelav.commonandroid.ui.theme.AppTheme
import com.samuelav.commonandroid.ui.theme.AppTheme.colors
import com.samuelav.commonandroid.ui.theme.AppTheme.spacing
import com.samuelav.data.model.search.SearchResult

internal class SearchResultItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    var searchResult by mutableStateOf<SearchResult?>(null)
    var onClick by mutableStateOf<(Double, Double) -> Unit>({_,_ ->})

    @Composable
    override fun Content() {
        AppTheme {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { searchResult?.let { onClick(it.lat, it.lon) } }
                    .background(colors.surface)

            ) {
                Column {
                    BodyMediumRegular(
                        modifier = Modifier.padding(spacing.s),
                        text = searchResult?.location ?: "")
                    HorizontalSeparator()
                }
            }
        }
    }
}