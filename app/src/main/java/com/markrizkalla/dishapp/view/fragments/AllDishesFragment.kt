package com.markrizkalla.dishapp.view.fragments

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.markrizkalla.dishapp.R
import com.markrizkalla.dishapp.application.DishApp
import com.markrizkalla.dishapp.databinding.FragmentAllDishesBinding
import com.markrizkalla.dishapp.view.activities.AddUpdateDishActivity
import com.markrizkalla.dishapp.view.adapters.DishAdapter
import com.markrizkalla.dishapp.viewmodel.DishViewModel
import com.markrizkalla.dishapp.viewmodel.DishViewModelFactory
import com.markrizkalla.dishapp.viewmodel.HomeViewModel

class AllDishesFragment : Fragment() {

    private var _binding: FragmentAllDishesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAllDishesBinding.inflate(inflater, container, false)

        val root: View = binding.root



        setHasOptionsMenu(true)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = DishViewModelFactory((requireActivity().application as DishApp).repository)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(DishViewModel::class.java)

        binding.rvDishesList.layoutManager = GridLayoutManager(requireActivity(),2)
        val dishAdapter = DishAdapter(this)
        binding.rvDishesList.adapter = dishAdapter

        viewModel.allDishes.observe(viewLifecycleOwner, Observer {
            it.let {
                if (it.isNotEmpty()){
                    binding.rvDishesList.visibility = View.VISIBLE
                    binding.tvNoDishesAddedYet.visibility = View.GONE

                    dishAdapter.dishesList(it)
                }else{
                    binding.rvDishesList.visibility = View.GONE
                    binding.tvNoDishesAddedYet.visibility = View.VISIBLE
                }
            }
        })
    }

    fun dishDetails(){
        findNavController().navigate(AllDishesFragmentDirections.actionNavigationHomeToDishDetailsFragment())
    }


    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_all_dishes,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_add_dish -> {
                startActivity(Intent(requireActivity(),AddUpdateDishActivity::class.java))
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}