package ru.yunusov.weatherapplication.data.repository.room

import androidx.room.Room
import ru.yunusov.weatherapplication.App
import ru.yunusov.weatherapplication.other.DB_NAME

object RoomService {
    private val db = App.getAppContext()
        ?.let { context ->
            Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .build()
        } ?: throw IllegalStateException()

    fun getDB() = db.forecastDao()
}
