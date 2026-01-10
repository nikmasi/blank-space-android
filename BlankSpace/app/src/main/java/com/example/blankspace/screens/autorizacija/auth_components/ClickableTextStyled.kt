package com.example.blankspace.screens.autorizacija.auth_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.blankspace.ui.theme.PrimaryDark

@Composable
fun ClickableTextStyled(
    text: String,
    onClick: () -> Unit
) {
    Text(
        text = text,
        color = PrimaryDark,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        modifier = Modifier.clickable(indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) { onClick() }
    )
}