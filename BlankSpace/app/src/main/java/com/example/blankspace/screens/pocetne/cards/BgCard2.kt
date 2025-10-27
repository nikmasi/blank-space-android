package com.example.blankspace.screens.pocetne.cards

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BgCard2() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF0DAE7), // tvoja roze boja
                        Color(0xFFFFB6C1), // svetloroze
                        Color(0xFFF0DAE7)  // vraćamo baznu boju
                    )
                )
            )
    ) {
        // diskretan svetlosni krug za “glow” efekat
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color.White.copy(alpha = 0.12f),
                radius = size.minDimension / 2f,
                center = Offset(size.width / 2, size.height / 3)
            )
        }
    }
}

