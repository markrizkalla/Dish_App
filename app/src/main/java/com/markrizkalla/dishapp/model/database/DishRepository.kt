package com.markrizkalla.dishapp.model.database

import androidx.annotation.WorkerThread
import com.markrizkalla.dishapp.model.entities.Dish

class DishRepository(private val dishDao: DishDao) {

    @WorkerThread
    suspend fun insertDishData(dish: Dish){
        dishDao.insertDishDetails(dish)
    }
}