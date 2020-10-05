package io.tecnodev.pokemon.ui.type_list

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import io.tecnodev.pokemon.R
import io.tecnodev.pokemon.shared.data.repository.PokemonApiDataSource
import io.tecnodev.pokemon.shared.data.repository.PokemonRepository
import io.tecnodev.pokemon.shared.extension.navigateWithAnimations
import kotlinx.android.synthetic.main.type_list_fragment.*

class TypeListFragment : Fragment(R.layout.type_list_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelEvents()
        configureViewListeners()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTypeList()
    }

    private val viewModel: TypeListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repository: PokemonRepository = PokemonApiDataSource()
                return TypeListViewModel(repository) as T
            }
        }
    }

    private fun observeViewModelEvents() {
        viewModel.typesLiveData.observe(viewLifecycleOwner) { allTypes ->
            val typeListAdapter = TypeListAdapter(allTypes) { type ->

                val bundle = bundleOf(
                    "url" to type.url
                )

                findNavController().navigateWithAnimations(R.id.action_typeListFragment_to_pokemonListFragment, bundle)
            }

            with(recycler_types) {
                setHasFixedSize(true)
                adapter = typeListAdapter
            }
        }

//        viewModel.viewFlipperLiveData.observe(viewLifecycleOwner) {
//            it?.let { viewFlipper ->
//                viewFlipperTypes.displayedChild = viewFlipper.first
//
//                viewFlipper.second?.let { errorMessageResId ->
//                    textViewError.text = getString(errorMessageResId)
//                }
//            }
//        }
    }

    private fun configureViewListeners() {
        fabRefreshTypeList.setOnClickListener {
            viewModel.getTypeList()
        }
    }
}