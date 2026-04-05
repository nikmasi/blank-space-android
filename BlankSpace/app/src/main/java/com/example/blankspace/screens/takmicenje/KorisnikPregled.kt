package com.example.blankspace.screens.takmicenje

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.KorisniciViewModel
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.ui.theme.*

@Composable
fun KorisnikPregled(viewModelKorisnici: KorisniciViewModel, viewModelLogin:LoginViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        KorisnikPregled_mainCard(
            modifier = Modifier.align(Alignment.Center),
            viewModelKorisnici,
            viewModelLogin
        )
    }
}

@Composable
fun KorisnikPregled_mainCard(modifier: Modifier,viewModelKorisnici: KorisniciViewModel, viewModelLogin:LoginViewModel) {

    val uiState by viewModelKorisnici.uiStateKorisnikPregled.collectAsState()
    val uiStateLog by viewModelLogin.uiState.collectAsState()

    LaunchedEffect(Unit) {
        uiStateLog.login?.let { viewModelKorisnici.fetchInformacijeKorisnikPregled(it.korisnicko_ime) }
    }

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.75f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderStatistika()
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { UkupniSkorCard(uiState) }
                item { TopProtivniciCard(uiState) }
                item { NedavniMeceviCard(uiState) }
                item { PesmeMecevaCard(uiState) }
            }
        }
    }
}