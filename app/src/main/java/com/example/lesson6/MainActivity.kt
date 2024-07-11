package com.example.lesson6

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.lesson6.models.Weather
import com.example.lesson6.network.ApiClient
import com.example.lesson6.network.WeatherService
import retrofit2.Call
import retrofit2.Response

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.weather_layout)
        val confirmButton = findViewById<Button>(R.id.confirm)
        confirmButton.setOnClickListener { getWeather() }
    }

    private fun getWeather() {
        val cityTextField = findViewById<EditText>(R.id.city)
        val cityValue = cityTextField.text.toString()
        val loading = findViewById<ProgressBar>(R.id.loading)
        loading.isVisible = true

        ApiClient.retrofit
            .create(WeatherService::class.java)
            .getWeather(cityValue)
            .enqueue(object : retrofit2.Callback<Weather> {
                override fun onResponse(p0: Call<Weather>, p1: Response<Weather>) {
                    val weather = p1.body()

                    if (weather == null) {
                        showError("No Data, cannot be parsed")
                        loading.isVisible = false
                        return
                    }

                    val temperature = findViewById<TextView>(R.id.temperature)
                    val wind = findViewById<TextView>(R.id.wind)
                    val info = findViewById<TextView>(R.id.description)
                    val forecast = findViewById<TextView>(R.id.forecast)

                    temperature.text = weather.temperature
                    wind.text = weather.wind
                    info.text = weather.description
                    forecast.text = weather.forecastFormatted()
                    loading.isVisible = false
                }

                override fun onFailure(p0: Call<Weather>, p1: Throwable) {
                    showError("Failure : ${p1.message}")
                    loading.isVisible = false
                }
            })
    }

    private fun showError(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
    }
}
