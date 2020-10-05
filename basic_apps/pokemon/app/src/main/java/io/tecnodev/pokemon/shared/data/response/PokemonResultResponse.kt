package io.tecnodev.pokemon.shared.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.tecnodev.pokemon.shared.data.model.PokemonModel

@JsonClass(generateAdapter = true)
data class PokemonItemResponse(
    @Json(name = "pokemon")
    val pokemon: PokemonResultResponse
)

@JsonClass(generateAdapter = true)
data class PokemonResultResponse(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
) {
    fun getModel() = PokemonModel(
        name = this.name,
        url = this.url
    )
}