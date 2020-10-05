package io.tecnodev.pokemon.shared.data.result

import io.tecnodev.pokemon.shared.data.model.PokemonTypeModel

sealed class PokemonTypeResult {
    class Success(val types: List<PokemonTypeModel>) : PokemonTypeResult()
    class ApiError(val statusCode: Int) : PokemonTypeResult()
    object ServerError : PokemonTypeResult()
}
