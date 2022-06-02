package com.samuelav.data.remote.search

import android.location.Geocoder
import com.samuelav.common.utils.Error
import com.samuelav.common.utils.Result
import com.samuelav.data.model.search.SearchResult
import com.samuelav.data.source.search.SearchLocationRemoteDataSource

class SearchLocationRemoteDataSourceImpl(
    private val geocoder: Geocoder
): SearchLocationRemoteDataSource {
    override suspend fun search(query: String): Result<Error, List<SearchResult>> {
        val addresses = geocoder.getFromLocationName(query, 10)
        return if (addresses.isNotEmpty()) {
            Result.Success(
                addresses.map { address ->
                    val locality = address.locality?.let { it } ?: ""
                    val subAdminArea = address.subAdminArea?.let { "($it)" } ?: ""
                    val countryName = address.countryName?.let { ", $it" } ?: ""
                    SearchResult(
                        lat = address.latitude,
                        lon = address.longitude,
                        location = "$locality $subAdminArea $countryName")
                })
        } else {
            Result.Failure(Error.NotFound)
        }
    }
}