package com.example.turecetaapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.turecetaapp.data.local.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categories: CategoryEntity)

    @Upsert
    suspend fun save(categories: CategoryEntity)

    @Query(
        """
            SELECT * 
            FROM Categories
            WHERE idCategory = :id
            LIMIT 1
        """
    )
    suspend fun find(id: Int): CategoryEntity?

    @Query("SELECT * FROM Categories")
    fun getAll(): Flow<List<CategoryEntity>>


}
