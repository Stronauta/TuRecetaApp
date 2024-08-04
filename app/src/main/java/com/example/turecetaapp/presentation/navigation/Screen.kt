package com.example.turecetaapp.presentation.navigation

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
    data class CategoriesMealScreen(val category: String) : Screen()


}
