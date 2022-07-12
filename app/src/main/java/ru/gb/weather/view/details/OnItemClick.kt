package ru.gb.weather.view.details

import ru.gb.weather.domain.Weather

fun interface OnItemClick {
    fun onItemClick(weather: Weather)
}