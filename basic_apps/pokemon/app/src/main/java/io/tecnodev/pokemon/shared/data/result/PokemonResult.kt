package io.tecnodev.pokemon.shared.data.result

import io.tecnodev.pokemon.shared.data.model.PokemonModel

sealed class PokemonResult {
    class Success(val pokemons: List<PokemonModel>) : PokemonResult()
    class ApiError(val statusCode: Int) : PokemonResult()
    object ServerError : PokemonResult()
}
