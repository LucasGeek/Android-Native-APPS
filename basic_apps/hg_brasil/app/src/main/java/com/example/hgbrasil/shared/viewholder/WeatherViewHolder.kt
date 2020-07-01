package com.example.hgbrasil.shared.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hgbrasil.shared.model.Forecast
import kotlinx.android.synthetic.main.weather_item_list.view.*

class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title = itemView.txtTitleForecast
    private val subTitle = itemView.txtSubTitleForecast
    private val max = itemView.txtTitleMax
    private val min = itemView.txtTitleMin

    fun bindData(forecast: Forecast) {
        title.text = forecast.weekday + " - " + forecast.date
        subTitle.text = forecast.description
        max.text = (forecast.max).toString() + "°"
        min.text = (forecast.min).toString() + "°"
    }
}