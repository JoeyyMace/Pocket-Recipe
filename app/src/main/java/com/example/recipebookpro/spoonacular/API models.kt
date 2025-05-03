package com.example.recipebookpro.spoonacular

import android.util.Log
import com.example.recipebookpro.data.ApiRecipeResponse
import com.example.recipebookpro.data.Recipe

fun mapApiRecipeToRecipe(api: ApiRecipeResponse): Recipe {
    // Safely extract calories (from nutrients)
    val calories = api.nutrition?.nutrients
        ?.find { it.name.equals("Calories", ignoreCase = true) }
        ?.amount?.toInt() // Convert from Double to Int

    // Safely map extended ingredients or use fallback
    val ingredients = api.extendedIngredients?.map { it.original } ?: listOf("No ingredients available")

    // Logging for debugging
    Log.d("API Ingredients", "Ingredients: $ingredients")
    Log.d("API Response", "Full API Response: $api")

    // Build your internal Recipe model
    return Recipe(
        id = api.id,
        title = api.title,
        readyInMinutes = api.readyInMinutes,
        calories = calories,
        ingredients = ingredients,
        image = api.image,
        instructions = api.instructions ?: "No instructions provided"
    )
}






