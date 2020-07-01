package com.example.hgbrasil.presentation.main.fragments.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hgbrasil.shared.model.Forecast
import com.example.hgbrasil.shared.model.Weather
import com.example.hgbrasil.shared.network.ApiService
import com.example.hgbrasil.shared.network.response.ForecastResponse
import com.example.hgbrasil.shared.network.response.WeatherBodyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel() {

    val weatherLiveData: MutableLiveData<Weather> = MutableLiveData()

    fun getWeather() {
        ApiService.service.getWeather().enqueue(object: Callback<WeatherBodyResponse> {
            override fun onResponse(call: Call<WeatherBodyResponse>, response: Response<WeatherBodyResponse>) {
                if(response.isSuccessful) {

                    response.body()?.let { body ->
                        val item = body.results

                        weatherLiveData.value = Weather(
                            temp = item.temp,
                            date = item.date,
                            time = item.time,
                            conditionCode = item.conditionCode,
                            description = item.description,
                            currently = item.currently,
                            cid = item.cid,
                            city = item.city,
                            humidity = item.humidity,
                            windSpeedy = item.windSpeedy,
                            sunrise = item.sunrise,
                            sunset = item.sunset,
                            conditionSlug = item.conditionSlug,
                            cityName = item.cityName,
                            imgID = item.imgID,
                            forecast = item.forecast.map { forecast: ForecastResponse ->
                                Forecast(
                                    date = forecast.date,
                                    weekday = forecast.weekday,
                                    max = forecast.max,
                                    min = forecast.min,
                                    description = forecast.description,
                                    condition = forecast.condition)
                            }
                        )
                    }
                }
            }

            override fun onFailure(call: Call<WeatherBodyResponse>, t: Throwable) {
                Log.d("Error", t?.let { it -> it.message })
            }
        })
    }
}