package ru.gb.weather.model.dto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherDTO(

    val fact: Fact,
    val forecast: Forecast,
    val info: Info,
    val now: Int,
    @SerializedName("now_dt")
    val nowDt: String
) : Parcelable