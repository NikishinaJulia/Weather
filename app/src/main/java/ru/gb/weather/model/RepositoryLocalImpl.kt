package ru.gb.weather.model



import ru.gb.weather.domain.Weather
import ru.gb.weather.domain.getRussianCities
import ru.gb.weather.domain.getWorldCities

class RepositoryLocalImpl: ManyResultRepository, SingleResultRepository{
    override fun getListWeather(location: Location): List<Weather> {
        return when (location){
            Location.Russian -> {
                getRussianCities()
            }
            Location.World -> {
                getWorldCities()
            }
        }
    }

    override fun getWeather(lat: Double, lon: Double): Weather {
        return Weather()
    }
}