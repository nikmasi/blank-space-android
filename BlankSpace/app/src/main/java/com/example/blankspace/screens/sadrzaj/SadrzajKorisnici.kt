package com.example.blankspace.screens.sadrzaj

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.blankspace.R
import com.example.blankspace.data.retrofit.models.KorisniciResponse
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.KorisniciViewModel
import com.example.blankspace.ui.theme.*

@Composable
fun SadrzajKorisnici() {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        SadrzajKorisnici_mainCard(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun SadrzajKorisnici_mainCard(modifier: Modifier) {
    val viewModel: KorisniciViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) { viewModel.fetchKorisnici() }

    Spacer(modifier = Modifier.height(36.dp))
    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.75f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 32.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            SadrzajHeader(
                text1 = stringResource(id = R.string.user_overview),
                text2 = stringResource(id = R.string.list_of_all_users)
            )
            Spacer(modifier = Modifier.height(16.dp))

            KorisniciListaSadrzaj(
                korisnici = uiState.korisnici,
                isLoading = uiState.isRefreshing,
                error = uiState.error
            )
        }
    }
}

@Composable
fun KorisniciListaSadrzaj(korisnici: List<KorisniciResponse>, isLoading: Boolean, error: String?) {
    when {
        isLoading -> { CircularProgressIndicator(color = AccentPink) }
        error != null -> {
            Text(text = stringResource(id = R.string.error) +": $error", color = Color.Red,
                modifier = Modifier.padding(16.dp))
        }
        korisnici.isEmpty() -> {
            Text(
                text = stringResource(id = R.string.no_registered_users),
                color = PrimaryDark.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
        else -> {
            LazyColumn(
                modifier = Modifier.fillMaxHeight(1f).padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(korisnici) { item ->
                    KorisnikSadrzajCard(item = item)
                }
            }
        }
    }
}

@Composable
fun KorisnikSadrzajCard(item: KorisniciResponse) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(color = LightBackground, shape = RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = PrimaryDark.copy(alpha = 0.3f), shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.korisnicko_ime,
                color = PrimaryDark,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = stringResource(id = R.string.activity) + ": ${item.poslednja_aktivnost.toString()}",
                color = PrimaryDark.copy(alpha = 0.7f),
                fontSize = 12.sp,
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}
