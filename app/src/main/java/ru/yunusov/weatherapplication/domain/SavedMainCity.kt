package ru.yunusov.weatherapplication.domain

interface SavedMainCity {
    /**
     * Загружает основной город
     * @return название города
     * */
    fun loadMainCity(): String?
}
