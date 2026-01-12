package com.example.blankspace.screens.autorizacija.auth_components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blankspace.ui.theme.PrimaryDark

@Composable
fun AuthHeader(text:String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge,
        color = PrimaryDark,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp,
        modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
    )
}