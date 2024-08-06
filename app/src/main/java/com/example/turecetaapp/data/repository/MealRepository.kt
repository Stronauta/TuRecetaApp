package com.example.turecetaapp.data.repository

import android.util.Log
import com.example.turecetaapp.data.local.dao.CategoryDao
import com.example.turecetaapp.data.local.dao.MealDao
import com.example.turecetaapp.data.local.dao.MealDetailsDao
import com.example.turecetaapp.data.local.entities.CategoryEntity
import com.example.turecetaapp.data.local.entities.MealDetailsEntity
import com.example.turecetaapp.data.local.entities.MealEntity
import com.example.turecetaapp.data.remote.MealApi
import com.example.turecetaapp.data.remote.dto.Category
import com.example.turecetaapp.data.remote.dto.Meal
import com.example.turecetaapp.data.remote.dto.MealDetailResponse
import com.example.turecetaapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealRepository @Inject constructor(
    private val mealApi: MealApi,
    private val mealDao: MealDao,
    private val categoryDao: CategoryDao,
    private val mealDetailsDao: MealDetailsDao,
) {

    // Función para obtener y guardar categorías
    suspend fun fetchAndSaveCategories() {
        withContext(Dispatchers.IO) {
            try {
                val categoryResponse = mealApi.getCategories()
                val categories = categoryResponse.categories.map {
                    CategoryEntity(
                        idCategory = it.idCategory.toInt(),
                        strCategory = it.strCategory,
                        strCategoryThumb = it.strCategoryThumb
                    )
                }
                categoryDao.insert(categories)
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun fetchAndSaveMealsByCategory(category: String) {
        withContext(Dispatchers.IO) {
            try {
                val mealResponse = mealApi.getMealsByCategory(category)
                val meals = mealResponse.meals.map {
                    MealEntity(
                        idMeal = it.idMeal.toInt(),
                        strMeal = it.strMeal,
                        strMealThumb = it.strMealThumb
                    )
                }
                mealDao.insert(meals)
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Función para obtener y guardar detalles de una comida
    suspend fun fetchAndSaveMealDetails(mealId: String) {
        withContext(Dispatchers.IO) {
            try {
                val mealDetailResponse = mealApi.getMealById(mealId)
                val mealDetail = mealDetailResponse.meals.first()
                val mealDetailEntity = MealDetailsEntity(
                    idMeal = mealDetail.idMeal,
                    strMeal = mealDetail.strMeal,
                    strCategory = mealDetail.strCategory,
                    strArea = mealDetail.strArea,
                    strInstructions = mealDetail.strInstructions,
                    strMealThumb = mealDetail.strMealThumb,
                    strYoutube = mealDetail.strYoutube,
                    strIngredient1 = mealDetail.strIngredient1,
                    strIngredient2 = mealDetail.strIngredient2,
                    strIngredient3 = mealDetail.strIngredient3,
                    strIngredient4 = mealDetail.strIngredient4,
                    strIngredient5 = mealDetail.strIngredient5,
                    strIngredient6 = mealDetail.strIngredient6,
                    strIngredient7 = mealDetail.strIngredient7,
                    strIngredient8 = mealDetail.strIngredient8,
                    strIngredient9 = mealDetail.strIngredient9,
                    strIngredient10 = mealDetail.strIngredient10,
                    strMeasure1 = mealDetail.strMeasure1,
                    strMeasure2 = mealDetail.strMeasure2,
                    strMeasure3 = mealDetail.strMeasure3,
                    strMeasure4 = mealDetail.strMeasure4,
                    strMeasure5 = mealDetail.strMeasure5,
                    strMeasure6 = mealDetail.strMeasure6,
                    strMeasure7 = mealDetail.strMeasure7,
                    strMeasure8 = mealDetail.strMeasure8,
                    strMeasure9 = mealDetail.strMeasure9,
                    strMeasure10 = mealDetail.strMeasure10
                )
                mealDetailsDao.insert(mealDetailEntity)
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun insertMeal(meal: MealEntity) =
        mealDao.save(meal)

    suspend fun insertMeals(meals: List<MealEntity>) {
        meals.forEach { meal ->
            mealDao.save(meal)
        }
    }

    suspend fun insertMealDetail(mealDetail: MealDetailsEntity) =
        mealDetailsDao.save(mealDetail)

    suspend fun insertMealDetails(mealDetails: List<MealDetailsEntity>) {
        mealDetails.forEach { mealDetails ->
            mealDetailsDao.save(mealDetails)
        }
    }

    suspend fun insertCategory(category: CategoryEntity) =
        categoryDao.save(category)

    suspend fun insertCategories(categories: List<CategoryEntity>) {
        categories.forEach { category ->
            categoryDao.save(category)
        }
    }

    suspend fun find(id: Int): MealEntity? = mealDao.find(id)


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

    suspend fun getMealsByCategory(category: String): Flow<Resource<List<Meal>>> = flow {
        emit(Resource.Loading())
        try {
            val container = mealApi.getMealsByCategory(category)
            emit(Resource.Success(container.meals))
        } catch (e: Exception) {
            Log.e("MealRepository", "Error fetching meals", e)
            emit(Resource.Error("Error fetching meals"))
        }
    }

    suspend fun getMealsByArea(area: String): Flow<Resource<List<Meal>>> = flow {
        emit(Resource.Loading())
        try {
            val container = mealApi.getMealsByArea(area)
            emit(Resource.Success(container.meals))
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
