package com.example.hgbrasil.shared.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class WeatherBodyResponse (
    @Json(name = "by")
    val by: String,

    @Json(name = "valid_key")
    val validKey: Boolean,

    @Json(name = "results")
    val results: WeatherResultsResponse,

    @Json(name = "execution_time")
    val executionTime: Double,

    @Json(name = "from_cache")
    val fromCache: Boolean
)