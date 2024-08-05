package com.example.turecetaapp.data.Mapper

import com.example.turecetaapp.data.local.entities.MealEntity
import com.example.turecetaapp.data.remote.dto.Meal
import com.example.turecetaapp.data.remote.dto.MealDetails

fun MealDetails.toMealEntity(): MealEntity {
    return MealEntity(
        idMeal = this.idMeal.toIntOrNull(),
        strMeal = this.strMeal,
        strCategory = this.strCategory,
        strArea = this.strArea,
        strInstructions = this.strInstructions,
        strMealThumb = this.strMealThumb,
        strTags = null, // No hay un campo `strTags` en `MealDetails`, dejar como null
        strYoutube = null, // No hay un campo `strYoutube` en `MealDetails`, dejar como null
        strSource = null, // No hay un campo `strSource` en `MealDetails`, dejar como null
        strImageSource = null, // No hay un campo `strImageSource` en `MealDetails`, dejar como null
        strCreativeCommonsConfirmed = null, // No hay un campo `strCreativeCommonsConfirmed`, dejar como null
        dateModified = null // No hay un campo `dateModified` en `MealDetails`, dejar como null
    )
}

fun Meal.toMealEntity(): MealEntity {
    return MealEntity(
        idMeal = this.idMeal.toIntOrNull(),
        strMeal = this.strMeal,
        strMealThumb = this.strMealThumb

    )
}
