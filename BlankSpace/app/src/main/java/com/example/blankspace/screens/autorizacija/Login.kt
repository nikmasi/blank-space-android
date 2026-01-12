package com.example.blankspace.screens.autorizacija

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.R
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.autorizacija.auth_components.AuthHeader
import com.example.blankspace.screens.autorizacija.auth_components.AuthNavigation
import com.example.blankspace.screens.autorizacija.auth_components.ClickableTextStyled
import com.example.blankspace.screens.autorizacija.auth_components.DividerWithIconModernAuth
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.modifiers.columnMainStyle
import com.example.blankspace.ui.modifiers.mainCardStyle
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.UiStateL
import kotlinx.coroutines.delay
import com.example.blankspace.ui.theme.*

@Composable
fun Login(navController: NavController, viewModelLogin: LoginViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        Login_mainCard(
            navController = navController,
            viewModel = viewModelLogin,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun Login_mainCard(navController: NavController, viewModel: LoginViewModel, modifier: Modifier) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val onSignUpClick = remember { { navController.navigate(Destinacije.Registracija.ruta) } }
    val onGuestClick = remember { { navController.navigate(Destinacije.Pocetna.ruta) } }
    val onForgotClick = remember { { navController.navigate(Destinacije.ZaboravljenaLozinka.ruta) } }

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

            LoginButton(username, password, viewModel, context)

            DividerWithIconModernAuth()

            Spacer(modifier = Modifier.height(24.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ForgotPassword(onClick = onForgotClick)
                Spacer(modifier = Modifier.height(12.dp))
                SignUpNavigation(onSignUpClick=onSignUpClick, onGuestClick = onGuestClick)
            }
        }
    }
    HandleLoginResponse(uiState = uiState, context = context, navController = navController)
}

@Composable
fun HandleLoginResponse(uiState: UiStateL, context: android.content.Context, navController: NavController) {
    LaunchedEffect(uiState.login?.odgovor) {
        val odgovor = uiState.login?.odgovor
        if (!odgovor.isNullOrEmpty() && odgovor.contains("Pogrešn")) {
            Toast.makeText(context, odgovor, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(uiState.login?.tip) {
        uiState.login?.tip?.let {
            when (it) {
                "S" -> navController.navigate(Destinacije.PocetnaStudent.ruta)
                "B" -> navController.navigate(Destinacije.PocetnaBrucos.ruta)
                "M" -> navController.navigate(Destinacije.PocetnaMaster.ruta)
                "A" -> navController.navigate(Destinacije.PocetnaAdmin.ruta)
            }
        }
    }
}

@Composable
private fun LoginImage() {
    Image(
        painter = painterResource(id = R.mipmap.ic_launcher_foreground),
        contentDescription = "Logo",
        modifier = Modifier.fillMaxWidth(0.6f).padding(vertical = 8.dp),
        contentScale = ContentScale.Fit
    )
}

@Composable
private fun LoginFields(username: String, password: String, onUsernameChange: (String) -> Unit, onPasswordChange: (String) -> Unit) {
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
fun LoginButton(username: String, password: String, viewModel: LoginViewModel, context: android.content.Context) {
    var pressed by remember { mutableStateOf(false) }
    val elevation = if (pressed) 2.dp else 8.dp

    Button(
        onClick = {
            if(username.isBlank() || password.isBlank()){
                Toast.makeText(context, "Niste uneli sve podatke!", Toast.LENGTH_SHORT).show()
            }
            else{
                pressed = true
                viewModel.fetchLogin(username,password)
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