package com.example.recipebookpro.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.recipebookpro.spoonacular.RecipeViewModel
import kotlinx.coroutines.launch


@Composable
fun MyRecipeScreen(viewModel: RecipeViewModel) {
    val myRecipes by viewModel.myRecipes.observeAsState(emptyList())
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val expandedRecipes by viewModel.expandedRecipes.observeAsState(emptyMap())

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
                        items(myRecipes, key = { it.id }) { recipe ->
                            val fullRecipe = expandedRecipes[recipe.id] ?: recipe

                            Column {
                                RecipeItem(
                                    recipe = recipe,
                                    isAddButton = false,
                                    onButtonClick = {
                                        viewModel.removeRecipe(fullRecipe)
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("Recipe Removed")
                                        }
                                    },
                                    buttonText = "Remove"
                                )
                                Button(
                                    onClick = {
                                        // Add ingredients of the recipe to the grocery list
                                        viewModel.addRecipeToGroceryList(recipe)
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("Added ingredients to Grocery List")
                                        }
                                    },
                                    modifier = Modifier
                                        .align(Alignment.End)
                                        .padding(end = 16.dp, top = 4.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFFFDDAA),
                                        contentColor = Color.Black
                                    )
                                ) {
                                    Text("Add to Grocery List")
                                }
                                HorizontalDivider()
                            }
                        }
                    }
                }
            }
        }
    )
}















