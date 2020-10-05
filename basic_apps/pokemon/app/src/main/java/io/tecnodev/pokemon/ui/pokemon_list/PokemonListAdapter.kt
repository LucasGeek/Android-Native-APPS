package io.tecnodev.pokemon.ui.pokemon_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.tecnodev.pokemon.R
import io.tecnodev.pokemon.shared.data.model.PokemonModel
import kotlinx.android.synthetic.main.pokemon_item.view.*

class PokemonListAdapter(
    private val pokemons: List<PokemonModel>,
    private val onItemClickListener: ((book: PokemonModel) -> Unit)
) : RecyclerView.Adapter<PokemonListAdapter.PokemonListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, view: Int): PokemonListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)
        return PokemonListViewHolder(itemView, onItemClickListener)
    }

    override fun getItemCount() = pokemons.count()

    override fun onBindViewHolder(viewHolder: PokemonListViewHolder, position: Int) {
        viewHolder.bindView(pokemons[position])
    }

    class PokemonListViewHolder(
        itemView: View,
        private val onItemClickListener: ((pokemonModel: PokemonModel) -> Unit)
    ) : RecyclerView.ViewHolder(itemView) {

        private val name = itemView.text_pokemon_name
        private val url = itemView.text_pokemon_url

        fun bindView(pokemon: PokemonModel) {
            name.text = pokemon.name
            url.text = pokemon.url

            itemView.setOnClickListener {
                onItemClickListener.invoke(pokemon)
            }
        }
    }
}