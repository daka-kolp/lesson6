package com.example.lesson6.network

import com.example.lesson6.models.Weather
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {
    @GET("/weather/{city}")
    fun getWeather(@Path("city") city: String): Single<Weather>
}
