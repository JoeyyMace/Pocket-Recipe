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
        @Query("cuisine") cuisine: String? = null,
        @Query("diet") diet: String? = null,
        @Query("intolerances") intolerances: String? = null,
        @Query("type") type: String? = null,
        @Query("maxReadyTime") maxReadyTime: Int? = null,
        @Query("sort") sort: String = "popularity" // Example for sorting by popularity
    ): RecipeSearchResponse

    @GET("recipes/{id}/information")
    suspend fun getRecipeInformation(
        @Path("id") id: Int,
        @Query("includeNutrition") includeNutrition: Boolean = true,
        @Query("apiKey") apiKey: String
    ): ApiRecipeResponse
}

