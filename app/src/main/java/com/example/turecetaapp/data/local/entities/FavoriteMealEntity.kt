package com.example.turecetaapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorites")
data class FavoriteFoodEntity(
    @PrimaryKey
    val favoriteId: Int,
    val strMeal: String,
    val strMealThumb: String

)
