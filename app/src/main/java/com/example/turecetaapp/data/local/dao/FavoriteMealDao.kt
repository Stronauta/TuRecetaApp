package com.example.turecetaapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.turecetaapp.data.local.entities.FavoriteMealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMeal(favoriteMeal: FavoriteMealEntity)

    @Delete
    suspend fun deleteFavoriteMeal(favoriteMeal: FavoriteMealEntity)

    @Query("SELECT * FROM favorite_meals")
    fun getAllFavoriteMeals(): Flow<List<FavoriteMealEntity>>
}
