package com.example.turecetaapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun TuRecetaNavHost(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen
    ) {

    }
}