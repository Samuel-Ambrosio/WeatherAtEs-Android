package com.samuelav.data.remote.weather

import com.samuelav.common.utils.Error
import com.samuelav.common.utils.Result
import com.samuelav.data.remote.weather.dto.WeatherOneCallDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherWs {
    @GET("data/2.5/onecall")
    suspend fun getWeatherOneCall(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("lang") lang: String,
    ): Result<Error, WeatherOneCallDTO>
}