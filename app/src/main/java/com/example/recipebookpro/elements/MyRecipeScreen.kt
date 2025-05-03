package com.example.recipebookpro.elements

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipebookpro.spoonacular.RecipeViewModel
import kotlinx.coroutines.launch


@Composable
fun MyRecipeScreen(viewModel: RecipeViewModel) {
    val myRecipes by viewModel.myRecipes.observeAsState(emptyList())
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    Log.d("MyRecipeScreen", "myRecipes: $myRecipes") // Log for debugging

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (myRecipes.isEmpty()) {
                    Text(
                        text = "No Recipes Found",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    LazyColumn {
                        items(myRecipes) { recipe ->
                            RecipeItem(
                                recipe = recipe,
                                onAddClick = { viewModel.removeRecipe(recipe) },
                                buttonText = "Remove"  // Change button text to "Remove" for MyRecipeScreen
                            )
                            HorizontalDivider()
                        }
                    }
                }

                Button(
                    onClick = {
                        coroutineScope.launch {
                            if (myRecipes.isEmpty()) {
                                snackbarHostState.showSnackbar("No recipes found!")
                            } else {
                                // Handle grocery list generation here
                            }
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) {
                    Text("Make Grocery List")
                }
            }
        }
    )
}














