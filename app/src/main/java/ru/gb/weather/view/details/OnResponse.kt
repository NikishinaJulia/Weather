package ru.gb.weather.view.details

import ru.gb.weather.model.dto.WeatherDTO

interface OnResponse {
    fun onResponse(weather: WeatherDTO)
}