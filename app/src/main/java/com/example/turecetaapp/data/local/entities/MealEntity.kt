package com.example.turecetaapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Meals")
class MealEntity {
    @PrimaryKey
    var idMeal: Int? = null
    val strMeal: String? = null
    val strCategory: String? = null
    val strDrinkAlternate: String? = null
    val strArea: String? = null
    val strInstructions: String? = null
    val strMealThumb: String? = null
    val strTags: String? = null
    val strYoutube: String? = null
    val strSource: String? = null
    val strImageSource: String? = null
    val strCreativeCommonsConfirmed: String? = null
    val dateModified: String? = null
    val ingredients: List<IngredientEntity> = emptyList()

}

@Entity(tableName = "Ingredients")
class IngredientEntity {
    @PrimaryKey
    var idMeal: Int? = null
    var ingredient: List<String>? = null
    var measure: String? = null
}