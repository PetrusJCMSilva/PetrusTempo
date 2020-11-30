package com.devapp20201.petrustempo.manager

import com.devapp20201.petrustempo.service.OpenWeatherService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OpenWeatherManager {
    private val instance = Retrofit.Builder().baseUrl("http://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create()).build()

    fun getOpenWeatherService(): OpenWeatherService = instance.create(OpenWeatherService::class.java)
}