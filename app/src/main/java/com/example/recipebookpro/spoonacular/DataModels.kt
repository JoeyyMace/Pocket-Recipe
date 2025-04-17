package com.example.recipebookpro.spoonacular

import com.example.recipebookpro.data.ApiRecipeResponse

data class RecipeSearchResponse(
    val results: List<ApiRecipeResponse>, // ✅ Correct type
    val offset: Int,
    val number: Int,
    val totalResults: Int
)


