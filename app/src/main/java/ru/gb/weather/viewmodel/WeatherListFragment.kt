package ru.gb.weather.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gb.k_2135_2136_2.view.weatherlist.WeatherListAdapter
import ru.gb.weather.R
import ru.gb.weather.databinding.FragmentWeatherListBinding


class WeatherListFragment : Fragment() {
    companion object{
        fun newInstance() = WeatherListFragment()
    }

    var isRussian = true

    private var _binding : FragmentWeatherListBinding? = null
    private val binding : FragmentWeatherListBinding
    get(){
        return _binding!!
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    lateinit var viewModel: WeatherListViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainFragmentRecyclerView.adapter = WeatherListAdapter(ArrayList())
        viewModel = ViewModelProvider(this).get(WeatherListViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, object : Observer<AppState>{
            override fun onChanged(t: AppState) {
                renderData(t)

            }
        })

        binding.weatherListFragmentFAB.setOnClickListener {
            isRussian = !isRussian
            if(isRussian) {
                viewModel.getWeatherListForRussia()
                binding.weatherListFragmentFAB.setImageResource(R.drawable.ic_russia)
            }else {
                viewModel.getWeatherListForWorld()
                binding.weatherListFragmentFAB.setImageResource(R.drawable.ic_earth)
            }
        }
        viewModel.getWeatherListForRussia()
    }

    private fun renderData(appState: AppState) {
        when(appState) {
            is AppState.Error -> {
               // binding.loadingLayout.visibility = View.GONE
                val result = appState.error.message
     //           binding.cityName.text = result
     //           binding.temperatureLabel.visibility = View.INVISIBLE
     //           binding.feelsLikeLabel.visibility = View.INVISIBLE
     //           binding.temperatureValue.text = ""
     //           binding.feelsLikeValue.text = ""
     //           binding.cityCoordinates.text = ""

                 }
            AppState.Loading -> {
                //binding.loadingLayout.visibility = View.VISIBLE
                 }
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                val result = appState.weatherData
            }
            is AppState.SuccessList -> {
                binding.loadingLayout.visibility = View.GONE
                binding.mainFragmentRecyclerView.adapter = WeatherListAdapter(appState.weatherList)
     //           binding.cityName.text = result.city.name
     //           binding.temperatureValue.text = result.temperature.toString()
     //           binding.feelsLikeValue.text = result.feelsLike.toString()
     //           binding.cityCoordinates.text = "${result.city.lat}/${result.city.lon}"
     //           binding.loadingLayout.visibility = View.GONE
     //           binding.temperatureLabel.visibility = View.VISIBLE
     //           binding.feelsLikeLabel.visibility = View.VISIBLE

            }
        }

    }
}