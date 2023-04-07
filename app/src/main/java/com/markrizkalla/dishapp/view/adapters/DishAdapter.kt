package com.markrizkalla.dishapp.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.markrizkalla.dishapp.databinding.ItemDishLayoutBinding
import com.markrizkalla.dishapp.model.entities.Dish
import com.markrizkalla.dishapp.view.fragments.AllDishesFragment

class DishAdapter(private val fragment:Fragment) : RecyclerView.Adapter<DishAdapter.ViewHolder>() {

    private var dishes : List<Dish> = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding :ItemDishLayoutBinding = ItemDishLayoutBinding.inflate(LayoutInflater.from(fragment.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dish = dishes[position]

        Glide.with(fragment).load(dish.image).into(holder.ivDishImage)

        holder.tvTitle.text = dish.title

        holder.itemView.setOnClickListener {
            if (fragment is AllDishesFragment){
                fragment.dishDetails()
            }
        }
    }

    override fun getItemCount(): Int {
        return dishes.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun dishesList(list: List<Dish>) {
        dishes = list
        notifyDataSetChanged()
    }

    class ViewHolder(item:ItemDishLayoutBinding) :RecyclerView.ViewHolder(item.root){

        val ivDishImage = item.ivDishImage
        val tvTitle = item.tvDishTitle
    }



}