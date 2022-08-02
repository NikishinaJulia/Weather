package ru.gb.weather.utils

import com.google.gson.Gson
import ru.gb.weather.BuildConfig
import ru.gb.weather.model.dto.WeatherDTO
import ru.gb.weather.view.details.OnResponse
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object WeatherLoader {
    const val WEATHER_API_KEY = "X-Yandex-API-Key"

    fun requestFirstVariant(lat: Double, lon: Double, onResponse: OnResponse) {
        val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")
        var myConnection: HttpURLConnection? = null

        myConnection = uri.openConnection() as HttpURLConnection
        myConnection.readTimeout = 5000
        myConnection.addRequestProperty(WEATHER_API_KEY, BuildConfig.WEATHER_API_KEY)
        Thread {
            try {
                val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                val fromJson = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
                onResponse.onResponse(fromJson)
            }catch (th : Throwable) {
                onResponse.onFailure(th)
            }finally {
                myConnection.disconnect()
            }
        }.start()
    }

    fun requestSecondVariant(lat: Double, lon: Double, block: (weather: WeatherDTO) -> Unit) {
        val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")
        var myConnection: HttpURLConnection? = null

        myConnection = uri.openConnection() as HttpURLConnection
        myConnection.readTimeout = 5000
        myConnection.addRequestProperty(WEATHER_API_KEY, BuildConfig.WEATHER_API_KEY)
        Thread {
            val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
            val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
            block(weatherDTO)
        }.start()
    }
}