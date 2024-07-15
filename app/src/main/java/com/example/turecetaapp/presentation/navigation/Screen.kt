package com.example.turecetaapp.presentation.navigation

import kotlinx.serialization.Serializable


sealed class Screen{

    @Serializable
    data object  HomeScreen : Screen()


}