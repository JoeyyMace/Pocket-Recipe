package com.example.recipebookpro.spoonacular

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.BuildConfig
import com.example.recipebookpro.data.Recipe
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    private val _recipes = MutableLiveData<List<Recipe>>(emptyList())  // Use Recipe model here
    val recipes: LiveData<List<Recipe>> get() = _recipes

    fun searchRecipes(query: String) {
        val apiKey = BuildConfig.API_KEY
        viewModelScope.launch {
            try {
                // Fetch the response from the API
                val response = RetrofitInstance.api.searchRecipes(query, apiKey)

                // Map the API response (which is List<ApiRecipeResponse>) to your Recipe model
                // response.results is a List<ApiRecipeResponse> that you want to map to List<Recipe>
                val recipeList = response.results.map { apiRecipe ->
                    mapApiRecipeToRecipe(apiRecipe)
                }

                // Update LiveData with the mapped list of Recipes
                _recipes.postValue(recipeList)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Failed to fetch recipes", e)
                Log.d("API_KEY_CHECK", "API Key: ${BuildConfig.API_KEY}")
            }
        }
    }
}



