package com.samuelav.data.repository.weather

import com.samuelav.common.app.AppCommonConfiguration
import com.samuelav.common.utils.Error
import com.samuelav.common.utils.Result
import com.samuelav.data.model.weather.WeatherOneCallBO
import com.samuelav.data.model.weather.WeatherUnit
import com.samuelav.data.repository.utils.CacheRepositoryResponse
import com.samuelav.data.source.preferences.PreferencesLocalDataSource
import com.samuelav.data.source.weather.WeatherLocalDataSource
import com.samuelav.data.source.weather.WeatherRemoteDataSource
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDateTime

interface WeatherRepository {
    suspend fun getWeather(lat: Double?, lon: Double?, refresh: Boolean): Result<Error, WeatherOneCallBO>
    suspend fun searchWeather(lat: Double, lon: Double, refresh: Boolean): Result<Error, WeatherOneCallBO>
    suspend fun getSearchedWeather(): Result<Error, WeatherOneCallBO>
}

class WeatherRepositoryImpl(
    private val appCommonConfiguration: AppCommonConfiguration,
    private val weatherLocalDataSource: WeatherLocalDataSource,
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
    private val preferencesLocalDataSource: PreferencesLocalDataSource,
): WeatherRepository {
    override suspend fun getWeather(
        lat: Double?,
        lon: Double?,
        refresh: Boolean
    ): Result<Error, WeatherOneCallBO> {
        val weatherUnit =
            preferencesLocalDataSource.getWeatherUnit().firstOrNull() ?: WeatherUnit.Metric

        return object : CacheRepositoryResponse<WeatherOneCallBO>() {
            override fun shouldFetchFromRemote(dataFromLocal: WeatherOneCallBO?): Boolean {
                val nowLocalDateTime = LocalDateTime.now()
                val nextNeededUpdateDateTime =
                    dataFromLocal?.current?.dateTime?.plusHours(appCommonConfiguration.minHoursToRefreshWeatherData)

                return refresh || dataFromLocal == null || nextNeededUpdateDateTime?.isBefore(nowLocalDateTime) == true
            }

            override suspend fun loadFromLocal(): WeatherOneCallBO? =
                weatherLocalDataSource.getWeatherInfo()

            override suspend fun loadFromRemote(): Result<Error, WeatherOneCallBO> =
                weatherRemoteDataSource.getWeather(
                    lat = lat ?: appCommonConfiguration.defaultLat,
                    lon = lon ?: appCommonConfiguration.defaultLon,
                    units = weatherUnit.system)

            override suspend fun saveRemoteResult(dataFromRemote: WeatherOneCallBO) {
                weatherLocalDataSource.saveWeatherInfo(
                    weatherInfo = dataFromRemote.apply { this.weatherUnit = weatherUnit })
            }
        }.execute()
    }

    override suspend fun searchWeather(
        lat: Double,
        lon: Double,
        refresh: Boolean
    ): Result<Error, WeatherOneCallBO> {
        val weatherUnit = preferencesLocalDataSource.getWeatherUnit().firstOrNull() ?: WeatherUnit.Metric

        return object : CacheRepositoryResponse<WeatherOneCallBO>() {
            override fun shouldFetchFromRemote(dataFromLocal: WeatherOneCallBO?): Boolean =
                refresh || dataFromLocal == null

            override suspend fun loadFromLocal(): WeatherOneCallBO? =
                weatherLocalDataSource.getSearchedWeatherInfo()

            override suspend fun loadFromRemote(): Result<Error, WeatherOneCallBO> =
                weatherRemoteDataSource.getWeather(
                    lat = lat,
                    lon = lon,
                    units = weatherUnit.system)

            override suspend fun saveRemoteResult(dataFromRemote: WeatherOneCallBO) {
                weatherLocalDataSource.saveSearchedWeatherInfo(
                    weatherInfo = dataFromRemote.apply { this.weatherUnit = weatherUnit })
            }
        }.execute()
    }

    override suspend fun getSearchedWeather(): Result<Error, WeatherOneCallBO> {
        val searchedWeatherInfo = weatherLocalDataSource.getSearchedWeatherInfo()
        return if (searchedWeatherInfo == null) {
            Result.Failure(Error.NotFound)
        } else {
            Result.Success(searchedWeatherInfo)
        }
    }
}