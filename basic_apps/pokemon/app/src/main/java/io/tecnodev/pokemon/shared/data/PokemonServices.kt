package io.tecnodev.pokemon.shared.data

import io.tecnodev.pokemon.shared.data.response.PokemonBodyResponse
import io.tecnodev.pokemon.shared.data.response.PokemonDetailBodyResponse
import io.tecnodev.pokemon.shared.data.response.PokemonTypeBodyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonServices {

    @GET("type/")
    fun getTypes(): Call<PokemonTypeBodyResponse>

    @GET("type/{id}/")
    fun getPokemons(@Path("id") id: Int): Call<PokemonBodyResponse>

    @GET("pokemon/{id}/")
    fun getPokemonDetail(@Path("id") id: Int): Call<PokemonDetailBodyResponse>
}