package com.samuelav.data.local.weather

import com.samuelav.data.local.weather.dbo.toBO
import com.samuelav.data.local.weather.dbo.toDBO
import com.samuelav.data.model.weather.WeatherOneCallBO
import com.samuelav.data.source.weather.WeatherLocalDataSource

class WeatherLocalDataSourceImpl(private val weatherDao: WeatherDao): WeatherLocalDataSource {
    override suspend fun saveWeatherInfo(weatherInfo: WeatherOneCallBO) {
        weatherDao.deleteAll()
        weatherDao.save(weatherInfo = weatherInfo.toDBO())
    }

    override suspend fun getWeatherInfo(): WeatherOneCallBO? =
        weatherDao.getWeatherInfo()?.toBO()
}