package com.samuelav.data.source.weather

import com.samuelav.data.model.weather.WeatherOneCallBO

interface WeatherLocalDataSource {
    suspend fun saveWeatherInfo(weatherInfo: WeatherOneCallBO)
    suspend fun getWeatherInfo(): WeatherOneCallBO?
}