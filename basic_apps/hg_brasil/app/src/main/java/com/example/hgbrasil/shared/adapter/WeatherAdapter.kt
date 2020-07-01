package com.example.hgbrasil.shared.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hgbrasil.R
import com.example.hgbrasil.shared.model.Forecast
import com.example.hgbrasil.shared.viewholder.WeatherViewHolder

class WeatherAdapter(private val forecasts: List<Forecast>) : RecyclerView.Adapter<WeatherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_item_list, parent, false)
        return WeatherViewHolder(view)
    }

    override fun getItemCount() = forecasts.count()

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) = holder.bindData(forecasts[position])
}