package com.example.turecetaapp.navigation

import MealDetailScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.turecetaapp.presentation.Meal.MealListScreen
import com.example.turecetaapp.presentation.authentication.HomePage
import com.example.turecetaapp.presentation.authentication.LoginPage
import com.example.turecetaapp.presentation.authentication.ProfileScreen
import com.example.turecetaapp.presentation.authentication.SignupPage
import com.example.turecetaapp.presentation.category.CategoryListScreen
import com.example.turecetaapp.presentation.home.HomeScreen

@Composable
fun TuRecetaNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.CategoriesList
    ) {

        composable<Screen.LoginScreen> {
            LoginPage(
                navController = navController,
                authViewModel = hiltViewModel()
            )
        }

        composable<Screen.SignupScreen> {
            SignupPage(
                navController = navController,
                authViewModel = hiltViewModel()
            )
        }

        composable<Screen.HomeScreen2> {
            HomePage(
                navController = navController,
                authViewModel = hiltViewModel()
            )
        }

        composable<Screen.ProfileScreen> {
            ProfileScreen(
                authViewModel = hiltViewModel(),
                navController = navController,
            )
        }

        composable<Screen.HomeScreen> {
            HomeScreen(
                onEnterApp = {
                    navController.navigate(Screen.CategoriesList)
                }
            )
        }

        composable<Screen.CategoriesList> {
            CategoryListScreen(
                onCategoryItemClick = { category ->
                    navController.navigate(Screen.CategoriesMealScreen(category))
                },
                authViewModel = hiltViewModel(),
                navController = navController,
            )
        }

        composable<Screen.CategoriesMealScreen> { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            MealListScreen(
                category = category,
                onMealItemClick = { mealId ->
                    navController.navigate(Screen.CategoriesMealScreen)
                },
                navController = navController,
                authViewModel = hiltViewModel()
            )
        }

        composable<Screen.MealDetailScreen> {
            val mealId = it.arguments?.getString("mealId") ?: ""
            MealDetailScreen(
                mealId = mealId,
                viewModel = hiltViewModel(),
                navController = navController
            )
        }


    }
}
