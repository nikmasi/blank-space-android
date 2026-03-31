package com.example.blankspace.screens.igra_challenge

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.igra_sam.Zanr_mainCard

@Composable
fun Zanr_challenge(selectedNivo:String, onNavigateNext: (String, String) -> Unit){
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        Zanr_mainCard(
            selectedNivo = selectedNivo,
            modifier = Modifier.align(Alignment.Center),
            onNavigateNext = onNavigateNext,
        )
    }
}