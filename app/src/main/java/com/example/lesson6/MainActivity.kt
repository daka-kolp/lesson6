package com.example.lesson6

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.lesson6.network.ApiClient
import com.example.lesson6.network.WeatherService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_layout)
        val confirmButton = findViewById<Button>(R.id.confirm)
        confirmButton.setOnClickListener { getWeather() }
    }

    private fun getWeather() {
        val cityName = findViewById<EditText>(R.id.input_text).text.toString()
        val loading = findViewById<ProgressBar>(R.id.loading)
        loading.isVisible = true

        ApiClient.retrofit
            .create(WeatherService::class.java)
            .getWeather(cityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                findViewById<TextView>(R.id.city).text = cityName
                findViewById<TextView>(R.id.temperature).text = it.temperature
                findViewById<TextView>(R.id.wind).text = it.wind
                findViewById<TextView>(R.id.description).text = it.description
                findViewById<TextView>(R.id.forecast).text = it.forecastFormatted()
                loading.isVisible = false
            }, {
                showError("Failure : ${it.message}")
                loading.isVisible = false
            })
    }

    private fun showError(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
    }
}
