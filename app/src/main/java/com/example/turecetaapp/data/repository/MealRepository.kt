package com.example.turecetaapp.data.repository

import com.example.turecetaapp.data.remote.MealApi
import com.example.turecetaapp.data.remote.dto.Category
import com.example.turecetaapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import android.util.Log
import com.example.turecetaapp.data.remote.dto.Meal
import com.example.turecetaapp.data.remote.dto.MealDetailResponse
import javax.inject.Inject
import retrofit2.HttpException
import java.io.IOException


class MealRepository @Inject constructor(
  private val mealApi: MealApi
) {

    suspend fun getCategories(): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading())
        try {
            val container = mealApi.getCategories()
            emit(Resource.Success(container.categories))
        } catch (e: Exception) {
            Log.e("MealRepository", "Error fetching categories", e)
            emit(Resource.Error("Error fetching categories"))
        }
    }

    suspend fun getMealsByCategory(category: String): Flow<Resource<List<Meal>>> = flow {
        emit(Resource.Loading())
        try {
            val container = mealApi.getMealsByCategory(category)
            emit(Resource.Success(container.meals))
        } catch(e: Exception) {
            Log.e("MealRepository", "Error fetching meals", e)
            emit(Resource.Error("Error fetching meals"))
        }
    }

     suspend fun getMealById(idMeal: String): Flow<Resource<MealDetailResponse>> = flow {
        try {
            emit(Resource.Loading())
            val meals = mealApi.getMealById(idMeal)
            emit(Resource.Success(meals))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}