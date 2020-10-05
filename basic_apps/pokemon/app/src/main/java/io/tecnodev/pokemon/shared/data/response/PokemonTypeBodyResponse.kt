package io.tecnodev.pokemon.shared.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonTypeBodyResponse(
    @Json(name = "count")
    val count: Int,
    @Json(name = "results")
    val results: List<PokemonTypeResultResponse>
)