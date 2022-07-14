package ru.gb.weather.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.gb.weather.databinding.FragmentDetailsBinding
import ru.gb.weather.domain.Weather

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weather = (arguments?.getParcelable<Weather>(BUNDLE_WEATHER_EXTRA))
        renderData(weather)
    }

    private fun renderData(weather: Weather?) {

        if (weather != null) {
            binding.cityName.text = weather.city.name
            binding.temperatureValue.text = weather.temperature.toString()
            binding.feelsLikeValue.text = weather.feelsLike.toString()
            binding.cityCoordinates.text = "${weather.city.lat}/${weather.city.lon}"
            binding.loadingLayout.visibility = View.GONE
            binding.temperatureLabel.visibility = View.VISIBLE
            binding.feelsLikeLabel.visibility = View.VISIBLE
        }
    }

    companion object {
        const val BUNDLE_WEATHER_EXTRA = "fdkkojgo"
        fun newInstance(weather: Weather): DetailsFragment {
            val bundle = Bundle()
            bundle.putParcelable(BUNDLE_WEATHER_EXTRA, weather)
            val fr = DetailsFragment()
            fr.arguments = bundle
            return fr
        }
    }

}