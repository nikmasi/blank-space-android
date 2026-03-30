package com.example.blankspace.screens.autorizacija.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blankspace.R
import com.example.blankspace.screens.autorizacija.auth_components.AuthNavigation
import com.example.blankspace.screens.autorizacija.auth_components.ClickableTextStyled
import com.example.blankspace.ui.theme.AccentPink
import com.example.blankspace.ui.theme.PrimaryDark
import com.example.blankspace.viewModels.LoginViewModel
import kotlinx.coroutines.delay


@Composable
fun LoginImage() {
    Image(
        painter = painterResource(id = R.mipmap.ic_launcher_foreground),
        contentDescription = "Logo",
        modifier = Modifier.fillMaxWidth(0.6f).padding(vertical = 8.dp),
        contentScale = ContentScale.Fit
    )
}

@Composable
fun LoginFields(username: String, password: String, onUsernameChange: (String) -> Unit, onPasswordChange: (String) -> Unit) {
    OutlinedTextField(
        value = username,
        onValueChange = onUsernameChange,
        label = { Text(stringResource(id = R.string.textField_username), color = PrimaryDark) },
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
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(stringResource(id = R.string.textField_password), color = PrimaryDark) },
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
fun LoginButton(username: String, password: String, onLogin: (String, String) -> Unit, context: Context) {
    var pressed by remember { mutableStateOf(false) }
    val elevation = if (pressed) 2.dp else 8.dp

    Button(
        onClick = {
            if(username.isBlank() || password.isBlank()){
                Toast.makeText(context, "Niste uneli sve podatke!", Toast.LENGTH_SHORT).show()
            }
            else{
                pressed = true
                onLogin(username,password)
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = AccentPink,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(top=12.dp, bottom = 24.dp).fillMaxWidth()
            .height(56.dp).shadow(elevation, RoundedCornerShape(16.dp))
    ) {
        Text(
            text = stringResource(id = R.string.btn_login),
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
fun ForgotPassword(onClick: () ->Unit) {
    ClickableTextStyled(
        text = stringResource(id = R.string.login_forgot_password),
        onClick = onClick,
    )
}

@Composable
fun SignUpNavigation(onSignUpClick: () -> Unit, onGuestClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        AuthNavigation(title = stringResource(id = R.string.login_no_account),
            textClick = stringResource(id = R.string.login_sign_up), onSignUpClick)

        Button(
            onClick = onGuestClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = PrimaryDark
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = null,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.login_continue_as_guest),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}