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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.blankspace.R
import com.example.blankspace.ui.theme.AccentPink
import com.example.blankspace.ui.theme.PrimaryDark

@Composable
fun RegistrationFields(name: String, username: String, password: String,
    coPassword: String, question: String, answer: String, onValueChange: (String, String) -> Unit
) {
    val fullName = stringResource(id = R.string.full_name)
    val userName = stringResource(id = R.string.username)
    val pass =stringResource(id = R.string.password)
    val reEnterPass =stringResource(id = R.string.re_enter_password)
    val secretQuestion =stringResource(id = R.string.secret_question)
    val secretAnswer =stringResource(id = R.string.secret_answer)

    val fields = remember(name, username, password, coPassword, question, answer) {
        listOf(
            Triple(fullName, name) { value: String -> onValueChange("name", value) },
            Triple(userName, username) { value: String -> onValueChange("username", value) },
            Triple(pass, password) { value: String -> onValueChange("password", value) },
            Triple(reEnterPass, coPassword) { value: String -> onValueChange("co_password", value) },
            Triple(secretQuestion, question) { value: String -> onValueChange("question", value) },
            Triple(secretAnswer, answer) { value: String -> onValueChange("answer", value) }
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