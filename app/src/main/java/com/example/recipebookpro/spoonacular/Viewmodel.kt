package com.example.recipebookpro.spoonacular

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.BuildConfig
import com.example.recipebookpro.data.Recipe
import com.example.recipebookpro.database.RecipeDao
import com.example.recipebookpro.database.toEntity
import com.example.recipebookpro.database.toRecipe
import kotlinx.coroutines.launch

class RecipeViewModel(private val recipeDao: RecipeDao) : ViewModel() {

    init {
        Log.d("RecipeViewModel", "RecipeViewModel Created")
        loadRecipesFromDatabase()
    }

    private val _recipes = MutableLiveData<List<Recipe>>(emptyList())
    val recipes: LiveData<List<Recipe>> get() = _recipes

    private val _myRecipes = MutableLiveData<List<Recipe>>(emptyList())
    val myRecipes: LiveData<List<Recipe>> get() = _myRecipes

    private val _expandedRecipes = MutableLiveData<Map<Int, Recipe>>(emptyMap())
    val expandedRecipes: LiveData<Map<Int, Recipe>> get() = _expandedRecipes

    private val _groceryListIngredients = MutableLiveData<Set<String>>(setOf()) // Using Set to avoid duplicates
    val groceryListIngredients: LiveData<Set<String>> = _groceryListIngredients

    val isLoading = MutableLiveData(false)

    fun addRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeDao.insert(recipe.toEntity())
            val updatedList = _myRecipes.value.orEmpty() + recipe
            _myRecipes.postValue(updatedList)
            Log.d("RecipeViewModel", "Updated myRecipes: $updatedList") // Log the updated list
        }
    }

    fun removeRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeDao.delete(recipe.toEntity())
            // Remove from the local list as well
            val updatedList = _myRecipes.value.orEmpty().filterNot { it.id == recipe.id }
            _myRecipes.postValue(updatedList) // Update the list with the removed recipe
            Log.d("RecipeViewModel", "Updated myRecipes after remove: $updatedList")
        }
    }

    fun addRecipeToGroceryList(recipe: Recipe) {
        // Get current list of ingredients (avoid duplicates)
        val currentIngredients = _groceryListIngredients.value ?: emptySet()

        // Handle the nullable List<String> safely
        val newIngredients = recipe.ingredients?.filter { it.isNotEmpty() }?.map { it.trim() } ?: emptyList()

        // Update the ingredients list
        _groceryListIngredients.value = currentIngredients + newIngredients
    }

    fun removeIngredientsFromGroceryList(ingredientsToRemove: List<String>) {
        // Get current list of ingredients
        val currentIngredients = _groceryListIngredients.value ?: emptySet()

        // Create a new set without the ingredients to remove
        val updatedIngredients = currentIngredients.filterNot { ingredientsToRemove.contains(it) }.toSet()

        // Update the ingredients list
        _groceryListIngredients.value = updatedIngredients

        Log.d("RecipeViewModel", "Removed ${ingredientsToRemove.size} ingredients from grocery list")
    }

    fun clearGroceryList() {
        _groceryListIngredients.value = emptySet()
    }

    private fun loadRecipesFromDatabase() {
        viewModelScope.launch {
            recipeDao.getAllRecipes()
                .collect { recipeEntities ->
                    val recipeList = recipeEntities.map { it.toRecipe() }
                    _myRecipes.postValue(recipeList)
                }
        }
    }

    fun searchRecipes(query: String) {
        val apiKey = BuildConfig.API_KEY
        isLoading.value = true // Set loading to true when the search starts
        viewModelScope.launch {
            try {
                val searchResponse = RetrofitInstance.api.searchRecipes(query, apiKey)
                val recipeList = searchResponse.results.map { summary ->
                    Recipe(
                        id = summary.id,
                        title = summary.title,
                        readyInMinutes = summary.readyInMinutes,
                        calories = summary.nutrition?.nutrients
                            ?.find { it.name.equals("Calories", ignoreCase = true) }
                            ?.amount?.toInt(),
                        ingredients = listOf(),
                        image = summary.image
                    )
                }
                _recipes.postValue(recipeList)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Failed to fetch recipes", e)
            } finally {
                isLoading.value = false // Set loading to false once the API call is finished (either success or failure)
            }
        }
    }


    fun fetchFullRecipeDetails(recipeId: Int, onRecipeFetched: (Recipe) -> Unit) {
        val apiKey = BuildConfig.API_KEY
        viewModelScope.launch {
            try {
                val fullRecipeResponse = RetrofitInstance.api.getRecipeInformation(recipeId, apiKey = apiKey)
                val fullRecipe = mapApiRecipeToRecipe(fullRecipeResponse)

                val updatedRecipes = _expandedRecipes.value.orEmpty().toMutableMap()
                updatedRecipes[recipeId] = fullRecipe
                _expandedRecipes.postValue(updatedRecipes)

                onRecipeFetched(fullRecipe)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Failed to fetch full recipe details", e)
            }
        }
    }
}


