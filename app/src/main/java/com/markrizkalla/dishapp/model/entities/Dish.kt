package com.markrizkalla.dishapp.model.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize // to transfer object through fragments
@Entity(tableName = "dishes_tab")
data class Dish(
    @ColumnInfo val image:String,
    @ColumnInfo(name = "image_source") val imageSource: String,
    @ColumnInfo val title: String,
    @ColumnInfo val type: String,
    @ColumnInfo val category: String,
    @ColumnInfo val ingredients: String,
    @ColumnInfo(name = "cooking_time") val cookingTime: String,
    @ColumnInfo(name = "instructions") val directionToCook: String,
    @ColumnInfo(name = "favorite_dish") var favoriteDish: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id:Int=0

):Parcelable