package com.recipebookpro.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipebookpro.elements.FindRecipeScreen
import com.example.recipebookpro.elements.MyRecipeScreen
import com.example.recipebookpro.elements.SettingsScreen
import com.example.recipebookpro.nav.DrawerContent
import com.example.recipebookpro.nav.GroceryList
import com.example.recipebookpro.nav.HomeScreen
import com.example.recipebookpro.nav.ProfileScreen
import com.example.recipebookpro.spoonacular.RecipeViewModel
import com.example.recipebookpro.spoonacular.RecipeViewModelFactory
import com.example.recipebookpro.RecipeApp
import com.example.recipebookpro.elements.GroceryListScreen
import com.example.recipebookpro.ui.theme.RecipeBookProTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private lateinit var recipeViewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize DAO and ViewModel
        val dao = RecipeApp.database.recipeDao()
        val factory = RecipeViewModelFactory(dao)
        recipeViewModel = ViewModelProvider(this, factory)[RecipeViewModel::class.java]

        setContent {
            RecipeBookProTheme {
                // You can pass viewModel to MainLayout if needed
                MainLayout(recipeViewModel)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainLayout(viewmodel: RecipeViewModel) {
        // Declare ViewModels
        val recipeViewModel: RecipeViewModel = viewModel()
        // NavController and DrawerState
        val navController = rememberNavController()
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = true,
            drawerContent = {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFFDDAA)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(onClick = {
                                scope.launch { drawerState.close() }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close Drawer"
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        DrawerContent(
                            onDestinationClicked = { route ->
                                scope.launch {
                                    drawerState.close()
                                    navController.navigate(route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Pocket Recipe") },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch { drawerState.open() }
                            }) {
                                Icon(Icons.Default.Menu, contentDescription = null)
                            }
                        }
                    )
                }
            ) { padding ->
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.padding(padding)
                ) {
                    composable("home") { HomeScreen() }
                    composable("book") { FindRecipeScreen(viewModel = recipeViewModel) }
                    composable("settings") {
                        SettingsScreen()
                    }
                    composable("profile") { ProfileScreen() }
                    composable("recipes") { MyRecipeScreen(viewModel = recipeViewModel) } // Pass recipeViewModel
                    composable("grocery list") { GroceryListScreen(viewModel = recipeViewModel) }
                }
            }
        }
    }
}
