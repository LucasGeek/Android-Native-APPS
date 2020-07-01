package com.example.hgbrasil.shared.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class WeatherResultsResponse (
    @Json(name = "temp")
    val temp: Long,

    @Json(name = "date")
    val date: String,

    @Json(name = "time")
    val time: String,

    @Json(name = "condition_code")
    val conditionCode: String,

    @Json(name = "description")
    val description: String,

    @Json(name = "currently")
    val currently: String,

    @Json(name = "cid")
    val cid: String,

    @Json(name = "city")
    val city: String,

    @Json(name = "img_id")
    val imgID: String,

    @Json(name = "humidity")
    val humidity: Long,

    @Json(name = "wind_speedy")
    val windSpeedy: String,

    @Json(name = "sunrise")
    val sunrise: String,

    @Json(name = "sunset")
    val sunset: String,

    @Json(name = "condition_slug")
    val conditionSlug: String,

    @Json(name = "city_name")
    val cityName: String,

    @Json(name = "forecast")
    val forecast: List<ForecastResponse>
)