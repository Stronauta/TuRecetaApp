package com.example.turecetaapp.data.repository

import android.util.Log
import com.example.turecetaapp.data.local.dao.CategoryDao
import com.example.turecetaapp.data.local.dao.MealDao
import com.example.turecetaapp.data.local.dao.MealDetailsDao
import com.example.turecetaapp.data.local.entities.CategoryEntity
import com.example.turecetaapp.data.local.entities.MealEntity
import com.example.turecetaapp.data.remote.MealApi
import com.example.turecetaapp.data.remote.dto.Category
import com.example.turecetaapp.data.remote.dto.Meal
import com.example.turecetaapp.data.remote.dto.MealDetailResponse
import com.example.turecetaapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealRepository @Inject constructor(
    private val mealApi: MealApi,
    private val mealDao: MealDao,
    private val categoryDao: CategoryDao,
    private val mealDetailsDao: MealDetailsDao
) {

    fun getCategories(): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading())
        try {
            Log.d("MealRepository", "Fetching categories from local database")
            val localCategories = categoryDao.getAll().firstOrNull()
            if (!localCategories.isNullOrEmpty()) {
                Log.d("MealRepository", "Local categories found: ${localCategories.size}")
                val categories = localCategories.map {
                    Category(
                        idCategory = it.idCategory.toString(),
                        strCategory = it.strCategory,
                        strCategoryThumb = it.strCategoryThumb
                    )
                }
                emit(Resource.Success(categories))
            } else {
                Log.d("MealRepository", "No local categories found, fetching from API")
                val container = mealApi.getCategories()
                val categories = container.categories.map {
                    CategoryEntity(
                        idCategory = it.idCategory.toInt(),
                        strCategory = it.strCategory,
                        strCategoryThumb = it.strCategoryThumb
                    )
                }
                Log.d("MealRepository", "Saving categories to local database")
                categoryDao.insert(categories)
                val mappedCategories = categories.map {
                    Category(
                        idCategory = it.idCategory.toString(),
                        strCategory = it.strCategory,
                        strCategoryThumb = it.strCategoryThumb
                    )
                }
                emit(Resource.Success(mappedCategories))
            }
        } catch (e: Exception) {
            Log.e("MealRepository", "Error fetching categories", e)
            emit(Resource.Error("Error fetching categories"))
        }
    }

    suspend fun getMealsByCategory(category: String): Flow<Resource<List<Meal>>> = flow {
        emit(Resource.Loading())
        try {
            Log.d("MealRepository", "Fetching meals by category from local database")

            // Obtener datos locales de la base de datos
            val localMeals = mealDao.getMealsByCategory(category).firstOrNull() ?: emptyList()

            if (localMeals.isNotEmpty()) {
                Log.d("MealRepository", "Local meals found: ${localMeals.size}")

                // Convertir MealEntity a Meal y emitir el resultado
                val meals = localMeals.map { mealEntity ->
                    Meal(
                        idMeal = mealEntity.idMeal.toString(),
                        strMeal = mealEntity.strMeal.toString(),
                        strMealThumb = mealEntity.strMealThumb ?: "",
                    )
                }
                emit(Resource.Success(meals))
            } else {
                Log.d("MealRepository", "No local meals found, fetching from API")

                // Obtener datos de la API
                val container = mealApi.getMealsByCategory(category)
                val meals = container.meals.map { mealDto ->
                    MealEntity(
                        idMeal = mealDto.idMeal.toInt(),
                        strMeal = mealDto.strMeal,
                        strMealThumb = mealDto.strMealThumb,
                        strCategory = category,
                    )
                }

                // Guardar los datos en la base de datos local
                Log.d("MealRepository", "Saving meals to local database")
                mealDao.insert(meals)

                // Convertir y emitir los datos obtenidos
                val mappedMeals = meals.map { mealEntity ->
                    Meal(
                        idMeal = mealEntity.idMeal.toString(),
                        strMeal = mealEntity.strMeal.toString(),
                        strMealThumb = mealEntity.strMealThumb ?: "",

                        )
                }
                emit(Resource.Success(mappedMeals))
            }
        } catch (e: Exception) {
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
