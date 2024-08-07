package com.example.turecetaapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.turecetaapp.data.local.entities.MealDetailsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mealDetails: MealDetailsEntity)

    @Upsert
    suspend fun save(details: MealDetailsEntity)

    @Query(
        """
            SELECT * 
            FROM meals_details
            WHERE idMeal = :id
            LIMIT 1
        """
    )
    suspend fun getMealDetailById(id: String): MealDetailsEntity?

    @Query("SELECT * FROM meals_details WHERE idMeal = :idMeal")
    suspend fun getMealById(idMeal: String): MealDetailsEntity?

    @Query("SELECT * FROM meals_details")
    fun getAll(): Flow<List<MealDetailsEntity>>
}
