package ru.gb.weather.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.snackbar.Snackbar
import ru.gb.weather.MainActivity
import ru.gb.weather.R
import ru.gb.weather.databinding.FragmentDetailsBinding
import ru.gb.weather.domain.Weather
import ru.gb.weather.model.dto.WeatherDTO
import ru.gb.weather.utils.BUNDLE_CITY_KEY
import ru.gb.weather.utils.BUNDLE_WEATHER_DTO_KEY
import ru.gb.weather.utils.WAVE
import ru.gb.weather.utils.WeatherLoader
import ru.gb.weather.viewmodel.showErrorSnack

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    private var receiver: BroadcastReceiver? = null


    fun newInstanceReceiver() = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            Log.d("@@@", "onReceive ${binding.root}")
            intent?.let {
                it.getParcelableExtra<WeatherDTO>(BUNDLE_WEATHER_DTO_KEY)
                    ?.let { weatherDTO ->
                        bindWeatherLocalWithWeatherDTO(weatherLocal, weatherDTO)
                    }
            }
        }
    }

    lateinit var weatherLocal: Weather

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver!!)
        receiver = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater)
        receiver = newInstanceReceiver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weather = arguments?.let { arg ->
            arg.getParcelable<Weather>(BUNDLE_WEATHER_EXTRA)
        }

        weather?.let { weatherLocal ->
            this.weatherLocal = weatherLocal
            WeatherLoader.requestFirstVariant(
                weatherLocal.city.lat,
                weatherLocal.city.lon,
                object : OnResponse {
                    override fun onResponse(weather: WeatherDTO) {

                    }

                    override fun onFailure(throwable: Throwable) {
                        view.showErrorSnack(
                            getString(R.string.error) + ":" + throwable.message,
                            Snackbar.LENGTH_LONG,
                            getString(R.string.reload)
                        ){

                                 val returnFragment =
                                     (binding.root.context as MainActivity).supportFragmentManager.fragments[0]!!
                            (binding.root.context as MainActivity).supportFragmentManager.beginTransaction()
                                .remove(this@DetailsFragment)
                                .show(returnFragment)
                                .commit()
                        }
                    }
                }
            )
            LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
                receiver!!,
                IntentFilter(WAVE)
            )

            requireActivity().startService(
                Intent(
                    requireContext(),
                    DetailsServiceIntent::class.java
                ).apply {
                    putExtra(BUNDLE_CITY_KEY, weatherLocal.city)
                })
        }


    }


    private fun bindWeatherLocalWithWeatherDTO(
        weatherLocal: Weather,
        weatherDTO: WeatherDTO
    ) {
        renderData(weatherLocal.apply {
            feelsLike = weatherDTO.fact.feelsLike
            temperature = weatherDTO.fact.temp
            windSpeed = weatherDTO.fact.windSpeed
        })
    }


    private fun renderData(weather: Weather) {

        with(binding) {
            cityName.text = weather.city.name
            temperatureValue.text = weather.temperature.toString()
            feelsLikeValue.text = weather.feelsLike.toString()
            windSpeedValue.text = weather.windSpeed.toString()
            cityCoordinates.text = "${weather.city.lat}/${weather.city.lon}"
            loadingLayout.visibility = View.GONE
            temperatureLabel.visibility = View.VISIBLE
            feelsLikeLabel.visibility = View.VISIBLE
        }
    }

    companion object {
        const val BUNDLE_WEATHER_EXTRA = "fdkkojgo"
        fun newInstance(weather: Weather) = DetailsFragment().also { fr ->

            fr.arguments = Bundle().apply {
                putParcelable(BUNDLE_WEATHER_EXTRA, weather)
            }
             return fr
        }
    }

}