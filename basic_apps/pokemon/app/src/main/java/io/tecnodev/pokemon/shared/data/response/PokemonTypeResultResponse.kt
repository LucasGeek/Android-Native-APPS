package io.tecnodev.pokemon.shared.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.tecnodev.pokemon.shared.data.model.PokemonTypeModel

@JsonClass(generateAdapter = true)
data class PokemonTypeResultResponse(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
) {
    fun getTypeModel() = PokemonTypeModel(
        name = this.name,
        url = this.url
    )
}