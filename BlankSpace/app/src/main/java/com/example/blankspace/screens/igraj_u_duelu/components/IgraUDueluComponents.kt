package com.example.blankspace.screens.igraj_u_duelu.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blankspace.ui.theme.AccentPink
import com.example.blankspace.ui.theme.PrimaryDark

@Composable
fun HeadlineTextDuel(text: String, fontSize: TextUnit, fontWeight: FontWeight, flag: Boolean = false) {
    if (!flag){
        Text(text, fontSize = fontSize, fontWeight = fontWeight, color = PrimaryDark)
    }
    else{
        Text(text, fontSize = fontSize, fontWeight = fontWeight, color = PrimaryDark, textAlign = TextAlign.Center)
    }
}

@Composable
fun BodyTextDuel(text: String, flag: Boolean = false) {
    if (!flag) {
        Text(text, fontSize = 16.sp, color = PrimaryDark.copy(alpha = 0.8f))
    }
    else{
        Text(text, fontSize = 16.sp, color = PrimaryDark.copy(alpha = 0.8f), textAlign = TextAlign.Center)
    }
}

@Composable
fun ActionButtonDuel(onClick: () -> Unit, text: String, modifier: Modifier, containerColor: Color = AccentPink) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.height(48.dp)
    ) {
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextFieldInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(0.8f).padding(bottom = 16.dp),
        //keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AccentPink,
            unfocusedBorderColor = PrimaryDark.copy(alpha = 0.5f),
            cursorColor = AccentPink
        ),
        singleLine = true,
        shape = RoundedCornerShape(12.dp)
    )
}