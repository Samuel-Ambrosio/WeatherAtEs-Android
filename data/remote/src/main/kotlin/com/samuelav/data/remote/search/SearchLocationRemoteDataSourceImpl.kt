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
        val addresses = try {
            geocoder.getFromLocationName(query, 20)
        } catch (ex: Exception) {
            return Result.Failure(Error.Network)
        }

        return if (addresses.isNotEmpty()) {
            Result.Success(
                addresses.mapNotNull { address ->
                    val locality = address.locality?.let { it } ?: ""
                    val subAdminArea = address.subAdminArea?.let { if (address != null) "($it)" else it } ?: ""
                    val countryName = address.countryName?.let { ", $it" } ?: ""
                    if (locality.isNotBlank() || subAdminArea.isNotBlank()) {
                        SearchResult(
                            lat = address.latitude,
                            lon = address.longitude,
                            location = "$locality $subAdminArea $countryName")
                    } else null
                })
        } else {
            Result.Failure(Error.NotFound)
        }
    }
}