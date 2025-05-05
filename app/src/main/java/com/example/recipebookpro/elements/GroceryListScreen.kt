package com.example.recipebookpro.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.recipebookpro.spoonacular.RecipeViewModel
import com.example.recipebookpro.ui.theme.lightOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroceryListScreen(viewModel: RecipeViewModel) {
    val groceryListIngredients by viewModel.groceryListIngredients.observeAsState(emptySet())
    val selectedIngredients = remember { mutableStateListOf<String>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Grocery List") },
                actions = {
                    // Delete selected items button
                    if (selectedIngredients.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                // Remove selected ingredients
                                viewModel.removeIngredientsFromGroceryList(selectedIngredients)
                                // Clear selection
                                selectedIngredients.clear()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete selected items"
                            )
                        }
                    }

                    // Clear all button
                    IconButton(
                        onClick = {
                            viewModel.clearGroceryList()
                            selectedIngredients.clear()
                        },
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
                        // Selection summary if items are selected
                        if (selectedIngredients.isNotEmpty()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(color = lightOrange)
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${selectedIngredients.size} item${if (selectedIngredients.size > 1) "s" else ""} selected",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Black
                                )

                                TextButton(
                                    onClick = { selectedIngredients.clear() },
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = Color.Black
                                    )
                                ) {
                                    Text("Clear selection")
                                }
                            }
                        }

                        // List of grocery items with checkboxes
                        LazyColumn(
                            modifier = Modifier.weight(1f)
                        ) {
                            itemsIndexed(groceryListIngredients.toList()) { _, ingredient ->
                                val isSelected = selectedIngredients.contains(ingredient)

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            if (isSelected) {
                                                selectedIngredients.remove(ingredient)
                                            } else {
                                                selectedIngredients.add(ingredient)
                                            }
                                        }
                                        .padding(vertical = 8.dp, horizontal = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = isSelected,
                                        onCheckedChange = {
                                            if (it) {
                                                selectedIngredients.add(ingredient)
                                            } else {
                                                selectedIngredients.remove(ingredient)
                                            }
                                        },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = Color(0xFFFFDDAA),          // Green when checked
                                            uncheckedColor = Color.Gray,              // Gray when unchecked
                                            checkmarkColor = Color.Black              // Color of the checkmark
                                        )
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = ingredient,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.weight(1f)
                                    )
                                }

                                HorizontalDivider(modifier = Modifier.padding(start = 56.dp, end = 16.dp))
                            }
                        }

                        // Bottom action bar for selected items
                        if (selectedIngredients.isNotEmpty()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.surface)
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Button(
                                    onClick = {
                                        viewModel.removeIngredientsFromGroceryList(selectedIngredients)
                                        selectedIngredients.clear()
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFFFDDAA),
                                        contentColor = Color.Black
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Text("Delete Selected")
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

