package ru.gb.weather.model

import ru.gb.weather.domain.Weather

class RepositoryLocalImpl: Repository{
    override fun getListWeather(): List<Weather> {
        return listOf(Weather())
    }

    override fun getWeather(lat: Double, lon: Double): Weather {
        return Weather()
    }
}