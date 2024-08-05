package com.example.turecetaapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.turecetaapp.data.local.dao.CategoryDao
import com.example.turecetaapp.data.local.dao.MealDao
import com.example.turecetaapp.data.local.entities.CategoryEntity
import com.example.turecetaapp.data.local.entities.MealEntity

@Database(
    entities = [
        MealEntity::class,
        CategoryEntity::class,
    ],
    version = 5,
    exportSchema = false,
)

abstract class MealDb : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun categoryDao(): CategoryDao
}
