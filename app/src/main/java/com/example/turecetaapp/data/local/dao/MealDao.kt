package com.example.turecetaapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.turecetaapp.data.local.entities.Meal
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Upsert
    suspend fun upsertAll(meals: List<Meal>)

    @Query(
        """
            SELECT * 
            FROM Meals
            WHERE idMeal = :id
            LIMIT 1
        """
    )
    suspend fun find(id: Int): Meal?

    @Query("SELECT * FROM Meals")
    fun getAll(): Flow<List<Meal>>

}