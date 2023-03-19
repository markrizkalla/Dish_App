package com.markrizkalla.dishapp.application

import android.app.Application
import com.markrizkalla.dishapp.model.database.DishRepository
import com.markrizkalla.dishapp.model.database.DishRoomDatabase

class DishApp : Application() {

    private val database by lazy { DishRoomDatabase.getInstance(this@DishApp)}

    val repository by lazy { DishRepository(database.dishDao) }

}