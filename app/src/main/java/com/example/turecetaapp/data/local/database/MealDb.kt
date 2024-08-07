package com.example.turecetaapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.turecetaapp.data.local.dao.CategoryDao
import com.example.turecetaapp.data.local.dao.MealDao
import com.example.turecetaapp.data.local.dao.MealDetailsDao
import com.example.turecetaapp.data.local.entities.CategoryEntity
import com.example.turecetaapp.data.local.entities.MealDetailsEntity
import com.example.turecetaapp.data.local.entities.MealEntity

@Database(
    entities = [
        MealEntity::class,
        CategoryEntity::class,
        MealDetailsEntity::class
    ],
    version = 6,
    exportSchema = false,
)

abstract class MealDb : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun categoryDao(): CategoryDao
    abstract fun mealDetailsDao(): MealDetailsDao
}
