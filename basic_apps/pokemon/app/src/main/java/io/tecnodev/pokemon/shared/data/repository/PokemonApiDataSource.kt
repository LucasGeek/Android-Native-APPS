package io.tecnodev.pokemon.shared.data.repository

import io.tecnodev.pokemon.shared.data.ApiService
import io.tecnodev.pokemon.shared.data.model.PokemonDetailModel
import io.tecnodev.pokemon.shared.data.model.PokemonModel
import io.tecnodev.pokemon.shared.data.model.PokemonTypeModel
import io.tecnodev.pokemon.shared.data.response.PokemonBodyResponse
import io.tecnodev.pokemon.shared.data.response.PokemonDetailBodyResponse
import io.tecnodev.pokemon.shared.data.response.PokemonTypeBodyResponse
import io.tecnodev.pokemon.shared.data.result.PokemonDetailResult
import io.tecnodev.pokemon.shared.data.result.PokemonResult
import io.tecnodev.pokemon.shared.data.result.PokemonTypeResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonApiDataSource : PokemonRepository {
    override fun getTypes(typeResultCallback: (result: PokemonTypeResult) -> Unit) {
        ApiService.service.getTypes().enqueue(object : Callback<PokemonTypeBodyResponse> {
            override fun onResponse(call: Call<PokemonTypeBodyResponse>, response: Response<PokemonTypeBodyResponse>) {
                when {
                    response.isSuccessful -> {
                        val types: MutableList<PokemonTypeModel> = mutableListOf()

                        response.body()?.let { typeBodyResponse ->
                            for (result in typeBodyResponse.results) {
                                val type = result.getTypeModel()
                                types.add(type)
                            }
                        }

                        typeResultCallback(PokemonTypeResult.Success(types))
                    }
                    else -> typeResultCallback(PokemonTypeResult.ApiError(response.code()))
                }
            }

            override fun onFailure(call: Call<PokemonTypeBodyResponse>, t: Throwable) {
                typeResultCallback(PokemonTypeResult.ServerError)
            }
        })
    }

    override fun getPokemons(idType: Int, pokemonResultCallback: (result: PokemonResult) -> Unit) {
        ApiService.service.getPokemons(idType).enqueue(object : Callback<PokemonBodyResponse> {
            override fun onResponse(call: Call<PokemonBodyResponse>, response: Response<PokemonBodyResponse>) {
                when {
                    response.isSuccessful -> {
                        val pokemons: MutableList<PokemonModel> = mutableListOf()

                        response.body()?.let { pokemonBodyResponse ->
                            for (result in pokemonBodyResponse.pokemon) {
                                val pokemon = result.pokemon.getModel()
                                pokemons.add(pokemon)
                            }
                        }

                        pokemonResultCallback(PokemonResult.Success(pokemons))
                    }
                    else -> pokemonResultCallback(PokemonResult.ApiError(response.code()))
                }
            }

            override fun onFailure(call: Call<PokemonBodyResponse>, t: Throwable) {
                pokemonResultCallback(PokemonResult.ServerError)
            }
        })
    }

    override fun getPokemonDetail(idPokemon: Int, pokemonResultCallback: (result: PokemonDetailResult) -> Unit) {
        ApiService.service.getPokemonDetail(idPokemon).enqueue(object : Callback<PokemonDetailBodyResponse> {
            override fun onResponse(call: Call<PokemonDetailBodyResponse>, response: Response<PokemonDetailBodyResponse>) {
                when {
                    response.isSuccessful -> {
                        val pokemon: PokemonDetailModel = response.body()!!.getModel()

                        pokemonResultCallback(PokemonDetailResult.Success(pokemon))
                    }
                    else -> pokemonResultCallback(PokemonDetailResult.ApiError(response.code()))
                }
            }

            override fun onFailure(call: Call<PokemonDetailBodyResponse>, t: Throwable) {
                pokemonResultCallback(PokemonDetailResult.ServerError)
            }
        })
    }
}