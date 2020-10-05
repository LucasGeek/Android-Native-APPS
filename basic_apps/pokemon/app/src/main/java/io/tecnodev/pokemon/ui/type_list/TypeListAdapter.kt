package io.tecnodev.pokemon.ui.type_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.tecnodev.pokemon.R
import io.tecnodev.pokemon.shared.data.model.PokemonTypeModel
import kotlinx.android.synthetic.main.type_item.view.*

class TypeListAdapter(
    private val types: List<PokemonTypeModel>,
    private val onItemClickListener: ((book: PokemonTypeModel) -> Unit)
) : RecyclerView.Adapter<TypeListAdapter.TypeListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, view: Int): TypeListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.type_item, parent, false)
        return TypeListViewHolder(itemView, onItemClickListener)
    }

    override fun getItemCount() = types.count()

    override fun onBindViewHolder(viewHolder: TypeListViewHolder, position: Int) {
        viewHolder.bindView(types[position])
    }

    class TypeListViewHolder(
        itemView: View,
        private val onItemClickListener: ((pokemonTypeModel: PokemonTypeModel) -> Unit)
    ) : RecyclerView.ViewHolder(itemView) {

        private val name = itemView.text_type_name
        private val url = itemView.text_type_url

        fun bindView(type: PokemonTypeModel) {
            name.text = type.name
            url.text = type.url

            itemView.setOnClickListener {
                onItemClickListener.invoke(type)
            }
        }
    }
}