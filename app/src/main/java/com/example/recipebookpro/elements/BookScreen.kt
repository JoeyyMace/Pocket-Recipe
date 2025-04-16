package com.example.recipebookpro.elements

/*
@OptIn(ExperimentalPagerApi::class)
@Composable
fun FindRecipeScreen(recipes: List<Recipe>, onSearch: (String) -> Unit) {
    val pagerState = rememberPagerState()

    if (recipes.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "No recipes available.",
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }

    } else {
        HorizontalPager(
            count = recipes.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val recipe = recipes[page]

            Column(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = rememberAsyncImagePainter(recipe.imageResId),
                    contentDescription = "Recipe Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .border(1.dp, Color.Black)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(modifier = Modifier.padding(16.dp)) {
                    // Name Text, Prep Time, Calories, Ingredients as before
                }
            }
        }
    }
} */

// Coil dependency if not added yet
// implementation("io.coil-kt:coil-compose:2.4.0")

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.recipebookpro.data.Recipe
import com.example.recipebookpro.spoonacular.RecipeViewModel

@Composable
fun FindRecipeScreen() {
    // Get the ViewModel using the viewModel() function
    val viewModel: RecipeViewModel = viewModel()

    // Observe the recipes from the ViewModel
    val recipes by viewModel.recipes.observeAsState(emptyList())
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        // Search TextField
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search Recipes") },
            modifier = Modifier.fillMaxWidth()
        )

        // Search Button
        Button(onClick = {
            // Call the ViewModel's searchRecipes method
            viewModel.searchRecipes(searchQuery)
        }) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display recipes
        LazyColumn {
            items(recipes) { recipe ->
                RecipeItem(recipe)
                HorizontalDivider()
            }
        }
    }
}



@Composable
fun RecipeItem(recipe: Recipe) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AsyncImage(
            model = if (recipe.imageResId.isNotBlank()) recipe.imageResId else "https://via.placeholder.com/312x231", // Fallback
            contentDescription = recipe.name,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = recipe.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "Prep Time: ${recipe.prepTime}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Calories: ${recipe.calories}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
