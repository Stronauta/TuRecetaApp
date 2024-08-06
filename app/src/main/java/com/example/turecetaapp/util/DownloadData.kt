package com.example.turecetaapp.util

import com.example.turecetaapp.data.remote.MealApi
import com.example.turecetaapp.data.repository.MealRepository
import javax.inject.Inject

class DataDownload @Inject constructor(

    private val mealRepository: MealRepository,
    private val MealApi: MealApi
) {

    /*    suspend fun downloadMealData() {
            try {
                val categoriesResponse = MealApi.getCategories()
                val categories = categoriesResponse.categories

                categories.forEach { category ->
                    val mealsResponse = MealApi.getMealsByCategory(category.strCategory)
                    val meals = mealsResponse.meals

                    meals.forEach { meal ->
                        mealRepository.insertMeal(meal)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }*/


}
