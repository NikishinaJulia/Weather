package ru.gb.weather.model

import ru.gb.weather.domain.Weather
import ru.gb.weather.domain.getRussianCities
import ru.gb.weather.domain.getWorldCities
import ru.gb.weather.viewmodel.AppState

class RepositoryRemoteImpl: SingleResultRepository {

    override fun getWeather(lat: Double, lon: Double): Weather {

        return Weather()
    }
}