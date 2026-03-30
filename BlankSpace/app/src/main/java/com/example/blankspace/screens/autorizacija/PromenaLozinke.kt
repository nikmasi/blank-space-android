package com.example.blankspace.screens.autorizacija

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.autorizacija.promena_lozinke.ChangePasswordButtonStyled
import com.example.blankspace.screens.autorizacija.promena_lozinke.PasswordChangeHeader
import com.example.blankspace.screens.autorizacija.promena_lozinke.PasswordFieldStyled
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.UiStateNL
import com.example.blankspace.viewModels.UiStateZL
import com.example.blankspace.viewModels.ZaboravljenaLozinkaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.blankspace.ui.theme.*

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

            PasswordFieldStyled(
                label = "Nova lozinka",
                value = novaLozinka,
                onValueChange = { novaLozinka = it })
            Spacer(modifier = Modifier.height(12.dp))
            PasswordFieldStyled(
                label = "Potvrdite lozinku",
                value = potvrdaLozinke,
                onValueChange = { potvrdaLozinke = it })

            Spacer(modifier = Modifier.height(32.dp))
            ChangePasswordButtonStyled(novaLozinka, potvrdaLozinke, uiState, viewModel, context)
        }
    }
}

@Composable
fun HandlePasswordChangeResponse(
    uiStateNL: UiStateNL,
    context: Context,
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
                return@LaunchedEffect
            } else if (odgovor.contains("Uspeh")) {

                loginViewModel.setKorisnikZL(uiState)
                navController.navigate(Destinacije.Login.ruta) {
                    popUpTo(Destinacije.PromenaLozinke.ruta) { inclusive = true }
                }
            }
        }
    }
}