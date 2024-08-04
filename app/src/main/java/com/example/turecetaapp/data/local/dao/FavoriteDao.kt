package com.example.turecetaapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.turecetaapp.data.local.entities.FavoriteFoodEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(Meal: FavoriteFoodEntity)

    @Query("DELETE FROM Favorites WHERE favoriteId = :favoriteId")
    suspend fun removeFavorite(favoriteId: Int)
}