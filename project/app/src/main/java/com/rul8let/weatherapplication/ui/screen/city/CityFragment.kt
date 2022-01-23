package com.rul8let.weatherapplication.ui.screen.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rul8let.weatherapplication.R
import com.rul8let.weatherapplication.data.Response
import com.rul8let.weatherapplication.data.city.model.City
import com.rul8let.weatherapplication.databinding.CitySearchBinding
import com.rul8let.weatherapplication.ui.adapter.city.CityAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityFragment : Fragment() {

    private val viewModel : CityViewModel by viewModels()
    private var _binding: CitySearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var cityAdapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CitySearchBinding.inflate(inflater,container,false)
        binding.adapterBind()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.cityList.observe(viewLifecycleOwner){
            when(it){
                is Response.Error -> {
                    Toast.makeText(
                        view.context,
                        getString(R.string.error_load_city),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Response.Success -> {
                    if(it.data.isEmpty()){
                        Toast.makeText(
                            view.context,
                            getString(R.string.city_not_found),
                            Toast.LENGTH_SHORT).show()
                    } else {
                        cityAdapter.submitList(it.data)
                    }
                }
            }
        }

        binding.textField.setEndIconOnClickListener {
            viewModel.getList(binding.editTextSearch.text.toString())
        }
    }

    fun CitySearchBinding.adapterBind(){
        cityAdapter = CityAdapter(clickItem)
        recyckerCity.layoutManager = LinearLayoutManager(root.context)
        recyckerCity.adapter = cityAdapter
    }

    //Получает объект на который нажал пользователь
    private val clickItem = fun (selectCity : City){
        setFragmentResult(requestKey = requestKeyCityFragment, bundleOf(bundleCity to selectCity))
        findNavController().navigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    companion object {
        const val requestKeyCityFragment = "CityFragment"
        const val bundleCity = "citySelect"
    }
}