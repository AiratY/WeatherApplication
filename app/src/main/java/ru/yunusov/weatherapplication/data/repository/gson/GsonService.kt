package ru.yunusov.weatherapplication.data.repository.gson

import com.google.gson.Gson
import ru.yunusov.weatherapplication.App
import java.io.InputStreamReader

class GsonService : ListAllCity {
    override fun loadList(): List<String> {
        val assets = App.getAppContext()?.assets?.open(FILE_NAME)
        val input = InputStreamReader(assets)
        return Gson().fromJson(input, Array<String>::class.java).toList()
    }

    companion object {
        private const val FILE_NAME = "cities.json"
    }
}
