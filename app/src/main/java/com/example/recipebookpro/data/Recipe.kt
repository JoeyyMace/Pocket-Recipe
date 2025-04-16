package com.example.recipebookpro.data

data class Recipe(
    val name: String,
    val prepTime: String,
    val calories: String?,
    val ingredients: List<String>,
    val imageResId: String // Assuming we use URL as String for image URL
)