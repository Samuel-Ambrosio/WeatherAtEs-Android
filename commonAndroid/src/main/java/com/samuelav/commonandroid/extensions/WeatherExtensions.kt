package com.samuelav.commonandroid.extensions

import androidx.compose.runtime.Composable
import com.samuelav.commonandroid.ui.theme.AppTheme.icons
import com.samuelav.data.model.weather.WeatherStateBO

@Composable
fun WeatherStateBO.icon() =
    when (icon) {
        "01d" -> icons.weatherClearDay
        "01n" -> icons.weatherClearNight
        "02d" -> icons.weatherMostlyClearDay
        "02n" -> icons.weatherMostlyClearNight
        "03d" -> icons.weatherMostlyCloudyDay
        "03n" -> icons.weatherMostlyCloudyNight
        "04d" -> icons.weatherCloudy
        "04n" -> icons.weatherCloudy
        "09d" -> icons.weatherChanceRain
        "09n" -> icons.weatherChanceRain
        "10d" -> icons.weatherRain
        "10n" -> icons.weatherRain
        "11d" -> icons.weatherStorm
        "11n" -> icons.weatherStorm
        "13d" -> icons.weatherSnow
        "13n" -> icons.weatherSnow
        "50d" -> icons.weatherFog
        "50n" -> icons.weatherFog
        else -> icons.weatherCloudy
    }