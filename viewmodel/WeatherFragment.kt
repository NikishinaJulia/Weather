package ru.gb.weather.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.gb.weather.databinding.FragmentWeatherListBinding

class WeatherFragment : Fragment() {
    companion object{
        fun newInstance() = WeatherFragment()
    }

    lateinit var binding : FragmentWeatherListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }
}