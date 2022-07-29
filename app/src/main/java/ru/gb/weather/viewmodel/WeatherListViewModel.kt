package ru.gb.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gb.weather.model.*
import kotlin.random.Random

class WeatherListViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()) :
    ViewModel() {

    lateinit var repositorySingle: SingleResultRepository
    lateinit var repositoryMulti: ManyResultRepository

    fun getLiveData(): MutableLiveData<AppState> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {
        if (isConnection()) {
            repositorySingle = RepositoryRemoteImpl()
        } else {
            repositorySingle = RepositoryLocalImpl()
        }
        repositoryMulti = RepositoryLocalImpl()
    }

    fun getWeatherListForRussia() {
        sentRequest(Location.Russian)
    }

    fun getWeatherListForWorld() {
        sentRequest(Location.World)
    }

    private fun sentRequest(location: Location) {

        liveData.value = AppState.Loading
        Thread {
            Thread.sleep(1000L)
            if (false) {
                liveData.postValue(AppState.Error(IllegalStateException("что-то пошло не так!")))
            } else {
                liveData.postValue(AppState.SuccessList(repositoryMulti.getListWeather(location)))
            }
        }.start()


    }

    private fun isConnection(): Boolean {
        return false
    }

    override fun onCleared() {
        super.onCleared()
        android.util.Log.d("TEST", "onCleared MyViewModel hash=" + hashCode());
    }

}