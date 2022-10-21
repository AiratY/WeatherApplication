package ru.yunusov.weatherapplication.domain

interface SavedCities {
    /**
     * Загружает сохраненный список городов
     * @return список городов
     * */
    fun loadList(): List<String>

    /**
     * Сохраняет новый город
     * @param city - название города
     * */
    fun saveNew(city: String)

    /**
     * Удаляет город
     * @param city - название города
     * */
    fun removeCity(city: String)
}
