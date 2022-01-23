package com.rul8let.weatherapplication.ui.adapter.city

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rul8let.weatherapplication.data.city.model.City

class CityAdapter(private val clickItem: (City) -> Unit) : ListAdapter<City,RecyclerView.ViewHolder>(CityDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CityViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
           is CityViewHolder -> holder.bind(currentList[position], clickItem)
        }
    }
}