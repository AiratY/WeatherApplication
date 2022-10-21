package ru.yunusov.weatherapplication.domain

interface ListAllCity {
    /**
     * Загружает список всех городов
     * */
    fun loadList(): List<String>
}
