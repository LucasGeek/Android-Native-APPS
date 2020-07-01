package com.example.hgbrasil.shared.network

import com.example.hgbrasil.shared.network.response.WeatherBodyResponse
import retrofit2.Call
import retrofit2.http.GET

interface HGServices {
    @GET("weather?key=34dc9577&lat=-23.682&log=-46.875&user_ip=remote") fun getWeather(): Call<WeatherBodyResponse>
}