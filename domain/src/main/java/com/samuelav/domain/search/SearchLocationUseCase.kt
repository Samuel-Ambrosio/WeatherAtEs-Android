package com.samuelav.domain.search

import com.samuelav.data.repository.search.SearchLocationRepository

class SearchLocationUseCase(private val searchLocationRepository: SearchLocationRepository) {
    suspend operator fun invoke(query: String) =
        searchLocationRepository.search(query = query)
}