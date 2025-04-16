package com.example.recipebookpro.spoonacular

import com.example.recipebookpro.data.NetworkRecipe

data class RecipeSearchResponse(
    val results: List<NetworkRecipe>,
    val offset: Int,
    val number: Int,
    val totalResults: Int
)
