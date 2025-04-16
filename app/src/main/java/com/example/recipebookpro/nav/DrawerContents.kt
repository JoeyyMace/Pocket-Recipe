package com.example.recipebookpro.nav

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DrawerContent(onDestinationClicked: (route: String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 112.dp) // Offset to match TopAppBar height
    ) {
        Text(
            text = "Home",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onDestinationClicked("home") }
                .padding(16.dp)
        )
        Text(
            text = "My Recipes",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onDestinationClicked("recipes") }
                .padding(16.dp)
        )
        Text(
            text = "Grocery List",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onDestinationClicked("grocery list") }
                .padding(16.dp)
        )
        Text(
            text = "Find Recipes",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onDestinationClicked("book") }
                .padding(16.dp)
        )
        Text(
            text = "Settings",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onDestinationClicked("settings") }
                .padding(16.dp)
        )
        Text(
            text = "Profile",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onDestinationClicked("profile") }
                .padding(16.dp)
        )
    }
}