package com.example.turecetaapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.turecetaapp.data.local.dao.CategoryDao
import com.example.turecetaapp.data.local.dao.MealDao
import com.example.turecetaapp.data.local.dao.MealDetailsDao
import com.example.turecetaapp.data.local.database.MealDb
import com.example.turecetaapp.data.remote.MealApi
import com.example.turecetaapp.util.Constants.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideTuRecetaDb(@ApplicationContext appContext: Context): MealDb {
        return Room.databaseBuilder(
            appContext,
            MealDb::class.java,
            "TuReceta.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }


    @Singleton
    @Provides
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun providMeal(moshi: Moshi): MealApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(MealApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMealDao(database: MealDb): MealDao {
        return database.mealDao()
    }

    @Singleton
    @Provides
    fun provideCategoryDao(database: MealDb): CategoryDao {
        return database.categoryDao()
    }

    @Singleton
    @Provides
    fun provideMealDetailsDao(database: MealDb): MealDetailsDao {
        return database.MealDetailsDao()
    }

    /*    @Singleton
        @Provides
        fun provideADao(database: MealDb): aDao {
            return database.aDao()
        }*/
}
