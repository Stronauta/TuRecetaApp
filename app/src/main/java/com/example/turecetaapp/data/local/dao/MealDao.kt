package com.example.turecetaapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.turecetaapp.data.local.entities.MealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meals: List<MealEntity>)

    @Upsert
    suspend fun save(meal: MealEntity)

    @Query(
        """
            SELECT * 
            FROM Meals
            WHERE idMeal = :id
            LIMIT 1
        """
    )
    suspend fun find(id: Int): MealEntity?

        @Query("SELECT * FROM Meals WHERE strCategory = :category")
        fun getMealsByCategory(category: String): Flow<List<MealEntity>>

    @Query("SELECT * FROM Meals")
    fun getAll(): Flow<List<MealEntity>>


}
