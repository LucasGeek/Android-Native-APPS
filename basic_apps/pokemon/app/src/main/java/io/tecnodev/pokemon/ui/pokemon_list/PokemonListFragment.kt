package io.tecnodev.pokemon.ui.pokemon_list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import io.tecnodev.pokemon.R
import io.tecnodev.pokemon.shared.constant.ConstantApp
import io.tecnodev.pokemon.shared.data.repository.PokemonApiDataSource
import io.tecnodev.pokemon.shared.data.repository.PokemonRepository
import io.tecnodev.pokemon.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.pokemon_list_fragment.*

class PokemonListFragment : Fragment(R.layout.pokemon_list_fragment) {
    private val viewModel: PokemonListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repository: PokemonRepository = PokemonApiDataSource()
                return PokemonListViewModel(repository) as T
            }
        }
    }

    private fun observeViewModelEvents() {
        viewModel.pokemonsLiveData.observe(viewLifecycleOwner) { allPokemons ->
            val pokemonsListAdapter = PokemonListAdapter(allPokemons) { pokemon ->

                var intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra("url", pokemon.url)

                startActivity(intent)
            }

            with(recycler_pokemons) {
                setHasFixedSize(true)
                adapter = pokemonsListAdapter
            }
        }
    }

    private fun configureViewListeners() {
        fabRefreshPokemonList.setOnClickListener {
            getPokemons()
        }
    }

    private fun getPokemons() {
        var urlType = arguments?.getString("url")
        var urlReplaced = urlType?.replace(ConstantApp.SERVICE.BASE_URL, "")
        var idType = urlReplaced?.filter { it.isDigit() }

        viewModel.getPokemonsList(idType?.toInt()!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModelEvents()
        configureViewListeners()
    }

    override fun onResume() {
        super.onResume()

        getPokemons()
    }
}