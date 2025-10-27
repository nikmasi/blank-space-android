package com.example.blankspace.screens.autorizacija

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.UiStateNL
import com.example.blankspace.viewModels.UiStateZL
import com.example.blankspace.viewModels.ZaboravljenaLozinkaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)

@Composable
fun PromenaLozinke(navController: NavController, viewModel: ZaboravljenaLozinkaViewModel, loginViewModel: LoginViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()

        PromenaLozinke_mainCard(
            navController = navController,
            viewModel = viewModel,
            loginViewModel = loginViewModel,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun PromenaLozinke_mainCard(navController: NavController, viewModel: ZaboravljenaLozinkaViewModel, loginViewModel: LoginViewModel, modifier: Modifier) {
    val uiState by viewModel.uiState.collectAsState()
    val uiStateNL by viewModel.uiStateNL.collectAsState()
    val context = LocalContext.current

    HandlePasswordChangeResponse(uiStateNL, context, loginViewModel, navController, uiState)

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.85f)
            .fillMaxHeight(0.55f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PasswordChangeHeader()

            Spacer(modifier = Modifier.height(24.dp))

            var novaLozinka by remember { mutableStateOf("") }
            var potvrdaLozinke by remember { mutableStateOf("") }

            PasswordFieldStyled(label = "Nova lozinka", value = novaLozinka, onValueChange = { novaLozinka = it })
            Spacer(modifier = Modifier.height(12.dp))
            PasswordFieldStyled(label = "Potvrdite lozinku", value = potvrdaLozinke, onValueChange = { potvrdaLozinke = it })

            Spacer(modifier = Modifier.height(32.dp))
            ChangePasswordButtonStyled(novaLozinka, potvrdaLozinke, uiState, viewModel, context)
        }
    }
}

@Composable
fun HandlePasswordChangeResponse(
    uiStateNL: UiStateNL,
    context: android.content.Context,
    loginViewModel: LoginViewModel,
    navController: NavController,
    uiState: UiStateZL
) {
    LaunchedEffect(uiStateNL.novaLozinka?.odgovor) {
        val odgovor = uiStateNL.novaLozinka?.odgovor
        if (!odgovor.isNullOrEmpty()) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, odgovor, Toast.LENGTH_SHORT).show()
            }
            if (odgovor.contains("Lozink")) {
                // Greška u lozinci
                return@LaunchedEffect
            } else if (odgovor.contains("Uspeh")) {

                loginViewModel.setKorisnikZL(uiState)
                navController.navigate(Destinacije.Login.ruta) {
                    popUpTo(Destinacije.PromenaLozinke.ruta) { inclusive = true } // Čišćenje back stack-a
                }
            }
        }
    }
}

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
    context: android.content.Context
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