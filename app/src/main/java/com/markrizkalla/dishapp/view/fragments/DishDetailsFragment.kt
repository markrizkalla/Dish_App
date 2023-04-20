package com.markrizkalla.dishapp.view.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.markrizkalla.dishapp.R
import com.markrizkalla.dishapp.application.DishApp
import com.markrizkalla.dishapp.databinding.FragmentDishDetailsBinding
import com.markrizkalla.dishapp.viewmodel.DishViewModel
import com.markrizkalla.dishapp.viewmodel.DishViewModelFactory
import java.io.IOException


class DishDetailsFragment : Fragment() {
    private  var _binding : FragmentDishDetailsBinding?= null
    private val binding get() = _binding!!

    private val mDishViewModel : DishViewModel by viewModels {
        DishViewModelFactory(((requireActivity().application) as DishApp).repository)
    }


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


        args.let {

            try {
                Glide.with(requireActivity()).load(it.dishDetails.image).centerCrop()
                    .listener(object :RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            resource?.toBitmap()?.let { it1 ->
                                Palette.from(it1).generate(){

                                        palette ->
                                    val intColor = palette?.vibrantSwatch?.rgb?:0
                                    binding.rlDishDetailMain.setBackgroundColor(intColor)
                                }
                            }

                            return false
                        }

                    })
                    .into(binding.ivDishImage)
            }catch (e:IOException){
                e.printStackTrace()
            }
            binding.tvTitle.text = it.dishDetails.title
            binding.tvType.text = it.dishDetails.type
            binding.tvCategory.text = it.dishDetails.category
            binding.tvIngredients.text = it.dishDetails.ingredients
            binding.tvCookingDirection.text = it.dishDetails.directionToCook
            binding.tvCookingTime.text = it.dishDetails.cookingTime

            if (args.dishDetails.favoriteDish){
                binding.ivFavoriteDish.setImageDrawable(ContextCompat.getDrawable(requireActivity(),R.drawable.ic_favorite_selected))
            }else{
                binding.ivFavoriteDish.setImageDrawable(ContextCompat.getDrawable(requireActivity(),R.drawable.ic_favorite_unselected))

            }

        }

        binding.ivFavoriteDish.setOnClickListener {
            args.dishDetails.favoriteDish = !args.dishDetails.favoriteDish

            mDishViewModel.update(args.dishDetails)

            if (args.dishDetails.favoriteDish){
                binding.ivFavoriteDish.setImageDrawable(ContextCompat.getDrawable(requireActivity(),R.drawable.ic_favorite_selected))
            }else{
                binding.ivFavoriteDish.setImageDrawable(ContextCompat.getDrawable(requireActivity(),R.drawable.ic_favorite_unselected))

            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}