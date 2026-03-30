package com.example.blankspace.screens.autorizacija.zaboravljena_lozinka

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blankspace.ui.theme.AccentPink
import com.example.blankspace.ui.theme.PrimaryDark
import kotlinx.coroutines.delay


@Composable
fun ForgotPasswordHeader() {
    Text(
        text = "Zaboravljena lozinka",
        color = PrimaryDark,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
fun ForgotPasswordField(username: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = username,
        onValueChange = onValueChange,
        label = { Text("Korisničko ime", color = PrimaryDark) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AccentPink,
            unfocusedBorderColor = PrimaryDark.copy(alpha = 0.5f),
            cursorColor = AccentPink,
            focusedTextColor = PrimaryDark,
            unfocusedTextColor = PrimaryDark
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ResetPasswordButton(username: String, context: Context, onResetClick: (String) -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val elevation = if (pressed) 2.dp else 8.dp

    Button(
        onClick = {
            if (username.isBlank()) {
                Toast.makeText(context, "Niste uneli korisničko ime!", Toast.LENGTH_SHORT).show()
            } else {
                pressed = true
                onResetClick(username)
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = AccentPink,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(elevation, RoundedCornerShape(16.dp))
    ) {
        Text(
            text = "Postavi pitanje",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
    LaunchedEffect(pressed) {
        if (pressed) {
            delay(100)
            pressed = false
        }
    }
}