package com.example.turecetaapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.turecetaapp.data.local.entities.Category
import com.example.turecetaapp.data.local.entities.Meal
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Upsert
    suspend fun upsertAll(categories: List<Category>)

    @Query(
        """
            SELECT * 
            FROM Categorys
            WHERE idCategory = :id
            LIMIT 1
        """
    )
    suspend fun find(id: Int): Category?

    @Query("SELECT * FROM Meals")
    fun getAll(): Flow<List<Category>>

}