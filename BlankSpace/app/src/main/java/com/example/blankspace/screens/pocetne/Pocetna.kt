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
fun Pocetna(modifier: Modifier = Modifier, onGameSoloClick: () -> Unit, onGameDuelClick: () -> Unit) {
    Box(modifier = modifier.fillMaxSize()) {
        BgCard2()

        PocetnaMainCard(
            onGameSoloClick = onGameSoloClick,
            onGameDuelClick = onGameDuelClick
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
                popUpTo(Destinacije.Pocetna.ruta) { inclusive = true }
            }
        }
    }
}

@Composable
fun checkLoginStates(viewModel: LoginViewModel,onClick:()->Unit){
    val loginState by viewModel.uiState.collectAsState()
    Log.d("LOGIN check",loginState.toString())

    LaunchedEffect(loginState.login) {
        if (loginState.login == null) {
            onClick()
        }
    }
}
