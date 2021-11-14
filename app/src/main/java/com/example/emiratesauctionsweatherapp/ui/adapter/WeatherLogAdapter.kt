package com.example.emiratesauctionsweatherapp.ui.adapter

import com.example.emiratesauctionsweatherapp.BR
import com.example.emiratesauctionsweatherapp.R
import com.example.emiratesauctionsweatherapp.data.model.WeatherLog
import com.example.emiratesauctionsweatherapp.databinding.ItemWeatherLogBinding
import com.example.emiratesauctionsweatherapp.ui.adapter.base.BaseRecyclerViewAdapter

class WeatherLogAdapter(onItemClickListener: OnItemClickListener<WeatherLog>) :
    BaseRecyclerViewAdapter<WeatherLog, ItemWeatherLogBinding>(onItemClickListener) {

    override fun getItemLayoutId(): Int {
        return R.layout.item_weather_log
    }

    override fun getViewBindingVariableId(): Int {
        return BR.weatherLog
    }

    override fun onViewHolderBinding(
        viewDataBinding: ItemWeatherLogBinding?,
        item: WeatherLog?,
        position: Int
    ) {
    }
}