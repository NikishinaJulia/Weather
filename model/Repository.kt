package ru.gb.weather.model

import ru.gb.weather.domain.Weather
import ru.gb.weather.viewmodel.AppState

interface Repository {
    fun getListWeather(): List<Weather>
    fun getWeather( lat: Double, lon: Double): Weather


}