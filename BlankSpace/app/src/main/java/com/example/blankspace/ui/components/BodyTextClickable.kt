package com.example.blankspace.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.blankspace.ui.theme.TEXT_COLOR

@Composable
fun BodyTextClickable(text: String, navController: NavController, destination: String) {
    Text(
        text = text,
        color = TEXT_COLOR,
        modifier = Modifier.clickable {
            navController.navigate(destination)
        },
        style = MaterialTheme.typography.bodyMedium
    )
}