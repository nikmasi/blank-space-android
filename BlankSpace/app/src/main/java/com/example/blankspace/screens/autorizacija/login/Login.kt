package com.example.blankspace.screens.autorizacija.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.blankspace.R
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.autorizacija.auth_components.AuthButton
import com.example.blankspace.screens.autorizacija.auth_components.AuthHeader
import com.example.blankspace.screens.autorizacija.auth_components.DividerWithIconModernAuth
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.modifiers.columnMainStyle
import com.example.blankspace.ui.modifiers.mainCardStyle
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.UiStateL
import com.example.blankspace.ui.theme.*

@Composable
fun Login(
    viewModelLogin: LoginViewModel, onSignUpClick: () -> Unit,
    onGuestClick: () -> Unit, onForgotClick: () -> Unit, onNavigate: (String) -> Unit
) {

    val uiState by viewModelLogin.uiState.collectAsState()

    LoginContent(
        uiState = uiState,
        onSignUpClick = onSignUpClick,
        onGuestClick = onGuestClick,
        onForgotClick = onForgotClick,
        onLogin = { u, p -> viewModelLogin.fetchLogin(u, p) },
        onNavigate = onNavigate
    )
}

@Composable
fun LoginContent(
    uiState: UiStateL, onSignUpClick: () -> Unit,
    onGuestClick: () -> Unit, onForgotClick: () -> Unit,
    onLogin: (String, String) -> Unit, onNavigate: (String) -> Unit
){
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()

        Login_mainCard(
            modifier = Modifier.align(Alignment.Center),
            onSignUpClick = onSignUpClick,
            onGuestClick = onGuestClick,
            onForgotClick = onForgotClick,
            onLogin = onLogin
        )
    }

    HandleLoginResponse(
        uiState = uiState,
        onNavigate = { ruta ->
            onNavigate(ruta)
        }
    )
}

@Composable
fun Login_mainCard(
    modifier: Modifier,
    onSignUpClick: () -> Unit,
    onGuestClick: () -> Unit,
    onForgotClick: () -> Unit,
    onLogin: (String,String) -> Unit
) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(
        color = CardContainerColor,
        modifier = modifier.mainCardStyle(widthFraction = 0.9f, heightFraction = 0.75f, cornerRadius = 32.dp),
        shape = RoundedCornerShape(32.dp)
    ) {
        Column(
            modifier = Modifier.columnMainStyle().padding(top=19.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            LoginImage()
            AuthHeader(stringResource(id = R.string.title_login))

            LoginFields(username = username, password = password, onUsernameChange = { username = it }, onPasswordChange = { password = it })

            AuthButton(
                text = stringResource(id = R.string.btn_login),
                validation = {
                    if (username.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "Niste uneli sve podatke!", Toast.LENGTH_SHORT).show()
                        false
                    } else true
                },
                onClickAction = { onLogin(username, password) },
                modifier = Modifier.padding(top=12.dp, bottom = 24.dp)
            )

            DividerWithIconModernAuth()

            Spacer(modifier = Modifier.height(24.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ForgotPassword(onClick = onForgotClick)
                Spacer(modifier = Modifier.height(12.dp))
                SignUpNavigation(onSignUpClick=onSignUpClick, onGuestClick = onGuestClick)
            }
        }
    }
}

@Composable
fun HandleLoginResponse(uiState: UiStateL, onNavigate: (String) -> Unit) {
    val context = LocalContext.current

    LaunchedEffect(uiState.login) {
        val login = uiState.login

        if (login?.odgovor?.contains("Pogrešn") == true) {
            Toast.makeText(context, login.odgovor, Toast.LENGTH_SHORT).show()
        }

        login?.tip?.let { tip ->
            val ruta = when (tip) {
                "S" -> Destinacije.PocetnaStudent.ruta
                "B" -> Destinacije.PocetnaBrucos.ruta
                "M" -> Destinacije.PocetnaMaster.ruta
                "A" -> Destinacije.PocetnaAdmin.ruta
                else -> null
            }
            ruta?.let { onNavigate(it) }
        }
    }
}