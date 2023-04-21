package com.markrizkalla.dishapp.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.markrizkalla.dishapp.application.DishApp
import com.markrizkalla.dishapp.databinding.FragmentDashboardBinding
import com.markrizkalla.dishapp.view.adapters.DishAdapter
import com.markrizkalla.dishapp.viewmodel.DashboardViewModel
import com.markrizkalla.dishapp.viewmodel.DishViewModel
import com.markrizkalla.dishapp.viewmodel.DishViewModelFactory
import kotlin.math.log

class FavoriteDishesFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    private val mDishViewMode : DishViewModel by viewModels {
        DishViewModelFactory((requireActivity().application as DishApp).repository)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDishViewMode.favoriteDishes.observe(viewLifecycleOwner){
            dishes ->
            dishes.let {


                binding.rvFavoriteDishesList.layoutManager =
                       GridLayoutManager(requireActivity(),2)

                val adapter = DishAdapter(this)
                binding.rvFavoriteDishesList.adapter = adapter

                if (it.isNotEmpty()){
                    binding.tvNoFavoriteDishes.visibility = View.VISIBLE
                    binding.tvNoFavoriteDishes.visibility = View.GONE

                    adapter.dishesList(dishes)
                }else{
                    binding.tvNoFavoriteDishes.visibility = View.VISIBLE
                    binding.rvFavoriteDishesList.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}