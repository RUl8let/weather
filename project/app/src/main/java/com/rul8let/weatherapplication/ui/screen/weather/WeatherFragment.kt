package com.rul8let.weatherapplication.ui.screen.weather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rul8let.weatherapplication.data.Response
import com.rul8let.weatherapplication.databinding.WeatherBinding
import com.rul8let.weatherapplication.ui.adapter.forecast.ForecastAdapter
import com.rul8let.weatherapplication.R
import com.rul8let.weatherapplication.data.city.model.City
import com.rul8let.weatherapplication.data.weather.model.WeatherInformation
import com.rul8let.weatherapplication.ui.screen.city.CityFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val viewModel : WeatherViewModel by viewModels()
    private var _binding: WeatherBinding? = null
    private val binding get() = _binding!!

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var forecastAdapter: ForecastAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        setFragmentResultListener(CityFragment.requestKeyCityFragment) { _, bundle ->
            val citySelect = bundle.getParcelable<City>(CityFragment.bundleCity)
            if (citySelect != null) {
                viewModel.getWeather(citySelect.lon,citySelect.lat)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WeatherBinding.inflate(inflater,container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(binding.root.context)
        binding.adapterBind()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.weatherNow.observe(viewLifecycleOwner){
            when(it){
                is Response.Error -> {
                    Toast.makeText(
                        view.context,
                        getString(R.string.error_load_temp),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is Response.Success -> {
                    binding.updateCard(it.data)
                    viewModel.getWeatherWeek(it.data.lon,it.data.lat)
                }
            }
        }

        viewModel.weatherWeek.observe(viewLifecycleOwner){
            when(it){
                is Response.Error-> {
                    Toast.makeText(
                        view.context,
                        getString(R.string.error_load_temp),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Response.Success -> {
                    forecastAdapter.submitList(it.data)
                }
            }
        }

        if(viewModel.weatherNow.value == null){
            localWeather()
        }
    }

    private fun WeatherBinding.adapterBind(){
        forecastAdapter = ForecastAdapter()
        recycler.layoutManager = LinearLayoutManager(root.context)
        recycler.adapter = forecastAdapter
    }

    //Обнавляет погоду на основнйо cardView
    private fun WeatherBinding.updateCard(data: WeatherInformation) {
        cityName.text=data.cityName
        tempToday.text= getString(R.string.temp,data.tempToday.toString())
        weatherIconDescriptor.text=data.iconDescription
        Glide.with(root.context).load("https://openweathermap.org/img/wn/${data.icon}@4x.png").into(binding.weatherIcon)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.weather_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.city_search -> findNavController().navigate(R.id.action_weatherFragment_to_cityFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    //Запрашивает у пользователя разрешение
    private val getRegister = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        when (it) {
            true -> localWeather()
            false -> {}
        }
    }

    //Получает последнюю сохраненую гео позицию
    private fun localWeather(){
        if (ActivityCompat.checkSelfPermission(
                binding.root.context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                binding.root.context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            getRegister.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        fusedLocationClient.lastLocation.addOnSuccessListener {
            if(it!=null){
                viewModel.getWeather(it.longitude,it.latitude)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}