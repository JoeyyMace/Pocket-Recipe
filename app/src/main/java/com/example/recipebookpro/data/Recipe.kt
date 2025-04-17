package com.example.recipebookpro.data

// Network model
data class ApiRecipeResponse(
    val id: Int,
    val title: String,
    val readyInMinutes: Int,
    val image: String?,
    val nutrition: NutritionInfo? // <- optional, in case some results don’t include it
)

// Your app's Recipe model
data class Recipe(
    val id: Int,
    val title: String,
    val readyInMinutes: Int,
    val calories: Int?,
    val ingredients: List<String>?,
    val image: String?
)

data class NutritionInfo(
    val nutrients: List<Nutrient>
)

data class Nutrient(
    val name: String,
    val amount: Double,
    val unit: String
)