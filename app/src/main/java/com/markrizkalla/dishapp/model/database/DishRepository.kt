package com.markrizkalla.dishapp.model.database

import androidx.annotation.WorkerThread
import com.markrizkalla.dishapp.model.entities.Dish
import kotlinx.coroutines.flow.Flow

class DishRepository(private val dishDao: DishDao) {

    @WorkerThread
    suspend fun insertDishData(dish: Dish){
        dishDao.insertDishDetails(dish)
    }

     fun getAllDishes() : Flow<List<Dish>> {
        return dishDao.getAllDishes()
    }

    @WorkerThread
    suspend fun updateDishData(dish: Dish){
        dishDao.updateDishDetails(dish)
    }

    val favoriteDishes:Flow<List<Dish>> = dishDao.getFavoriteDishesList()

    @WorkerThread
    suspend fun deleteDish(dish: Dish){
        dishDao.deleteDish(dish)
    }

}