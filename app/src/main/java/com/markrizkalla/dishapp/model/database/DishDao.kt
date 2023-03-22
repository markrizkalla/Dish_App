package com.markrizkalla.dishapp.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.markrizkalla.dishapp.model.entities.Dish
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


@Dao
interface DishDao {

    @Insert
    suspend fun insertDishDetails(dish:Dish)

    @Query("SELECT * FROM dishes_tab ORDER BY ID")
     fun getAllDishes() : Flow<List<Dish>>
}