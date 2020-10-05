package io.tecnodev.pokemon.shared.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonBodyResponse(
    @Json(name = "id")
    val int: Int,
    @Json(name = "pokemon")
    val pokemon: List<PokemonItemResponse>
)