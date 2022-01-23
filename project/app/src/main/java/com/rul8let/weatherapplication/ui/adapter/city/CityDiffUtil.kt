package com.rul8let.weatherapplication.ui.adapter.city

import androidx.recyclerview.widget.DiffUtil
import com.rul8let.weatherapplication.data.city.model.City
import com.rul8let.weatherapplication.data.city.network.model.CityData

class CityDiffUtil : DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem.name==newItem.name
    }

    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem==newItem
    }

}