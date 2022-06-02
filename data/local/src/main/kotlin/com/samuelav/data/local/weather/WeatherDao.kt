package com.samuelav.data.local.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samuelav.data.local.weather.dbo.WeatherOneCallDBO

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(weatherInfo: WeatherOneCallDBO)

    @Query("DELETE FROM WeatherOneCallDBO")
    suspend fun deleteAll()

    @Query("DELETE FROM WeatherOneCallDBO WHERE id NOT IN (SELECT id FROM WeatherOneCallDBO LIMIT 1)")
    suspend fun deleteAllExceptFirst()

    @Query("SELECT * FROM WeatherOneCallDBO")
    suspend fun getWeatherInfo(): List<WeatherOneCallDBO>?
}