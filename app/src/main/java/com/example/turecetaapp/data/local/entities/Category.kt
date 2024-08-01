package com.example.turecetaapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Categorys")
class Category {
    @PrimaryKey
    val idCategory: Int? = null
    val strCategory: String = ""
    val strCategoryThumb: String = ""
}

