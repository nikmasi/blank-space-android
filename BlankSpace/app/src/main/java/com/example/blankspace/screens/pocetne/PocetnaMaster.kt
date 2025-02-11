package com.example.blankspace.screens.pocetne

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
fun PocetnaMaster(modifier: Modifier = Modifier,navController: NavController,viewModelLogin:LoginViewModel) {
    Box(modifier = modifier.fillMaxSize()) {
        BgCard2()

        checkLoginState(viewModelLogin,navController)

        val uiStateLogin by viewModelLogin.uiState.collectAsState()
        val ime by viewModelLogin.ime.collectAsState()
        val userName = uiStateLogin.login?.ime
        val isLoggedIn = uiStateLogin.login != null

        LaunchedEffect(uiStateLogin.login?.odgovor) {
            val odgovor = uiStateLogin.login?.odgovor
            if (!odgovor.isNullOrEmpty() && odgovor.contains("Logout")) {
                navController.navigate(Destinacije.Login.ruta)
            }
        }

        PocetnaMainCard(
            navController = navController,
            imgSize = 5,
            userName = ime.ime,
            isLoggedIn = isLoggedIn,
            onGameSoloClick = { navController.navigate(Destinacije.Nivo_igra_sam.ruta) },
            onGameDuelClick = { navController.navigate(Destinacije.Sifra_sobe_duel.ruta) },
            onSuggestArtistClick = { navController.navigate(Destinacije.PredlaganjeIzvodjaca.ruta) },
            onSuggestSongClick = { navController.navigate(Destinacije.PredlaganjePesme.ruta) }
        )
    }
}