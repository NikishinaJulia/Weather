package ru.gb.weather.view.weatherlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gb.weather.model.Repository
import ru.gb.weather.model.RepositoryLocalImpl
import ru.gb.weather.model.RepositoryRemoteImpl
import ru.gb.weather.viewmodel.AppState
import java.lang.Thread.sleep

class WeatherListViewModel(private val liveData:MutableLiveData<AppState> = MutableLiveData<AppState>()):ViewModel() {

    lateinit var repository: Repository

    fun getLiveData():MutableLiveData<AppState> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {
        if(isConnection()){
            repository = RepositoryRemoteImpl()
        }else {
            repository = RepositoryLocalImpl()
        }
    }

    fun sentRequest() {

        liveData.value = AppState.Loading
        if((0..3).random()==1){
            liveData.postValue(AppState.Error( IllegalStateException("что-то пошло не так!")))
        } else
        liveData.postValue(AppState.Success(repository.getWeather(55.755826, 37.617299900000035)))



    }

    private fun isConnection(): Boolean {
         return false
    }

    override fun onCleared() {
        super.onCleared()
    }

}