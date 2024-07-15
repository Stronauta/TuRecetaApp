package com.example.turecetaapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.turecetaapp.data.remote.dto.Meal
import com.example.turecetaapp.presentation.HomeBody

@Composable
fun TuRecetaNavHost(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen
    ) {
        composable<Screen.HomeScreen>{

            val samplemeal = Meal(
                idMeal = "52772",
                strMeal = "Chicken Handi",
                strMealThumb = "https://www.themealdb.com/images/media/meals/wyxwsp1486979827.jpg"
            )

            HomeBody(
                mealsItem = samplemeal,

            )
        }
    }
}