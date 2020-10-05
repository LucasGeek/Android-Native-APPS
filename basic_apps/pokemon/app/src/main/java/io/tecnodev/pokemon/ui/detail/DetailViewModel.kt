package io.tecnodev.pokemon.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.tecnodev.pokemon.R
import io.tecnodev.pokemon.shared.data.model.PokemonDetailModel
import io.tecnodev.pokemon.shared.data.repository.PokemonRepository
import io.tecnodev.pokemon.shared.data.result.PokemonDetailResult

class DetailViewModel(private val dataSource: PokemonRepository) : ViewModel() {
    val pokemonLiveDataModel: MutableLiveData<PokemonDetailModel> = MutableLiveData()
    val viewFlipperLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()

    fun getPokemon(idPokemon: Int) {
        dataSource.getPokemonDetail(idPokemon) { result: PokemonDetailResult ->
            when (result) {
                is PokemonDetailResult.Success -> {
                    pokemonLiveDataModel.value = result.pokemon
                    viewFlipperLiveData.value = Pair(DetailViewModel.VIEW_FLIPPER_TYPES, null)
                }
                is PokemonDetailResult.ApiError -> {
                    if (result.statusCode == 400) {
                        viewFlipperLiveData.value =
                            Pair(DetailViewModel.VIEW_FLIPPER_ERROR, R.string.types_error_400_generic)
                    }
                }
                is PokemonDetailResult.ServerError -> {
                    viewFlipperLiveData.value =
                        Pair(DetailViewModel.VIEW_FLIPPER_ERROR, R.string.types_error_500_generic)
                }
            }
        }
    }

    companion object {
        private const val VIEW_FLIPPER_TYPES = 1
        private const val VIEW_FLIPPER_ERROR = 2
    }
}