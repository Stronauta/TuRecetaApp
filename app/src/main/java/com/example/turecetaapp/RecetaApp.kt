package com.example.turecetaapp

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.turecetaapp.data.local.database.MealDb
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RecetaApp : Application() {

    lateinit var database: MealDb

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            MealDb::class.java, "meal-database"
        ).build()
        Log.d("Database", "Database created: ${database.isOpen}")
    }
}
