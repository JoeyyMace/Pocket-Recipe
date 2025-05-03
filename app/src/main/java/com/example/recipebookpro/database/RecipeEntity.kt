package com.example.recipebookpro.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val readyInMinutes: Int,
    val calories: Int?,
    val ingredients: List<String>?,  // List<String> that gets converted
    val image: String?,
    val instructions: String?
)
