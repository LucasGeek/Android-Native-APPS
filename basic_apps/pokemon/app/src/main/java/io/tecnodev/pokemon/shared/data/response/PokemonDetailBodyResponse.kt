package io.tecnodev.pokemon.shared.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.tecnodev.pokemon.shared.data.model.PokemonDetailModel

@JsonClass(generateAdapter = true)
data class PokemonDetailBodyResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "base_experience")
    val base_experience: Int,
    @Json(name = "height")
    val height: Int,
    @Json(name = "weight")
    val weight: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "sprites")
    val sprites: PokemonDetailSpritesResponse
) {
    fun getModel() = PokemonDetailModel(
        base_experience = this.base_experience,
        height = this.height,
        weight = this.weight,
        name = this.name,
        img = getImgNotNull(this.sprites)
    )

    fun getImgNotNull(sprites: PokemonDetailSpritesResponse): String {
        var img: String = ""

        if(sprites.front_default.isNullOrEmpty()) {
            if(sprites.front_female.isNullOrEmpty()) {
                if(sprites.front_shiny.isNullOrEmpty()) {
                    if(sprites.front_shiny_female.isNullOrEmpty()) {
                        if(sprites.back_default.isNullOrEmpty()) {
                            if(sprites.back_female.isNullOrEmpty()) {
                                if(sprites.back_shiny.isNullOrEmpty()) {
                                    if(sprites.back_shiny_female.isNullOrEmpty()) {
                                        img = ""
                                    } else {
                                        img = sprites.back_shiny_female
                                    }
                                } else {
                                    img = sprites.back_shiny
                                }
                            } else {
                                img = sprites.back_female
                            }
                        } else {
                            img = sprites.back_default
                        }
                    } else {
                        img = sprites.front_shiny_female
                    }
                } else {
                    img = sprites.front_shiny
                }
            } else {
                img = sprites.front_female
            }
        } else {
            img = sprites.front_default
        }

        return img
    }
}

@JsonClass(generateAdapter = true)
data class PokemonDetailSpritesResponse(
    @Json(name = "back_default")
    val back_default: String?,
    @Json(name = "back_female")
    val back_female: String?,
    @Json(name = "back_shiny")
    val back_shiny: String?,
    @Json(name = "back_shiny_female")
    val back_shiny_female: String?,
    @Json(name = "front_default")
    val front_default: String?,
    @Json(name = "front_female")
    val front_female: String?,
    @Json(name = "front_shiny")
    val front_shiny: String?,
    @Json(name = "front_shiny_female")
    val front_shiny_female: String?
)