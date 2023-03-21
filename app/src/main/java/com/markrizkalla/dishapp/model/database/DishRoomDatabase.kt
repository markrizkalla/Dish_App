package com.markrizkalla.dishapp.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.markrizkalla.dishapp.model.entities.Dish

@Database(entities = [Dish::class], version = 2, exportSchema = false)
abstract class DishRoomDatabase:RoomDatabase() {
    abstract val dishDao: DishDao

    companion object{
        @Volatile
        private var INSTANCE:DishRoomDatabase? = null

        fun getInstance(context: Context):DishRoomDatabase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DishRoomDatabase::class.java,
                        "dish_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}