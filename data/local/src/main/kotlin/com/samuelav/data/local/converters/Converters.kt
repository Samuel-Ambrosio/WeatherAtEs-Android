package com.samuelav.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.samuelav.data.local.weather.dbo.*
import java.lang.reflect.Type
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? = value?.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it), ZoneId.systemDefault())
    }
    @TypeConverter
    fun localDateTimeToTimestamp(date: LocalDateTime?): Long? = date?.toEpochSecond(ZoneOffset.UTC)

    @TypeConverter
    fun hourlyWeatherListToJson(list: List<HourlyWeatherDBO>?): String = Gson().toJson(list)
    @TypeConverter
    fun jsonToHourlyWeatherList(jsonList: String): List<HourlyWeatherDBO>? = Gson().fromJson(jsonList)

    @TypeConverter
    fun dailyWeatherListToJson(list: List<DailyWeatherDBO>?): String = Gson().toJson(list)
    @TypeConverter
    fun jsonToDailyWeatherList(jsonList: String): List<DailyWeatherDBO>? = Gson().fromJson(jsonList)

    @TypeConverter
    fun weatherStateToJson(weatherStateDBO: WeatherStateDBO?): String = Gson().toJson(weatherStateDBO)
    @TypeConverter
    fun jsonToWeatherState(json: String): WeatherStateDBO? = Gson().fromJson(json)

    @TypeConverter
    fun dailyWeatherTempToJson(dailyWeatherTemp: DailyWeatherTempDBO?): String = Gson().toJson(dailyWeatherTemp)
    @TypeConverter
    fun jsonToDailyWeatherTemp(json: String): DailyWeatherTempDBO? = Gson().fromJson(json)

    @TypeConverter
    fun dailyWeatherFeelsLikeToJson(dailyWeatherFeelsLike: DailyWeatherFeelsLikeDBO?): String = Gson().toJson(dailyWeatherFeelsLike)
    @TypeConverter
    fun jsonToDailyWeatherFeelsLike(json: String): DailyWeatherFeelsLikeDBO? = Gson().fromJson(json)

    @TypeConverter
    fun currentWeatherToJson(currentWeather: CurrentWeatherDBO?): String = Gson().toJson(currentWeather)
    @TypeConverter
    fun jsonToCurrentWeather(json: String): CurrentWeatherDBO? = Gson().fromJson(json)

    @TypeConverter
    fun hourlyWeatherToJson(hourlyWeather: HourlyWeatherDBO?): String = Gson().toJson(hourlyWeather)
    @TypeConverter
    fun jsonToHourlyWeather(json: String): HourlyWeatherDBO? = Gson().fromJson(json)

    @TypeConverter
    fun dailyWeatherToJson(dailyWeather: DailyWeatherDBO?): String = Gson().toJson(dailyWeather)
    @TypeConverter
    fun jsonToDailyWeather(json: String): DailyWeatherDBO? = Gson().fromJson(json)

    private inline fun <reified T> Gson.fromJson(json: String): T = fromJson(json, genericType<T>())
    private inline fun <reified T> genericType() : Type? = object: TypeToken<T>() {}.type
}