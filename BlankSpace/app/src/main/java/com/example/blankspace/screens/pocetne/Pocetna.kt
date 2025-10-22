package com.example.blankspace.screens.pocetne

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.pocetne.cards.PocetnaMainCard
import com.example.blankspace.viewModels.LoginViewModel

@Composable
fun Pocetna(modifier: Modifier = Modifier, navController: NavController,viewModelLogin:LoginViewModel) {
    Box(modifier = modifier.fillMaxSize()) {
        BgCard2()

        PocetnaMainCard(
            navController = navController,
            8,
            userName = null,
            isLoggedIn = false,
            onGameSoloClick = { navController.navigate(Destinacije.Nivo_igra_sam.ruta) },
            onGameDuelClick = { navController.navigate(Destinacije.Sifra_sobe_duel.ruta) },
        )
    }
}

@Composable
fun checkLoginState(viewModel: LoginViewModel,navController: NavController){
    val loginState by viewModel.uiState.collectAsState()
    Log.d("LOGIN check",loginState.toString())

    LaunchedEffect(loginState.login) {
        if (loginState.login == null) {
            navController.navigate(Destinacije.Login.ruta) {
                // Očisti stek
                popUpTo(Destinacije.Pocetna.ruta) { inclusive = true }
            }
        }
    }
}