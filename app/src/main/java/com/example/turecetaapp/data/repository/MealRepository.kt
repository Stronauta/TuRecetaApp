package com.example.turecetaapp.data.repository

import com.example.turecetaapp.data.remote.MealApi
import com.example.turecetaapp.data.remote.dto.Category
import com.example.turecetaapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import android.util.Log
import javax.inject.Inject

class MealRepository @Inject constructor(
  private val mealApi: MealApi
) {

    fun getCategories(): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading())
        try {
            val container = mealApi.getCategories()
            emit(Resource.Success(container.categories))
        } catch (e: Exception) {
            Log.e("MealRepository", "Error fetching categories", e)
            emit(Resource.Error("Error fetching categories"))
        }
    }
}