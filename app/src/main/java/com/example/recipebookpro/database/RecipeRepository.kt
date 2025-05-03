package com.example.recipebookpro.database

import com.example.recipebookpro.data.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecipeRepository(private val recipeDao: RecipeDao) {

    val allRecipes: Flow<List<Recipe>> = recipeDao.getAllRecipes()
        .map { entities ->
            entities.map { it.toRecipe() }
        }

    suspend fun insert(recipe: Recipe) {
        recipeDao.insert(recipe.toEntity())
    }

    suspend fun delete(recipe: Recipe) {
        recipeDao.delete(recipe.toEntity())
    }
}

