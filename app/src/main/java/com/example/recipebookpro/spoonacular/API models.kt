package com.example.recipebookpro.spoonacular

import com.example.recipebookpro.data.ApiRecipeResponse
import com.example.recipebookpro.data.Recipe
import com.example.recipebookpro.spoonacular.RetrofitInstance.api

fun mapApiRecipeToRecipe(api: ApiRecipeResponse): Recipe {
    val calories = api.nutrition?.nutrients
        ?.find { it.name.equals("Calories", ignoreCase = true) }
        ?.amount?.toInt() // Convert from Double to Int (e.g. 462.0 -> 462)

    return Recipe(
        id = api.id,
        title = api.title,
        readyInMinutes = api.readyInMinutes,
        calories = calories,
        ingredients = null, // Still not available in complexSearch
        image = api.image
    )
}




