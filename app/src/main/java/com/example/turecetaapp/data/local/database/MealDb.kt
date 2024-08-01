package com.example.turecetaapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.turecetaapp.data.local.dao.CategoryDao
import com.example.turecetaapp.data.local.dao.MealDao
import com.example.turecetaapp.data.local.entities.Category
import com.example.turecetaapp.data.local.entities.Ingredient
import com.example.turecetaapp.data.local.entities.Meal

@Database(
    entities = [
        Meal::class,
        Category::class,
        Ingredient::class
    ],
    version = 1,
    exportSchema = true,
)

abstract class MealDb : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun categoryDao(): CategoryDao
}