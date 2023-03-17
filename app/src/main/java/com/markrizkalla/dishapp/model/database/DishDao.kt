package com.markrizkalla.dishapp.model.database

import androidx.room.Dao
import androidx.room.Insert
import com.markrizkalla.dishapp.model.entities.Dish


@Dao
interface DishDao {

    @Insert
    suspend fun insertDishDetails(dish:Dish)

}