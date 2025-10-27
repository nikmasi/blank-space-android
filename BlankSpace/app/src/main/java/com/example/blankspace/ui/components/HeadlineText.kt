package com.example.blankspace.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.blankspace.ui.theme.TEXT_COLOR
import com.example.blankspace.ui.theme.TEXT_COLOR_WHITE

@Composable
fun HeadlineText(text:String){
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        color = TEXT_COLOR
    )
}

@Composable
fun HeadlineTextWhite(text:String){
    Text(
        text = text,
        color = TEXT_COLOR_WHITE,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}