package com.example.blankspace.screens.pocetne

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.pocetne.cards.PocetnaMainCard

@Composable
fun PocetnaOffline(onNavigateToOffline: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        PocetnaMainCard(onGameSoloClick = onNavigateToOffline)
    }
}