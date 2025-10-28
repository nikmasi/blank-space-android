package com.example.blankspace.screens.sadrzaj

import com.example.blankspace.data.retrofit.models.Pesma
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.blankspace.data.retrofit.models.Izvodjac
import com.example.blankspace.data.retrofit.models.PesmePoIzvodjacimaResponse
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.AdminStatistikaViewModel
import com.example.blankspace.viewModels.IzvodjacZanrViewModel
import com.example.blankspace.viewModels.PesmePoIzvodjacimaUiState
import com.example.blankspace.viewModels.UiStateIZ

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)
private val LightBackground = Color(0xFFF7F7F7)

@Composable
fun SadrzajPesme(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        SadrzajPesme_mainCard(
            navController = navController,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun SadrzajPesme_mainCard(navController: NavController, modifier: Modifier) {
    val viewModel: AdminStatistikaViewModel = hiltViewModel()

    val uiState by viewModel.uiStatePesmePoIzvodjacima.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchPesmePoIzvodjacima()
    }

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.7f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            SadrzajPesmeHeader()
            Spacer(modifier = Modifier.height(16.dp))

            PesmaListaStyled(uiState = uiState)
        }
    }
}


@Composable
fun PesmaListaStyled(
    uiState: PesmePoIzvodjacimaUiState
) {
    when {
        uiState.isRefreshing -> {
            CircularProgressIndicator(color = AccentPink)
        }
        uiState.error != null -> {
            Text(text = "Greška: ${uiState.error}", color = Color.Red, modifier = Modifier.padding(16.dp))
        }
        uiState.pesmePoIzvodjacima?.isEmpty() == true -> {
            Text(
                text = "Nema pesama za prikaz.",
                color = PrimaryDark.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
        else -> {
            val grupisano = uiState.pesmePoIzvodjacima!!
                .groupBy { it.ime_izvodjaca ?: "Nepoznat izvođač" }

            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                grupisano.forEach { (izvodjac, pesme) ->
                    item {
                        IzvodjacSection(izvodjac = izvodjac, pesme = pesme)
                    }
                }
            }
        }
    }
}

@Composable
fun SadrzajPesmeHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Pregled pesama",
            color = PrimaryDark,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Spisak svih pesama u sistemu po izvodjacima.",
            style = MaterialTheme.typography.bodyMedium,
            color = PrimaryDark.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun IzvodjacSection(
    izvodjac: String,
    pesme: List<PesmePoIzvodjacimaResponse>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = CardContainerColor.copy(alpha = 0.6f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {
        Text(
            text = izvodjac,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryDark,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        pesme.forEach { pesma ->
            PesmaCardItem(pesma)
        }
    }
}

@Composable
fun PesmaCardItem(pesma: PesmePoIzvodjacimaResponse) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, PrimaryDark.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = pesma.naziv,
            color = PrimaryDark,
            fontSize = 16.sp
        )
    }
}

