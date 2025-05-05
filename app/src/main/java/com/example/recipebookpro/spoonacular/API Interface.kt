package com.example.recipebookpro.spoonacular

import com.example.recipebookpro.data.ApiRecipeResponse
import retrofit2.http.GET
import retrofit2.http.Path

import retrofit2.http.Query

interface SpoonacularApi {

    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String,
        @Query("addRecipeNutrition") addNutrition: Boolean = true,
    ): RecipeSearchResponse

    @GET("recipes/{id}/information")
    suspend fun getRecipeInformation(
        @Path("id") id: Int,
        @Query("includeNutrition") includeNutrition: Boolean = true,
        @Query("apiKey") apiKey: String
    ): ApiRecipeResponse
}

