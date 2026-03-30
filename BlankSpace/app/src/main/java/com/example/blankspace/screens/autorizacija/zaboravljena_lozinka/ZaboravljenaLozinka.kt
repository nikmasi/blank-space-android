package com.example.blankspace.screens.autorizacija.zaboravljena_lozinka

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.modifiers.columnMainStyle
import com.example.blankspace.ui.modifiers.mainCardStyle
import com.example.blankspace.viewModels.UiStateZL
import com.example.blankspace.viewModels.ZaboravljenaLozinkaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import com.example.blankspace.ui.theme.*

@Composable
fun ZaboravljenaLozinka(viewModel: ZaboravljenaLozinkaViewModel,onNavigateToQuestion: () -> Unit) {

    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()

        ZaboravljenaLozinka_mainCard(
            modifier = Modifier.align(Alignment.Center),
            onResetClick = { username ->
                viewModel.fetchZaboravljenaLozinka(username)
            }
        )
    }
    HandleForgotPasswordResponse(uiState, onSuccess = onNavigateToQuestion)
}

@Composable
fun ZaboravljenaLozinka_mainCard(modifier: Modifier, onResetClick: (String) -> Unit) {
    val context= LocalContext.current

    Surface(
        color = CardContainerColor,
        modifier = modifier.mainCardStyle(heightFraction = 0.45f, widthFraction = 0.8f),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.columnMainStyle(),
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

            ResetPasswordButton(username = username, context = context, onResetClick= { onResetClick(username) })
        }
    }
}

@Composable
fun HandleForgotPasswordResponse(uiState: UiStateZL, onSuccess: () -> Unit) {
    val context = LocalContext.current

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
            onSuccess()
        }
    }
}