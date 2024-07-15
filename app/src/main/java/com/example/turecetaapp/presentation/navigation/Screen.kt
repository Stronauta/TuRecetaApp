package com.example.turecetaapp.presentation.navigation

import kotlinx.serialization.Serializable


sealed class Screen{

    @Serializable
    object  MealList : Screen()

    @Serializable
    data class MealBody(val idMeal: String) : Screen()

    @Serializable
    data object  CategoriesScreen : Screen()



}