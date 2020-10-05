package io.tecnodev.pokemon.ui.type_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.tecnodev.pokemon.R
import io.tecnodev.pokemon.shared.data.model.PokemonTypeModel
import io.tecnodev.pokemon.shared.data.repository.PokemonRepository
import io.tecnodev.pokemon.shared.data.result.PokemonTypeResult

class TypeListViewModel(private val dataSource: PokemonRepository) : ViewModel() {

    val typesLiveData: MutableLiveData<List<PokemonTypeModel>> = MutableLiveData()
    val viewFlipperLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()

    fun getTypeList() {
        dataSource.getTypes { result: PokemonTypeResult ->
            when (result) {
                is PokemonTypeResult.Success -> {
                    typesLiveData.value = result.types
                    viewFlipperLiveData.value = Pair(VIEW_FLIPPER_TYPES, null)
                }
                is PokemonTypeResult.ApiError -> {
                    if (result.statusCode == 400) {
                        viewFlipperLiveData.value =
                            Pair(VIEW_FLIPPER_ERROR, R.string.types_error_400_generic)
                    }
                }
                is PokemonTypeResult.ServerError -> {
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