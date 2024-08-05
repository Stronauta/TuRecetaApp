package com.example.turecetaapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Categories")
data class CategoryEntity(
    @PrimaryKey
    var idCategory: Int? = null,
    var strCategory: String = "",
    var strCategoryThumb: String = ""
)
