package com.example.recipebookpro.spoonacular

import retrofit2.http.GET

import retrofit2.http.Query

interface SpoonacularApi {

    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String,
        @Query("addRecipeNutrition") addNutrition: Boolean = true
    ): RecipeSearchResponse
}

