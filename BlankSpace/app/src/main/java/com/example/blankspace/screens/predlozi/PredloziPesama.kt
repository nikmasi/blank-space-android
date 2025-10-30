package com.example.blankspace.screens.uklanjanje

import android.content.Context
import android.widget.Toast
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
import com.example.blankspace.data.retrofit.models.PredloziPesamaResponse
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.PredloziViewModel
import com.example.blankspace.viewModels.UiStatePredloziPes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val AccentGreen = Color(0xFF66BB6A)
private val AccentRed = Color(0xFFEF5350)
private val CardContainerColor = Color(0xFFF0DAE7)
private val LightBackground = Color(0xFFF7F7F7)

@Composable
fun PredloziPesama(navController: NavController, viewModelPredlozi: PredloziViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        PredloziPesama_mainCard(
            navController = navController,
            viewModelPredlozi = viewModelPredlozi,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun PredloziPesama_mainCard(navController: NavController, viewModelPredlozi: PredloziViewModel, modifier: Modifier) {
    val context = LocalContext.current
    val uiState by viewModelPredlozi.uiStateP.collectAsState()

    LaunchedEffect(Unit) {
        viewModelPredlozi.fetchPredloziPesama()
    }
    Spacer(modifier = Modifier.height(30.dp))
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
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            PredloziPesamaHeader()
            Spacer(modifier = Modifier.height(16.dp))

            PredloziPesamaListaStyled(
                uiState = uiState,
                navController = navController,
                viewModel = viewModelPredlozi
            )
        }
    }
}

@Composable
fun PredloziPesamaHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Predlozi Pesama",
            color = PrimaryDark,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Pregledajte predloge korisnika. Prihvatite za dodavanje ili odbijte.",
            style = MaterialTheme.typography.bodyMedium,
            color = PrimaryDark.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun PredloziPesamaListaStyled(
    uiState: UiStatePredloziPes,
    navController: NavController,
    viewModel: PredloziViewModel
) {
    when {
        uiState.isRefreshing -> {
            CircularProgressIndicator(color = AccentPink)
        }
        uiState.error != null -> {
            Text(text = "Greška: ${uiState.error}", color = Color.Red, modifier = Modifier.padding(16.dp))
        }
        uiState.predloziPesama.isEmpty() -> {
            Text(
                text = "Nema novih predloga pesama.",
                color = PrimaryDark.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.predloziPesama) { item ->
                    PredlogPesmeCard(item = item, navController = navController, viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun PredlogPesmeCard(
    item: PredloziPesamaResponse,
    navController: NavController,
    viewModel: PredloziViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(16.dp))
            .border(1.dp, PrimaryDark.copy(alpha = 0.1f), RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = LightBackground)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = item.naziv_pesme,
                color = PrimaryDark,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Izvođač:", color = PrimaryDark.copy(alpha = 0.6f), fontSize = 12.sp)
                    Text(text = item.izv_ime, color = PrimaryDark, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                }
                Spacer(Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Žanr:", color = PrimaryDark.copy(alpha = 0.6f), fontSize = 12.sp)
                    Text(text = item.zan_naziv, color = PrimaryDark, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                }
                Spacer(Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Predložio:", color = PrimaryDark.copy(alpha = 0.6f), fontSize = 12.sp)
                    Text(text = item.kor_ime, color = PrimaryDark, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                }
            }

            Divider(modifier = Modifier.padding(vertical = 10.dp), color = PrimaryDark.copy(alpha = 0.1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = {
                        //viewModel.sacuvajPesmuPredlozi(item.id) // Prvo uklonimo predlog
                        viewModel.odbijPredlogPesme(item.id)
                        navController.navigate("${Destinacije.PesmaPodaci2.ruta}/${item.zan_naziv}/${item.izv_ime}/${item.naziv_pesme}")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AccentGreen, contentColor = Color.White),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f).padding(end = 6.dp)
                ) {
                    Text("Prihvati", fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { viewModel.odbijPredlogPesme(item.id) },
                    colors = ButtonDefaults.buttonColors(containerColor = AccentRed, contentColor = Color.White),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f).padding(start = 6.dp)
                ) {
                    Text("Odbij", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}