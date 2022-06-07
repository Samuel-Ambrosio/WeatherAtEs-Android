package com.samuelav.commonandroid.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.samuelav.commonandroid.R
import com.samuelav.commonandroid.ui.theme.AppTheme.icons
import com.samuelav.data.model.weather.WeatherStateBO
import com.samuelav.data.model.weather.WeatherUnit

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

@Composable
fun Double.cardinalDirection(): String {
    val directions = listOf(
        R.string.weather_north,
        R.string.weather_north_east,
        R.string.weather_east,
        R.string.weather_south_east,
        R.string.weather_south,
        R.string.weather_south_west,
        R.string.weather_west,
        R.string.weather_north_west
    )
    val index = ((this + 22.5) / 45.0).toInt() and 7
    return stringResource(id = directions[index])
}

@Composable
fun WeatherUnit.textName() =
    stringResource(
        id = when (this) {
            is WeatherUnit.Metric -> R.string.unit_measurement_config_metric
            is WeatherUnit.Imperial -> R.string.unit_measurement_config_imperial
            is WeatherUnit.Standard -> R.string.unit_measurement_config_standard
        }
    )
