package com.example.blankspace.screens.igra_offline

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.blankspace.screens.igra_pogodi_i_pevaj.Nivo_mainCardStyled
import com.example.blankspace.screens.pocetne.cards.BgCard2

@Composable
fun Nivo_igra_offline(onDifficultySelected: (String) -> Unit){
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        Nivo_mainCardStyled(
            onDifficultySelected = onDifficultySelected,
            modifier = Modifier.align(Alignment.Center),
            text= "Offline igra"
        )
    }
}