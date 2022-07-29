package ru.gb.weather.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.gb.weather.view.weatherlist.WeatherListAdapter
import com.google.android.material.snackbar.Snackbar
import ru.gb.weather.MainActivity
import ru.gb.weather.R
import ru.gb.weather.databinding.FragmentWeatherListBinding
import ru.gb.weather.domain.Weather
import ru.gb.weather.view.details.DetailsFragment
import ru.gb.weather.view.details.OnItemClick


class WeatherListFragment : Fragment(), OnItemClick {
    companion object {
        fun newInstance() = WeatherListFragment()
    }

    var isRussian = true

    private var _binding: FragmentWeatherListBinding? = null
    private val binding: FragmentWeatherListBinding
        get() {
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

        viewModel = ViewModelProvider(this).get(WeatherListViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner,
            { t -> renderData(t) })

        binding.weatherListFragmentFAB.setOnClickListener {
            isRussian = !isRussian
            if (isRussian) {
                viewModel.getWeatherListForRussia()
                binding.weatherListFragmentFAB.apply {
                    setImageResource(R.drawable.ic_russia)
                }

            } else {
                viewModel.getWeatherListForWorld()
                binding.weatherListFragmentFAB.setImageResource(R.drawable.ic_earth)
            }
        }
        viewModel.getWeatherListForRussia()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.showResult()
                val result = appState.error.message
                binding.root.showErrorSnack(
                    getString(R.string.error) + ":" + result,
                    Snackbar.LENGTH_SHORT,
                    getString(R.string.reload)
                )
                { _ ->
                    if (isRussian) {
                        viewModel.getWeatherListForRussia()
                    } else {
                        viewModel.getWeatherListForWorld()
                    }
                }
            }


            AppState.Loading -> {
                binding.loading()
            }
            is AppState.Success -> {
                binding.showResult()
                val result = appState.weatherData
            }
            is AppState.SuccessList -> {
                binding.showResult()
                binding.mainFragmentRecyclerView.adapter =
                    WeatherListAdapter(appState.weatherList, this)

            }
        }

    }

    fun FragmentWeatherListBinding.loading() {
        this.loadingLayout.visibility = View.VISIBLE
        this.weatherListFragmentFAB.visibility = View.GONE
    }

    fun FragmentWeatherListBinding.showResult() {
        this.loadingLayout.visibility = View.GONE
        this.weatherListFragmentFAB.visibility = View.VISIBLE

    }

    override fun onItemClick(weather: Weather) {
        (binding.root.context as MainActivity).supportFragmentManager.beginTransaction().hide(this)
            .add(
                R.id.container, DetailsFragment.newInstance(weather)
            ).addToBackStack("").commit()
    }
}


public fun View.showErrorSnack(
    textError: String,
    duration: Int,
    actionText: String,
    block: (v: View) -> Unit
) {
    Snackbar
        .make(
            this, textError, duration
        )
        .setAction(actionText, block)
        .show()
}