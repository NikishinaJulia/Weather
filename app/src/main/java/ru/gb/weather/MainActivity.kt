package ru.gb.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.gb.weather.databinding.ActivityMainBinding
import ru.gb.weather.viewmodel.WeatherListFragment

internal class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance()).commit()
        }
    }
}
