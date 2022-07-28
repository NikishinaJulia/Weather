package ru.gb.weather.utils

import com.google.gson.Gson
import ru.gb.weather.model.dto.WeatherDTO
import ru.gb.weather.view.details.OnResponse
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object WeatherLoader {

    fun requestFirstVariant(lat: Double, lon: Double, onResponse: OnResponse){
        val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")
        var myConnection: HttpURLConnection? = null

        myConnection = uri.openConnection() as HttpURLConnection
        myConnection.readTimeout = 5000
        myConnection.addRequestProperty("X-Yandex-API-Key","71602cbc-db15-4026-b9cb-87f4e535ed04")
        Thread{
            val reader = BufferedReader(InputStreamReader(myConnection.inputStream))

            val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)

            onResponse.onResponse(weatherDTO)
        }.start()
    }

    fun requestSecondVariant(lat: Double,lon: Double,block:(weather: WeatherDTO)->Unit){
        val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")
        var myConnection: HttpURLConnection? = null

        myConnection = uri.openConnection() as HttpURLConnection
        myConnection.readTimeout = 5000
        myConnection.addRequestProperty("X-Yandex-API-Key","71602cbc-db15-4026-b9cb-87f4e535ed04")
        Thread{
            val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
            val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
            block(weatherDTO)
        }.start()
    }
}