package com.example.blankspace.screens.autorizacija.registracija

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blankspace.ui.theme.AccentPink
import com.example.blankspace.ui.theme.PrimaryDark
import kotlinx.coroutines.delay

@Composable
fun RegistrationButton(
    name: String, username: String, password: String, co_password: String, question: String, answer: String,
    onSignUp: (String, String,String, String, String, String) -> Unit
) {
    val context = LocalContext.current

    var pressed by remember { mutableStateOf(false) }
    val elevation = if (pressed) 2.dp else 8.dp

    Button(
        onClick = {
            if (name.isBlank() || username.isBlank() || password.isBlank() || co_password.isBlank() || question.isBlank() || answer.isBlank()) {
                Toast.makeText(context, "Niste uneli sve podatke!", Toast.LENGTH_SHORT).show()
            } else if (!name.contains(" ")) {
                Toast.makeText(context, "Ime i prezime sa razmakom!", Toast.LENGTH_SHORT).show()
            } else {
                pressed = true
                onSignUp(name,username,password,co_password, question, answer)
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
            text = "Registruj se",
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