package com.example.blankspace.screens.autorizacija.auth_components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.blankspace.ui.theme.PrimaryDark

@Composable
fun AuthNavigation(title: String, textClick: String, onSignUpClick: () -> Unit){
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = PrimaryDark.copy(alpha = 0.8f)
        )

        ClickableTextStyled(
            text = textClick,
            onClick = onSignUpClick
        )
    }
}