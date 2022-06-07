package com.samuelav.features.search.ui.main

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.samuelav.common.utils.fold
import com.samuelav.commonandroid.extensions.handleErrorMessage
import com.samuelav.commonandroid.ui.base.BaseViewModel
import com.samuelav.data.model.search.SearchResult
import com.samuelav.domain.search.SearchLocationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class SearchScreenViewModel(
    private val searchLocationUseCase: SearchLocationUseCase
): BaseViewModel<SearchScreenState, SearchScreenCommand>(SearchScreenState()) {

    fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            emitState(state.value.copy(isLoading = true))
            searchLocationUseCase(query = query).fold(
                isLoading = { emitState(state.value.copy(isLoading = true)) },
                isSuccess = { emitState(SearchScreenState(isLoading = false, results = it)) },
                isFailure = {
                    emitState(state.value.copy(isLoading = false))
                    emitCommand(SearchScreenCommand.Failure(it.handleErrorMessage()))
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

internal sealed class SearchScreenCommand {
    data class Failure(@StringRes val messageRes: Int): SearchScreenCommand()
}