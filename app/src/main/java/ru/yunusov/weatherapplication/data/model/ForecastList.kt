package ru.yunusov.weatherapplication.data.model

data class ForecastList(
    val id: Int,
    val cod: Long,
    var list: List<Forecast>,
    val city: City
)

/*@Entity
data class ForecastListRoom(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val cod: Long,
    var list: Forecasts
)

fun fromForecastList(forecastList: ForecastList): ForecastListRoom {
    return ForecastListRoom(forecastList.id, forecastList.cod, Forecasts(forecastList.list))
}

fun forecastListRoomToForecastList(forecastListRoom: ForecastListRoom): ForecastList {
    return ForecastList(forecastListRoom.id, forecastListRoom.cod, forecastListRoom.list.list)
}*/
