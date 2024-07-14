package com.example.turecetaapp.presentation.navigation

import kotlinx.serialization.Serializable


sealed class Screen(){

    @Serializable
    object  HomeScreen : Screen()


}