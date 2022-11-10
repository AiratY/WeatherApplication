package ru.yunusov.weatherapplication.data.repository.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import ru.yunusov.weatherapplication.model.WeatherForecast

@Dao
interface ForecastDao {
    @Query("SELECT * FROM weatherforecast WHERE cityName = :city")
    fun getForecast(city: String): LiveData<WeatherForecast>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecast(weatherForecast: WeatherForecast): Completable
}
