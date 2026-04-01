package com.example.blankspace.screens.sadrzaj

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.blankspace.R
import com.example.blankspace.data.retrofit.models.StihoviPoPesmamaResponse
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.AdminStatistikaViewModel
import com.example.blankspace.viewModels.StihoviPoPesmamaUiState
import com.example.blankspace.ui.theme.*

@Composable
fun SadrzajStihovi() {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        SadrzajStihovi_mainCard(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun SadrzajStihovi_mainCard(modifier: Modifier) {
    val viewModel: AdminStatistikaViewModel = hiltViewModel()
    val uiState by viewModel.uiStateStihoviPoPesmama.collectAsState()

    LaunchedEffect(Unit) { viewModel.fetchStihoviPoPesmama() }

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.7f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 32.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            SadrzajHeader(
                text1 = stringResource(id = R.string.lyrics_overview),
                text2 = stringResource(id = R.string.list_of_all_lyrics)
            )
            Spacer(modifier = Modifier.height(16.dp))

            StihoviListaStyled(uiState = uiState)
        }
    }
}


@Composable
fun StihoviListaStyled(uiState: StihoviPoPesmamaUiState) {
    when {
        uiState.isRefreshing -> { CircularProgressIndicator(color = AccentPink) }
        uiState.error != null -> {
            Text(text = "Greška: ${uiState.error}", color = Color.Red, modifier = Modifier.padding(16.dp))
        }
        uiState.stihoviPoPesmama?.isEmpty() == true -> {
            Text(
                text = "Nema pesama za prikaz.",
                color = PrimaryDark.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
        else -> {
            val grupisano = uiState.stihoviPoPesmama!!.groupBy { it.pesma ?: "Nepoznata pesma" }

            LazyColumn(
                modifier = Modifier.fillMaxHeight(0.8f).padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                grupisano.forEach { (pesma,stih) ->
                    item { StihSection(pesma, stihovi = stih) }
                }
            }
        }
    }
}

@Composable
fun StihSection(pesma: String, stihovi: List<StihoviPoPesmamaResponse>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = CardContainerColor.copy(alpha = 0.6f), shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(
            text = pesma,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryDark,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        stihovi.forEach { stih -> StihCardItem(stih) }
    }
}

@Composable
fun StihCardItem(stih: StihoviPoPesmamaResponse) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, PrimaryDark.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = stih.poznat_tekst+ " "+stih.nepoznat_tekst+" "+stih.zvuk_ime,
            color = PrimaryDark,
            fontSize = 16.sp
        )
    }
}