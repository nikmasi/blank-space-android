package com.example.blankspace.screens.pocetne

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.pocetne.cards.PocetnaMainCard

@Composable
fun Pocetna(modifier: Modifier = Modifier, navController: NavController) {
    Box(modifier = modifier.fillMaxSize()) {
        BgCard2()

        PocetnaMainCard(
            navController = navController,
            8,
            userName = null,
            isLoggedIn = false,
            onGameSoloClick = { navController.navigate(Destinacije.Nivo_igra_sam.ruta) },
            onGameDuelClick = { navController.navigate(Destinacije.Sifra_sobe_duel.ruta) }
        )
    }
}

