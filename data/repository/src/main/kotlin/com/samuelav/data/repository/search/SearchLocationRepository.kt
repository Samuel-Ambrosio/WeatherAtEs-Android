package com.samuelav.data.repository.search

import com.samuelav.common.utils.Error
import com.samuelav.common.utils.Result
import com.samuelav.data.model.search.SearchResult
import com.samuelav.data.source.search.SearchLocationRemoteDataSource

interface SearchLocationRepository {
    suspend fun search(query: String): Result<Error, List<SearchResult>>
}

class SearchLocationRepositoryImpl(
    private val searchLocationRemoteDataSource: SearchLocationRemoteDataSource
): SearchLocationRepository {
    override suspend fun search(query: String): Result<Error, List<SearchResult>> =
        searchLocationRemoteDataSource.search(query = query)
}