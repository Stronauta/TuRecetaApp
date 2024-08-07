package com.example.turecetaapp.navigation

import kotlinx.serialization.Serializable


sealed class Screen {

    @Serializable
    data object HomeScreen : Screen()

    @Serializable
    data object CategoriesList : Screen()

    @Serializable
    data object LoginScreen : Screen()

    @Serializable
    data object SignupScreen : Screen()

    @Serializable
    data object HomeScreen2 : Screen()

    @Serializable
    data object ProfileScreen : Screen()

    @Serializable
    data object FavoriteScreen : Screen()

    @Serializable
    data class CategoriesMealScreen(val category: String) : Screen()

    @Serializable
    data class MealDetailScreen(val mealId: String) : Screen()


}
