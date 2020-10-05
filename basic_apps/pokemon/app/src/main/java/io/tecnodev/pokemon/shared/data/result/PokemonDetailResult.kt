package io.tecnodev.pokemon.shared.data.result

import io.tecnodev.pokemon.shared.data.model.PokemonDetailModel

sealed class  PokemonDetailResult {
    class Success(val pokemon: PokemonDetailModel) : PokemonDetailResult()
    class ApiError(val statusCode: Int) : PokemonDetailResult()
    object ServerError : PokemonDetailResult()
}