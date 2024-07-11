package com.example.lesson6.models

data class Weather(
    val temperature: String,
    val wind: String,
    val description: String,
    val forecast: List<Forecast>
) {
    fun forecastFormatted(): String {
        return forecast.fold("") { sum, element ->
            "$sum day: ${element.day} wind: ${element.wind} desc:${element.description}\n"
        }
    }
}
