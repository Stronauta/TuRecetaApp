package com.example.turecetaapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.turecetaapp.presentation.Meal.MealBody
import com.example.turecetaapp.presentation.category.CategoryListScreen
import com.example.turecetaapp.presentation.home.HomeScreen
import com.example.turecetaapp.presentation.home.HomeScreenBody

@Composable
fun TuRecetaNavHost(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen
    ) {

        composable<Screen.HomeScreen> {
            HomeScreen(
                onNavigateToCategories = {
                    navController.navigate(Screen.CategoriesScreen)
                }
            )
        }

        composable<Screen.CategoriesScreen> {
            CategoryListScreen(
                onCategoryItemClick = { category ->
                    // Example navigation action, replace "CategoryDetailScreen" with your actual destination
                    navController.navigate("CategoryDetailScreen/$category")
                }
            )
        }

        composable<Screen.MealList> {
            MealBody(
                onMealItemClick = { mealId ->
                    // Example navigation action, replace "MealDetailScreen" with your actual destination
                    navController.navigate("MealDetailScreen/$mealId")
                },
                goBack = navController
            )
        }
    }
}