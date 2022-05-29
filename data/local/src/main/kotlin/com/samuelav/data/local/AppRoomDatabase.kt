package com.samuelav.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.samuelav.data.local.converters.Converters
import com.samuelav.data.local.weather.WeatherDao
import com.samuelav.data.local.weather.dbo.WeatherOneCallDBO

@Database(
    entities = [WeatherOneCallDBO::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppRoomDatabase: RoomDatabase() {
    companion object {
        private const val DATABASE_NAME = "RoomDatabase.db"

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppRoomDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
    }
    abstract fun getWeatherDao(): WeatherDao
}