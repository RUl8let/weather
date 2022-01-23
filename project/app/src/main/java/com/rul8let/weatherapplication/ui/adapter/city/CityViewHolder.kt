package com.rul8let.weatherapplication.ui.adapter.city

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rul8let.weatherapplication.R
import com.rul8let.weatherapplication.data.city.model.City
import com.rul8let.weatherapplication.databinding.CityCardBinding

class CityViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    private val binding = CityCardBinding.bind(itemView)

    fun bind (city: City, clickItem: (City) -> Unit){
        binding.cityName.text=city.name
        binding.cityState.text = city.state
        Glide.with(binding.root)
            .load("https://openweathermap.org/images/flags/${city.country.lowercase()}.png")
            .into(binding.flagImageView)

        binding.cityCard.setOnClickListener {
            clickItem(city)
        }
    }

    companion object {
        fun create(parent: ViewGroup) : CityViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val view= layoutInflater.inflate(R.layout.city_card,parent,false)
            return CityViewHolder(view)
        }
    }
}