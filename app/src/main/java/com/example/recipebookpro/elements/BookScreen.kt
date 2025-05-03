package com.example.recipebookpro.elements

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.recipebookpro.data.Recipe
import com.example.recipebookpro.spoonacular.InstructionText
import com.example.recipebookpro.spoonacular.RecipeViewModel
import kotlinx.coroutines.launch

@Composable
fun FindRecipeScreen(viewModel: RecipeViewModel) {
    val recipes by viewModel.recipes.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false) // Add a loading state
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val expandedRecipes by viewModel.expandedRecipes.observeAsState(emptyMap())
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) } // ✅ Attach snackbar host
    ) { paddingValues ->

        Column(modifier = Modifier
            .padding(16.dp)
            .padding(paddingValues)) {

            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search Recipes") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(onClick = {
                viewModel.searchRecipes(searchQuery)
            }) {
                Text("Search")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                LazyColumn {
                    items(recipes) { recipe ->
                        val fullRecipe = expandedRecipes[recipe.id] ?: recipe

                        RecipeItem(
                            recipe = fullRecipe,
                            onAddClick = {
                                viewModel.addRecipe(fullRecipe)
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Recipe Added")
                                }
                            },
                            buttonText = "Add",
                            onRemoveClick = {},
                            onExpand = {
                                if (expandedRecipes[recipe.id] == null) {
                                    viewModel.fetchFullRecipeDetails(recipe.id) {}
                                }
                            }
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeItem(
    recipe: Recipe,
    onAddClick: ((Recipe) -> Unit)? = null,
    onRemoveClick: ((Recipe) -> Unit)? = null,
    buttonText: String = "Add",
    onExpand: () -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }

    val imageUrl = recipe.image?.takeIf { it.isNotBlank() }
        ?: "https://dummyimage.com/312x231/cccccc/000000&text=No+Image"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isExpanded = !isExpanded
                if (isExpanded) onExpand() // Trigger full fetch on expand
            }
            .padding(8.dp)
            .animateContentSize()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = imageUrl,
                contentDescription = recipe.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.error_image)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(text = recipe.title, style = MaterialTheme.typography.titleMedium)
                Text(text = "Prep Time: ${recipe.readyInMinutes} minutes", style = MaterialTheme.typography.bodySmall)
                Text(text = "Calories: ${recipe.calories}", style = MaterialTheme.typography.bodySmall)
            }
        }

        if (isExpanded) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Ingredients:", style = MaterialTheme.typography.titleSmall)
            recipe.ingredients?.forEach { ingredient ->
                Text(text = "- $ingredient", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Instructions:", style = MaterialTheme.typography.titleSmall)
            InstructionText(recipe.instructions ?: "No instructions provided.")
            Button(
                onClick = {
                    onAddClick?.invoke(recipe)
                    onRemoveClick?.invoke(recipe)
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(buttonText)
            }
        }
    }
}







