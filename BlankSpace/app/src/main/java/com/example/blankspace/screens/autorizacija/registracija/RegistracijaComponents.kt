package com.example.blankspace.screens.autorizacija.registracija

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.blankspace.ui.theme.AccentPink
import com.example.blankspace.ui.theme.PrimaryDark

@Composable
fun RegistrationFields(name: String, username: String, password: String,
    co_password: String, question: String, answer: String, onValueChange: (String, String) -> Unit
) {
    val fields = remember(name, username, password, co_password, question, answer) {
        listOf(
            Triple("Ime i prezime", name) { value: String -> onValueChange("name", value) },
            Triple("Korisničko ime", username) { value: String -> onValueChange("username", value) },
            Triple("Lozinka", password) { value: String -> onValueChange("password", value) },
            Triple("Ponovo unesite lozinku", co_password) { value: String -> onValueChange("co_password", value) },
            Triple("Pitanje za oporavak lozinke", question) { value: String -> onValueChange("question", value) },
            Triple("Odgovor na pitanje", answer) { value: String -> onValueChange("answer", value) }
        )
    }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        fields.forEachIndexed { index, field ->
            val isPassword = index == 2 || index == 3

            OutlinedTextField(
                value = field.second,
                onValueChange = field.third,
                label = { Text(field.first, color = PrimaryDark) },
                visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
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
            Spacer(modifier = Modifier.height(3.dp))
        }
    }
}