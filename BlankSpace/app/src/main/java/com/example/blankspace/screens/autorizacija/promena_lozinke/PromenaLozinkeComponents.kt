package com.example.blankspace.screens.autorizacija.promena_lozinke

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blankspace.ui.theme.AccentPink
import com.example.blankspace.ui.theme.PrimaryDark
import com.example.blankspace.viewModels.UiStateZL
import com.example.blankspace.viewModels.ZaboravljenaLozinkaViewModel
import kotlinx.coroutines.delay

@Composable
fun PasswordChangeHeader() {
    Text(
        text = "Promena lozinke",
        color = PrimaryDark,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(
        text = "Unesite Vašu novu lozinku.",
        style = MaterialTheme.typography.bodyMedium,
        color = PrimaryDark.copy(alpha = 0.8f)
    )
}

@Composable
fun PasswordFieldStyled(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = PrimaryDark) },
        visualTransformation = PasswordVisualTransformation(),
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
fun ChangePasswordButtonStyled(
    novaLozinka: String,
    potvrdaLozinke: String,
    uiState: UiStateZL,
    viewModel: ZaboravljenaLozinkaViewModel,
    context: Context
) {
    var pressed by remember { mutableStateOf(false) }
    val elevation = if (pressed) 2.dp else 8.dp

    Button(
        onClick = {
            if (novaLozinka.isBlank() || potvrdaLozinke.isBlank()) {
                Toast.makeText(context, "Niste uneli sve podatke!", Toast.LENGTH_SHORT).show()
            } else {
                pressed = true
                uiState.zaboravljenaLozinka?.korisnicko_ime?.let {
                    viewModel.fetchNovaLozinka(it, novaLozinka, potvrdaLozinke)
                }
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
            text = "Promenite lozinku",
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