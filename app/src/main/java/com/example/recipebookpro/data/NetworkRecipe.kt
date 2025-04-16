package com.example.recipebookpro.data

data class NetworkRecipe(
    val id: Int,
    val title: String,
    val readyInMinutes: Int,
    val calories: String,
    val ingredients: List<String>,
    val imageUrl: String
)