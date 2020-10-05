package io.tecnodev.pokemon.shared.data.repository

import io.tecnodev.pokemon.shared.data.result.PokemonDetailResult
import io.tecnodev.pokemon.shared.data.result.PokemonResult
import io.tecnodev.pokemon.shared.data.result.PokemonTypeResult

interface PokemonRepository {
    fun getTypes(typesResultCallback: (result: PokemonTypeResult) -> Unit)

    fun getPokemons(idType: Int, typesResultCallback: (result: PokemonResult) -> Unit)

    fun getPokemonDetail(idPokemon: Int, typesResultCallback: (result: PokemonDetailResult) -> Unit)
}