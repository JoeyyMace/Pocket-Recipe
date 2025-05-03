package com.example.recipebookpro.elements

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipebookpro.spoonacular.RecipeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroceryListScreen(viewModel: RecipeViewModel) {
    val groceryListIngredients by viewModel.groceryListIngredients.observeAsState(emptySet())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Grocery List") },
                actions = {
                    // Clear button in the top app bar
                    IconButton(
                        onClick = { viewModel.clearGroceryList() },
                        enabled = groceryListIngredients.isNotEmpty()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Clear grocery list"
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (groceryListIngredients.isEmpty()) {
                    Text(
                        text = "Grocery List is Empty",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    Column(modifier = Modifier.fillMaxSize()) {
                        // List of grocery items
                        LazyColumn(
                            modifier = Modifier.weight(1f)
                        ) {
                            itemsIndexed(groceryListIngredients.toList()) { _, ingredient ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp, horizontal = 16.dp)
                                ) {
                                    Text(
                                        text = "•",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Text(
                                        text = ingredient,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

// Add this function to your RecipeViewModel class:
// fun clearGroceryList() {
//     _groceryListIngredients.value = emptySet()
// }

/*
val snackbarHostState = remember { SnackbarHostState() }
val coroutineScope = rememberCoroutineScope()
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
*/

