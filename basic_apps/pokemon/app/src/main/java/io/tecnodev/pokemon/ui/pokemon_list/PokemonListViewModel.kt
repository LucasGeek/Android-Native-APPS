package io.tecnodev.pokemon.ui.pokemon_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.tecnodev.pokemon.R
import io.tecnodev.pokemon.shared.data.model.PokemonModel
import io.tecnodev.pokemon.shared.data.repository.PokemonRepository
import io.tecnodev.pokemon.shared.data.result.PokemonResult

class PokemonListViewModel(private val dataSource: PokemonRepository) : ViewModel() {

    val pokemonsLiveData: MutableLiveData<List<PokemonModel>> = MutableLiveData()
    val viewFlipperLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()

    fun getPokemonsList(idType: Int) {
        dataSource.getPokemons(idType) { result: PokemonResult ->
            when (result) {
                is PokemonResult.Success -> {
                    pokemonsLiveData.value = result.pokemons
                    viewFlipperLiveData.value = Pair(VIEW_FLIPPER_TYPES, null)
                }
                is PokemonResult.ApiError -> {
                    if (result.statusCode == 400) {
                        viewFlipperLiveData.value =
                            Pair(VIEW_FLIPPER_ERROR, R.string.types_error_400_generic)
                    }
                }
                is PokemonResult.ServerError -> {
                    viewFlipperLiveData.value =
                        Pair(VIEW_FLIPPER_ERROR, R.string.types_error_500_generic)
                }
            }
        }
    }

    companion object {
        private const val VIEW_FLIPPER_TYPES = 1
        private const val VIEW_FLIPPER_ERROR = 2
    }
}