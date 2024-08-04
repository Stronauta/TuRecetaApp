package com.example.turecetaapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Meals")
class MealEntity {
    @PrimaryKey
    var idMeal: Int? = null
    var strMeal: String? = null
    var strCategory: String? = null
    var strDrinkAlternate: String? = null
    var strArea: String? = null
    var strInstructions: String? = null
    var strMealThumb: String? = null
    var strTags: String? = null
    var strYoutube: String? = null
    var strSource: String? = null
    var strImageSource: String? = null
    var strCreativeCommonsConfirmed: String? = null
    var dateModified: String? = null
}

@Entity(tableName = "Ingredients")
class IngredientEntity {
    @PrimaryKey
    var idMeal: Int? = null
    var ingredient: String? = null
    var measure: String? = null
}
