package com.samuelav.commonandroid.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.samuelav.commonandroid.R

/**
 *  [Icons reference](https://fonts.google.com/icons?icon.style=Rounded)
 */
object AppIconography {
    val home: Icon = Icon(resId = R.drawable.ic_home)
    val search: Icon = Icon(resId = R.drawable.ic_search)
    val settings: Icon = Icon(resId = R.drawable.ic_settings)
    val menu: Icon = Icon(resId = R.drawable.ic_menu)
    val close: Icon = Icon(resId = R.drawable.ic_close)
    val chevronLeft: Icon = Icon(resId = R.drawable.ic_chevron_left)
    val chevronRight: Icon = Icon(resId = R.drawable.ic_chevron_right)
    val chevronUp: Icon = Icon(resId = R.drawable.ic_chevron_up)
    val chevronDown: Icon = Icon(resId = R.drawable.ic_chevron_down)
    val arrowBack: Icon = Icon(resId = R.drawable.ic_arrow_back)
    val arrowForward: Icon = Icon(resId = R.drawable.ic_arrow_forward)
    val logoTitle: Icon = Icon(resId = R.drawable.ic_app_logo_title)
    val weatherClearDay: Icon = Icon(resId = R.drawable.ic_weather_clear_day)
    val weatherClearNight: Icon = Icon(resId = R.drawable.ic_weather_clear_night)
    val weatherMostlyClearDay: Icon = Icon(resId = R.drawable.ic_weather_mostly_clear_day)
    val weatherMostlyClearNight: Icon = Icon(resId = R.drawable.ic_weather_mostly_clear_night)
    val weatherMostlyCloudyDay: Icon = Icon(resId = R.drawable.ic_weather_mostly_cloudy_day)
    val weatherMostlyCloudyNight: Icon = Icon(resId = R.drawable.ic_weather_mostly_cloudy_night)
    val weatherCloudy: Icon = Icon(resId = R.drawable.ic_weather_cloudy)
    val weatherChanceRain: Icon = Icon(resId = R.drawable.ic_weather_chance_rain)
    val weatherRain: Icon = Icon(resId = R.drawable.ic_weather_rain)
    val weatherStorm: Icon = Icon(resId = R.drawable.ic_weather_storm)
    val weatherSnow: Icon = Icon(resId = R.drawable.ic_weather_snow)
    val weatherFog: Icon = Icon(resId = R.drawable.ic_weather_fog)
}

data class Icon(@DrawableRes val resId: Int) {
    val painter
        @Composable get() = painterResource(id = resId)
}