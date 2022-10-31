package ru.yunusov.weatherapplication.presentation.weather

interface SelectCityCallback {
    /**
     * Запрашивает выбор нового города
     * */
    fun selectNewCity()

    /**
     * Возвращает название Города
     * */
    fun getCityName(): String?
}
