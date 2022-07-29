package ru.gb.weather.model

import ru.gb.weather.domain.Weather

class RepositoryRemoteImpl : SingleResultRepository {

    override fun getWeather(lat: Double, lon: Double): Weather {

        return Weather()
    }
}