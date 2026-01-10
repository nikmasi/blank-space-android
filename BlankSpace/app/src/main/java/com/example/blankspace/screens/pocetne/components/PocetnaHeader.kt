package com.example.blankspace.screens.pocetne.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PocetnaHeaderText(
    subtitle: String = "Igra dopunjavanja tekstova pesama"
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "BlankSpace 🎵",
            color = Color.White,
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = subtitle,
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 16.sp
        )
    }
}