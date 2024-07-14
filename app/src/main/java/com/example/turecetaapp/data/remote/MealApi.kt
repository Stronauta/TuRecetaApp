package com.example.turecetaapp.data.remote

import com.example.turecetaapp.data.remote.dto.CategoryResponse
import com.example.turecetaapp.data.remote.dto.MealDetailResponse
import com.example.turecetaapp.data.remote.dto.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") strCategory: String): MealResponse

    @GET("lookup.php")
    suspend fun getMealById(@Query("i") mealId: String): MealDetailResponse
}