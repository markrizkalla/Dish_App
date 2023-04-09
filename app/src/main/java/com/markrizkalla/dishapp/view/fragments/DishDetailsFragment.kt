package com.markrizkalla.dishapp.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.markrizkalla.dishapp.databinding.FragmentDishDetailsBinding
import java.io.IOException


class DishDetailsFragment : Fragment() {
    private  var _binding : FragmentDishDetailsBinding?= null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDishDetailsBinding.inflate(inflater, container, false)
        val view = binding?.root


        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args:DishDetailsFragmentArgs by navArgs()
        Log.i("Dish Title",args.dishDetails.title)

        args.let {

            try {
                Glide.with(requireActivity()).load(it.dishDetails.image).into(binding.ivDishImage)
            }catch (e:IOException){
                e.printStackTrace()
            }
            binding.tvTitle.text = it.dishDetails.title
            binding.tvType.text = it.dishDetails.type
            binding.tvCategory.text = it.dishDetails.category
            binding.tvIngredients.text = it.dishDetails.ingredients
            binding.tvCookingDirection.text = it.dishDetails.directionToCook
            binding.tvCookingTime.text = it.dishDetails.cookingTime


        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}