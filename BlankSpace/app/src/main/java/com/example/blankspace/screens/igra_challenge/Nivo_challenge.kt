package com.example.blankspace.screens.igra_challenge

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.igra_pogodi_i_pevaj.Nivo_mainCardStyled

@Composable
fun Nivo_challenge(onNavigateToGenre: (String) -> Unit){
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        Nivo_mainCardStyled(
            onDifficultySelected = onNavigateToGenre,
            modifier = Modifier.align(Alignment.Center),
            text="Challenge mode"
        )
    }
}