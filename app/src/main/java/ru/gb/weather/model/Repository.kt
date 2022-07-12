package ru.gb.weather.model

import ru.gb.weather.domain.Weather

fun interface SingleResultRepository {

    fun getWeather( lat: Double, lon: Double): Weather
}

fun interface ManyResultRepository {
    fun getListWeather(location:Location): List<Weather>

}

sealed class Location{
    object Russian:Location()
    object World:Location()
}