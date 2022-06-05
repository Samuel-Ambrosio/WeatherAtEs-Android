package com.samuelav.data.model.weather

data class WeatherOneCallBO(
    val location: String,
    val lat: Double,
    val lon: Double,
    val timeZone: String,
    val timeZoneOffset: Int,
    val current: CurrentWeatherBO,
    val hourly: List<HourlyWeatherBO>,
    val daily: List<DailyWeatherBO>,
    var weatherUnit: WeatherUnit = WeatherUnit.Metric,
)
