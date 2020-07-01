package com.example.hgbrasil.presentation.main.fragments.weather


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.hgbrasil.R
import com.example.hgbrasil.shared.adapter.WeatherAdapter
import com.example.hgbrasil.shared.model.Forecast
import com.example.hgbrasil.shared.model.Weather
import kotlinx.android.synthetic.main.fragment_weather.*

class WeatherFragment : Fragment(), View.OnClickListener {

    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_weather, container, false)

        mContext = rootView.context

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel : WeatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)

        viewModel.weatherLiveData.observe(this, Observer {
            txtCelcius.text = (it.temp).toString() + "°C"
            txtCity.text = it.city
            txtDescriptionWeather.text = it.description

            it.forecast?.let {
                forecasts: List<Forecast> ->
                recycleViewWeather.apply {
                    layoutManager = LinearLayoutManager(activity)
                    setHasFixedSize(true)
                    adapter = WeatherAdapter(forecasts)
                }
            }
        })

        viewModel.getWeather()
    }

    override fun onResume() {
        super.onResume()

        loadFragment()
    }

    override fun onClick(view: View) {
        when(view.id) {}
    }

    private fun loadFragment() {}
}
