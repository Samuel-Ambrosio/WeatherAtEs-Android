package com.samuelav.features.search.ui.main

import androidx.lifecycle.viewModelScope
import com.samuelav.common.utils.fold
import com.samuelav.commonandroid.ui.base.BaseViewModel
import com.samuelav.data.model.search.SearchResult
import com.samuelav.domain.search.SearchLocationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class SearchScreenViewModel(
    private val searchLocationUseCase: SearchLocationUseCase
): BaseViewModel<SearchScreenState, Unit>(SearchScreenState()) {

    fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            emitState(state.value.copy(isLoading = true))
            searchLocationUseCase(query = query).fold(
                isLoading = { emitState(state.value.copy(isLoading = true)) },
                isSuccess = { emitState(SearchScreenState(isLoading = false, results = it)) },
                isFailure = {
                    emitState(state.value.copy(isLoading = false))
                    // Emit command
                }
            )
        }
    }

    fun resetSearch() {
        emitState(SearchScreenState())
    }
}

internal data class SearchScreenState(
    val isLoading: Boolean = false,
    val results: List<SearchResult> = emptyList()
)