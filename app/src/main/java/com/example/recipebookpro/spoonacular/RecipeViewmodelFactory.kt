package com.example.recipebookpro.spoonacular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipebookpro.database.RecipeDao

class RecipeViewModelFactory(private val recipeDao: RecipeDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            RecipeViewModel(recipeDao) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
