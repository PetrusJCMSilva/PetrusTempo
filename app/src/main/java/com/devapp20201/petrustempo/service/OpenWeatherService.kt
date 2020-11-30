package com.devapp20201.petrustempo.service



import com.devapp20201.petrustempo.model.City
import com.devapp20201.petrustempo.model.Root
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService{
    //http://api.openweathermap.org/data/2.5/weather?q=recife&APPID=434e87114ed21284b1b62a4474ab970c
    @GET("weather")
    fun getCityWeather(
        @Query("q") cityName: String,
        @Query("APPID")appid: String = "434e87114ed21284b1b62a4474ab970c"
    ) : Call<City>

   @GET ("find")
   fun findTemperature(
           @Query("q") cityName: String,
           @Query("q") unit: String = "metrics",
           @Query("APPID")appid: String = "434e87114ed21284b1b62a4474ab970c"
   ) : Call <Root>

}