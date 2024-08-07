package com.example.turecetaapp.data.repository

import android.util.Log
import com.example.turecetaapp.data.local.dao.CategoryDao
import com.example.turecetaapp.data.local.dao.FavoriteMealDao
import com.example.turecetaapp.data.local.dao.MealDao
import com.example.turecetaapp.data.local.dao.MealDetailsDao
import com.example.turecetaapp.data.local.entities.CategoryEntity
import com.example.turecetaapp.data.local.entities.FavoriteMealEntity
import com.example.turecetaapp.data.local.entities.MealDetailsEntity
import com.example.turecetaapp.data.local.entities.MealEntity
import com.example.turecetaapp.data.remote.MealApi
import com.example.turecetaapp.data.remote.dto.Category
import com.example.turecetaapp.data.remote.dto.Meal
import com.example.turecetaapp.data.remote.dto.MealDetailResponse
import com.example.turecetaapp.data.remote.dto.MealDetails
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
    private val mealDetailsDao: MealDetailsDao,
    private val favoriteMealDao: FavoriteMealDao,
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

            val localMeals = mealDao.getMealsByCategory(category).firstOrNull() ?: emptyList()

            if (localMeals.isNotEmpty()) {
                Log.d("MealRepository", "Local meals found: ${localMeals.size}")

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

                val container = mealApi.getMealsByCategory(category)
                val meals = container.meals.map { mealDto ->
                    MealEntity(
                        idMeal = mealDto.idMeal.toInt(),
                        strMeal = mealDto.strMeal,
                        strMealThumb = mealDto.strMealThumb,
                        strCategory = category,
                    )
                }

                Log.d("MealRepository", "Saving meals to local database")
                mealDao.insert(meals)

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

    suspend fun getMealById(idMeal: String): Flow<Resource<MealDetails>> = flow {
        emit(Resource.Loading())

        // Primero, intenta obtener los datos de la base de datos local
        val localMealDetail = mealDetailsDao.getMealById(idMeal)
        if (localMealDetail != null) {
            emit(Resource.Success(localMealDetail.toDomainModel()))
        } else {
            // Si no hay datos locales, obtén los datos de la API
            try {
                val mealDetailResponse = mealApi.getMealById(idMeal)
                val mealDetail = mealDetailResponse.meals.first()

                // Convertir la respuesta de la API a la entidad de la base de datos
                val mealDetailsEntity = MealDetailsEntity(
                    idMeal = mealDetail.idMeal,
                    strMeal = mealDetail.strMeal,
                    strCategory = mealDetail.strCategory,
                    strArea = mealDetail.strArea,
                    strInstructions = mealDetail.strInstructions,
                    strMealThumb = mealDetail.strMealThumb,
                    strYoutube = mealDetail.strYoutube ?: "",
                    strIngredient1 = mealDetail.strIngredient1 ?: "",
                    strIngredient2 = mealDetail.strIngredient2 ?: "",
                    strIngredient3 = mealDetail.strIngredient3 ?: "",
                    strIngredient4 = mealDetail.strIngredient4 ?: "",
                    strIngredient5 = mealDetail.strIngredient5 ?: "",
                    strIngredient6 = mealDetail.strIngredient6 ?: "",
                    strIngredient7 = mealDetail.strIngredient7 ?: "",
                    strIngredient8 = mealDetail.strIngredient8 ?: "",
                    strIngredient9 = mealDetail.strIngredient9 ?: "",
                    strIngredient10 = mealDetail.strIngredient10 ?: "",
                    strMeasure1 = mealDetail.strMeasure1 ?: "",
                    strMeasure2 = mealDetail.strMeasure2 ?: "",
                    strMeasure3 = mealDetail.strMeasure3 ?: "",
                    strMeasure4 = mealDetail.strMeasure4 ?: "",
                    strMeasure5 = mealDetail.strMeasure5 ?: "",
                    strMeasure6 = mealDetail.strMeasure6 ?: "",
                    strMeasure7 = mealDetail.strMeasure7 ?: "",
                    strMeasure8 = mealDetail.strMeasure8 ?: "",
                    strMeasure9 = mealDetail.strMeasure9 ?: "",
                    strMeasure10 = mealDetail.strMeasure10 ?: ""
                )

                // Guardar los datos en la base de datos local
                mealDetailsDao.insert(mealDetailsEntity)

                // Emitir los datos obtenidos
                emit(Resource.Success(mealDetailsEntity.toDomainModel()))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection"))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
    }

    suspend fun addFavoriteMeal(meal: FavoriteMealEntity) {
        favoriteMealDao.insertFavoriteMeal(meal)
    }

    suspend fun removeFavoriteMeal(meal: FavoriteMealEntity) {
        favoriteMealDao.deleteFavoriteMeal(meal)
    }

    fun getFavoriteMeals(): Flow<List<FavoriteMealEntity>> {
        return favoriteMealDao.getAllFavoriteMeals()
    }
}

// Función de extensión para convertir MealDetailsEntity a MealDetails
fun MealDetailsEntity.toDomainModel(): MealDetails {
    return MealDetails(
        idMeal = this.idMeal,
        strMeal = this.strMeal,
        strCategory = this.strCategory,
        strArea = this.strArea,
        strInstructions = this.strInstructions,
        strMealThumb = this.strMealThumb,
        strYoutube = this.strYoutube,
        strIngredient1 = this.strIngredient1,
        strIngredient2 = this.strIngredient2,
        strIngredient3 = this.strIngredient3,
        strIngredient4 = this.strIngredient4,
        strIngredient5 = this.strIngredient5,
        strIngredient6 = this.strIngredient6,
        strIngredient7 = this.strIngredient7,
        strIngredient8 = this.strIngredient8,
        strIngredient9 = this.strIngredient9,
        strIngredient10 = this.strIngredient10,
        strMeasure1 = this.strMeasure1,
        strMeasure2 = this.strMeasure2,
        strMeasure3 = this.strMeasure3,
        strMeasure4 = this.strMeasure4,
        strMeasure5 = this.strMeasure5,
        strMeasure6 = this.strMeasure6,
        strMeasure7 = this.strMeasure7,
        strMeasure8 = this.strMeasure8,
        strMeasure9 = this.strMeasure9,
        strMeasure10 = this.strMeasure10
    )
}
