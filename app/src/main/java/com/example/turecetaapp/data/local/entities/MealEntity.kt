package com.example.turecetaapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Meals")
data class MealEntity(
    @PrimaryKey
    var idMeal: Int? = null,
    var strMeal: String? = null,
    var strMealThumb: String? = null,
    //  var strTags: String? = null,
)
