package com.example.blankspace.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SmallButton(onClick: () -> Unit, text:String, style: TextStyle) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFF69B4),
            contentColor = Color.White
        )
    ) {
        Text(
            text = text,
            style = style,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}