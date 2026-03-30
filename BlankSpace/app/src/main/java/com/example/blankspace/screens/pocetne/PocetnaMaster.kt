package com.example.blankspace.screens.pocetne

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.pocetne.cards.PocetnaMainCard
import com.example.blankspace.viewModels.LoginViewModel

@Composable
fun PocetnaMaster(modifier: Modifier = Modifier,viewModelLogin:LoginViewModel,
      onGameSoloClick: () -> Unit,
      onGameDuelClick: () -> Unit,
      onSuggestArtistClick: () -> Unit,
      onSuggestSongClick: () -> Unit,
      onSearchAndSuggestClick: () -> Unit,
      onGameSing: () -> Unit,
      onGameChallenge: () -> Unit,
      onLogOut: () -> Unit,
      onCheckLoginStates: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        BgCard2()

        checkLoginStates(viewModelLogin,onCheckLoginStates)

        val uiStateLogin by viewModelLogin.uiState.collectAsState()

        LaunchedEffect(uiStateLogin.login?.odgovor) {
            val odgovor = uiStateLogin.login?.odgovor
            if (!odgovor.isNullOrEmpty() && odgovor.contains("Logout")) {
                onLogOut()
            }
        }

        PocetnaMainCard(
            onGameSoloClick = onGameSoloClick,
            onGameDuelClick = onGameDuelClick,
            onSuggestArtistClick = onSuggestArtistClick,
            onSuggestSongClick = onSuggestSongClick,
            onSearchAndSuggestClick = onSearchAndSuggestClick,
            onGameSing = onGameSing,
            onGameChallenge = onGameChallenge
        )
    }
}