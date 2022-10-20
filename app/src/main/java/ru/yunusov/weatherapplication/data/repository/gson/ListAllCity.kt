package ru.yunusov.weatherapplication.data.repository.gson

interface ListAllCity {
    /**
     * Загружает список всех городов
     * */
    fun loadList(): List<String>
}
