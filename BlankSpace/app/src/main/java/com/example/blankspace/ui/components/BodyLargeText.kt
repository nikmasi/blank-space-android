package com.example.blankspace.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.blankspace.ui.theme.TEXT_COLOR

@Composable
fun BodyLargeText(text:String){
    Text(
        text = "Dodaj zvuk",
        style = MaterialTheme.typography.bodyLarge,
        color = TEXT_COLOR
    )
}