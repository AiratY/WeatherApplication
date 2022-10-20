package ru.yunusov.weatherapplication.data.repository.shared

interface SavedMainCity {
    /**
     * Загружает основной город
     * @return название города
     * */
    fun loadMainCity(): String?
}
