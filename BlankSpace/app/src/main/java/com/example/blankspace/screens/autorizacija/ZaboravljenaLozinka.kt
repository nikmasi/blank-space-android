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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.viewModels.UiStateZL
import com.example.blankspace.viewModels.ZaboravljenaLozinkaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)

@Composable
fun ZaboravljenaLozinka(navController: NavController, viewModel: ZaboravljenaLozinkaViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()

        ZaboravljenaLozinka_mainCard(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ZaboravljenaLozinka_mainCard(navController: NavController, viewModel: ZaboravljenaLozinkaViewModel, modifier: Modifier) {
    val uiState by viewModel.uiState.collectAsState()
    val context= LocalContext.current

    HandleForgotPasswordResponse(uiState, context, navController)

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight(0.45f)
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
            ForgotPasswordHeader()

            Text(
                text = "Unesite Vaše korisničko ime da bismo postavili sigurnosno pitanje.",
                style = MaterialTheme.typography.bodyMedium,
                color = PrimaryDark.copy(alpha = 0.8f),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            var username by remember { mutableStateOf("") }

            ForgotPasswordField(username = username, onValueChange = { username = it })

            Spacer(modifier = Modifier.height(24.dp))

            ResetPasswordButton(username = username, context = context, viewModel = viewModel)
        }
    }
}

@Composable
fun HandleForgotPasswordResponse(
    uiState: UiStateZL,
    context: android.content.Context,
    navController: NavController
) {
    LaunchedEffect(uiState.zaboravljenaLozinka?.odgovor) {
        val odgovor = uiState.zaboravljenaLozinka?.odgovor
        if (!odgovor.isNullOrEmpty()) {
            if (odgovor.contains("Pogrešno korisničko ime")) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, odgovor, Toast.LENGTH_SHORT).show()
                }
                return@LaunchedEffect
            }
            delay(100)
            navController.navigate(Destinacije.ZaboravljenaLozinkaPitanje.ruta)
        }
    }
}

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
fun ResetPasswordButton(username: String, context: android.content.Context, viewModel: ZaboravljenaLozinkaViewModel) {
    var pressed by remember { mutableStateOf(false) }
    val elevation = if (pressed) 2.dp else 8.dp

    Button(
        onClick = {
            if (username.isBlank()) {
                Toast.makeText(context, "Niste uneli korisničko ime!", Toast.LENGTH_SHORT).show()
            } else {
                pressed = true
                viewModel.fetchZaboravljenaLozinka(username)
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