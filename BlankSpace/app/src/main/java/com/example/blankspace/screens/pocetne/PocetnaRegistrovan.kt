package com.example.blankspace.screens.pocetne

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.pocetne.cards.PocetnaMainCard
import com.example.blankspace.viewModels.LoginViewModel

@Composable
fun PocetnaRegistrovan(
    modifier: Modifier = Modifier,
    viewModelLogin: LoginViewModel,
    onLogOut: () -> Unit,
    onCheckLoginStates: () -> Unit,
    onGameSoloClick: (() -> Unit)? = null,
    onGameDuelClick: (() -> Unit)? = null,
    onSuggestArtistClick: (() -> Unit)? = null,
    onSuggestSongClick: (() -> Unit)? = null,
    onSearchAndSuggestClick: (() -> Unit)? = null,
    onGameSing: (() -> Unit)? = null,
    onGameChallenge: (() -> Unit)? = null
) {
    val uiStateLogin by viewModelLogin.uiState.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        BgCard2()

        checkLoginStates(viewModelLogin, onCheckLoginStates)

        LaunchedEffect(uiStateLogin.login?.odgovor) {
            val odgovor = uiStateLogin.login?.odgovor
            if (!odgovor.isNullOrEmpty() && odgovor.contains("Logout")) {
                onLogOut()
            }
        }

        PocetnaMainCard(
            onGameSoloClick = onGameSoloClick ?: {},
            onGameDuelClick = onGameDuelClick ?: {},
            onSuggestArtistClick = onSuggestArtistClick,
            onSuggestSongClick = onSuggestSongClick,
            onSearchAndSuggestClick = onSearchAndSuggestClick,
            onGameSing = onGameSing,
            onGameChallenge = onGameChallenge
        )
    }
}