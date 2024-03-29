package com.markrizkalla.dishapp.view.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.markrizkalla.dishapp.R
import com.markrizkalla.dishapp.databinding.ItemDishLayoutBinding
import com.markrizkalla.dishapp.model.entities.Dish
import com.markrizkalla.dishapp.utils.Constants
import com.markrizkalla.dishapp.view.activities.AddUpdateDishActivity
import com.markrizkalla.dishapp.view.fragments.AllDishesFragment
import com.markrizkalla.dishapp.view.fragments.FavoriteDishesFragment

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
                fragment.dishDetails(dish);
            }

            if(fragment is FavoriteDishesFragment){
                fragment.dishDetails(dish)
            }
        }

        holder.ibMore.setOnClickListener{

            val popup = PopupMenu(fragment.context, holder.ibMore)
            popup.menuInflater.inflate(R.menu.menu_adapter,popup.menu)

            popup.setOnMenuItemClickListener {
                if (it.itemId == R.id.action_edit_dish){

                    val intent = Intent(fragment.requireActivity(),AddUpdateDishActivity::class.java)
                    intent.putExtra(Constants.EXTRA_DISH_DETAILS,dish)
                    fragment.requireActivity().startActivity(intent)

                }else if(it.itemId == R.id.action_delete_dish){
                    if (fragment is AllDishesFragment){
                        fragment.deleteDish(dish)
                    }
                }
                true
            }
            popup.show()
        }

        if (fragment is AllDishesFragment){
            holder.ibMore.visibility = View.VISIBLE
        }else if(fragment is FavoriteDishesFragment){
            holder.ibMore.visibility = View.GONE
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

        val ibMore = item.more
    }



}