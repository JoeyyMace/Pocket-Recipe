package com.example.recipebookpro.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Network model
data class ApiRecipeResponse(
    val id: Int,
    val title: String,
    val readyInMinutes: Int,
    val image: String?,
    val nutrition: NutritionInfo?,
    val extendedIngredients: List<ExtendedIngredient>,
    val instructions: String?
)

data class ExtendedIngredient(
    val original: String
)

// Your app's Recipe model
data class Recipe(
    val id: Int = 0,
    val title: String,
    val readyInMinutes: Int,
    val calories: Int?,
    val ingredients: List<String>?,
    val image: String?,
    val instructions: String? = null // <-- Add this
)


data class NutritionInfo(
    val nutrients: List<Nutrient>
)

data class Nutrient(
    val name: String,
    val amount: Double,
    val unit: String
)

