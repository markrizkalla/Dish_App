package com.markrizkalla.dishapp.viewmodel

import androidx.lifecycle.*
import com.markrizkalla.dishapp.model.database.DishRepository
import com.markrizkalla.dishapp.model.entities.Dish
import kotlinx.coroutines.launch

class DishViewModel(private val repository: DishRepository) : ViewModel() {

    fun insert(dish: Dish){
        viewModelScope.launch {
            repository.insertDishData(dish)
        }
    }

    val allDishes : LiveData<List<Dish>> = repository.getAllDishes().asLiveData()

    fun update(dish: Dish){
        viewModelScope.launch {
            repository.updateDishData(dish)
        }
    }
}

class DishViewModelFactory(private val repository: DishRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       if (modelClass.isAssignableFrom(DishViewModel::class.java)){
           return DishViewModel(repository) as T
       }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}