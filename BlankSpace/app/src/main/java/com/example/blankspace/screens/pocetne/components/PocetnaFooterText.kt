package com.example.blankspace.screens.pocetne.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PocetnaFooterText(){
    Text(
        text = "🎧 Zabavi se i testiraj svoje muzičko znanje!",
        color = Color(0xFF49006B).copy(alpha = 0.8f),
        fontSize = 14.sp,
        modifier = Modifier.padding(top=20.dp)
    )
}