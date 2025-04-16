package com.example.recipebookpro.spoonacular

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.BuildConfig
import com.example.recipebookpro.data.NetworkRecipe
import com.example.recipebookpro.data.Recipe
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    private val _recipes = MutableLiveData<List<Recipe>>(emptyList())
    val recipes: LiveData<List<Recipe>> get() = _recipes

    fun searchRecipes(query: String) {
        val apiKey = BuildConfig.API_KEY
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.searchRecipes(query, apiKey)

                // Map NetworkRecipe to Recipe
                val recipeList = response.results.map { networkRecipe ->
                    mapNetworkRecipeToRecipe(networkRecipe)
                }

                // Update LiveData with mapped recipes
                _recipes.postValue(recipeList)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Failed to fetch recipes", e)

                Log.d("API_KEY_CHECK", "API Key: ${BuildConfig.API_KEY}")
            }
        }
    }

    // Mapping function
    private fun mapNetworkRecipeToRecipe(networkRecipe: NetworkRecipe): Recipe {
        val imageUrl = networkRecipe.imageUrl?.let {
            if (it.startsWith("http")) {
                it
            } else {
                "https://spoonacular.com/recipeImages/${networkRecipe.id}-312x231.jpg"
            }
        } ?: "https://via.placeholder.com/312x231"

        return Recipe(
            name = networkRecipe.title,
            prepTime = "${networkRecipe.readyInMinutes} min",
            calories = "N/A", // You can update this if you want to get calories info
            ingredients = emptyList(), // You can update this to fetch actual ingredients
            imageResId = imageUrl
        )
    }
}