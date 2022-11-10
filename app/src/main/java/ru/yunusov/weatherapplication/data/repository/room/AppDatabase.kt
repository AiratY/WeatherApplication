package ru.yunusov.weatherapplication.data.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.yunusov.weatherapplication.model.WeatherForecast
import ru.yunusov.weatherapplication.other.DB_VERSION

@Database(entities = [WeatherForecast::class], version = DB_VERSION)
@TypeConverters(WeatherForecastConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun forecastDao(): ForecastDao
}
