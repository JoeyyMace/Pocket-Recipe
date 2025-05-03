package com.example.recipebookpro.database

import androidx.room.TypeConverter
import com.example.recipebookpro.data.Recipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun Recipe.toEntity(): RecipeEntity {
    return RecipeEntity(
        id = id,
        title = title,
        readyInMinutes = readyInMinutes,
        calories = calories,
        ingredients = ingredients,
        image = image,
        instructions = instructions
    )
}

fun RecipeEntity.toRecipe(): Recipe {
    return Recipe(
        id = id,
        title = title,
        readyInMinutes = readyInMinutes,
        calories = calories,
        ingredients = ingredients,
        image = image,
        instructions = instructions
    )
}

class Converters {

    // Convert List<String> to a String for Room to store
    @TypeConverter
    fun fromIngredientsList(value: List<String>?): String? {
        return Gson().toJson(value)
    }

    // Convert String back to List<String>
    @TypeConverter
    fun toIngredientsList(value: String?): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }
}

