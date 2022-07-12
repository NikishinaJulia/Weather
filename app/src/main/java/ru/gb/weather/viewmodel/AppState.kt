package ru.gb.weather.viewmodel

import ru.gb.weather.domain.Weather

sealed class AppState {

    data class Success(val weatherData: Weather) : AppState()
    data class SuccessList(val weatherList: List<Weather>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()


}