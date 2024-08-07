package com.example.turecetaapp.presentation.meal_details

import com.example.turecetaapp.data.local.entities.FavoriteMealEntity
import com.example.turecetaapp.data.remote.dto.MealDetails

fun MealDetails.toFavoriteMealEntity(): FavoriteMealEntity {
    return FavoriteMealEntity(
        idMeal = this.idMeal,
        strMeal = this.strMeal,
        strCategory = this.strCategory,
        strArea = this.strArea,
        strInstructions = this.strInstructions,
        strMealThumb = this.strMealThumb,
        strYoutube = this.strYoutube,
        strIngredient1 = this.strIngredient1,
        strIngredient2 = this.strIngredient2,
        strIngredient3 = this.strIngredient3,
        strIngredient4 = this.strIngredient4,
        strIngredient5 = this.strIngredient5,
        strIngredient6 = this.strIngredient6,
        strIngredient7 = this.strIngredient7,
        strIngredient8 = this.strIngredient8,
        strIngredient9 = this.strIngredient9,
        strIngredient10 = this.strIngredient10,
        strMeasure1 = this.strMeasure1,
        strMeasure2 = this.strMeasure2,
        strMeasure3 = this.strMeasure3,
        strMeasure4 = this.strMeasure4,
        strMeasure5 = this.strMeasure5,
        strMeasure6 = this.strMeasure6,
        strMeasure7 = this.strMeasure7,
        strMeasure8 = this.strMeasure8,
        strMeasure9 = this.strMeasure9,
        strMeasure10 = this.strMeasure10
    )
}
