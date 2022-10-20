package ru.yunusov.weatherapplication.presentation.selectcity

interface CityCallback {
    /**
     * Передает выбранный город
     * @param city - название города
     * */
    fun setCity(city: String)

    /**
     * Удаляет город из сохраненных
     * @param city - название города
     * */
    fun deleteCity(city: String)
}
