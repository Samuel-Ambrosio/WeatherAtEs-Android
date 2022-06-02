package com.samuelav.data.source.search

import com.samuelav.common.utils.Error
import com.samuelav.common.utils.Result
import com.samuelav.data.model.search.SearchResult

interface SearchLocationRemoteDataSource {
    suspend fun search(query: String): Result<Error, List<SearchResult>>
}