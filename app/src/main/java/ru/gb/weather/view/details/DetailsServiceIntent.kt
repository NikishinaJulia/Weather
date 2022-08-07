package ru.gb.weather.view.details

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import ru.gb.weather.BuildConfig
import ru.gb.weather.domain.City
import ru.gb.weather.model.dto.WeatherDTO
import ru.gb.weather.utils.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DetailsServiceIntent : IntentService("") {
    override fun onHandleIntent(intent: Intent?) {

        intent?.let {
            it.getParcelableExtra<City>(BUNDLE_CITY_KEY)?.let {

                val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${it.lat}&lon=${it.lon}")

                Thread {
                    var myConnection: HttpsURLConnection? = null
                    myConnection = uri.openConnection() as HttpsURLConnection
                    try {
                        myConnection.readTimeout = 5000
                        myConnection.addRequestProperty(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)

                        val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                        val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)

                        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent().apply {
                            putExtra(BUNDLE_WEATHER_DTO_KEY,weatherDTO)
                            action = WAVE
                        })

                    }catch (e:Exception){
                    }finally {
                        myConnection.disconnect()
                    }
                }.start()

            }

        }
    }
}