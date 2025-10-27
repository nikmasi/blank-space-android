package com.example.blankspace.screens.uklanjanje

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
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
import com.example.blankspace.data.retrofit.models.IzvodjaciZanra
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.IzvodjaciZanraViewModel
import com.example.blankspace.viewModels.UiStateIZU
import com.example.blankspace.viewModels.UiStateUklanjanjeIzvodjaca
import com.example.blankspace.viewModels.UklanjanjeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)
private val LightBackground = Color(0xFFF7F7F7)

@Composable
fun UklanjanjeIzvodjaca(navController: NavController, zanr: Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        UklanjanjeIzvodjaca_mainCard(
            navController = navController,
            zanr = zanr,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun UklanjanjeIzvodjaca_mainCard(navController: NavController, zanr: Int, modifier: Modifier) {
    val viewModel: IzvodjaciZanraViewModel = hiltViewModel()
    val viewModelUklanjanje: UklanjanjeViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsState()
    val uiStateUklanjanje by viewModelUklanjanje.uiStateIzvodjac.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchIzvodjaciZanra(zanr)
    }

    HandleUklanjanjeIzvodjacaResponse(context, viewModel, uiStateUklanjanje, zanr)

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
            IzvodjacUklanjanjeHeaderStyled()
            Spacer(modifier = Modifier.height(16.dp))

            IzvodjaciListaZaUklanjanjeStyled(
                uiState = uiState,
                onRemove = { izvodjacId ->
                    viewModelUklanjanje.fetchUklanjanjeIzvodjac(izvodjacId)
                }
            )
        }
    }
}

@Composable
fun IzvodjacUklanjanjeHeaderStyled() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Uklanjanje Izvođača",
            color = PrimaryDark,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Odaberite izvođača kojeg želite trajno ukloniti iz žanra.",
            style = MaterialTheme.typography.bodyMedium,
            color = PrimaryDark.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun IzvodjaciListaZaUklanjanjeStyled(
    uiState: UiStateIZU,
    onRemove: (Int) -> Unit
) {
    when {
        uiState.isRefreshing -> {
            CircularProgressIndicator(color = AccentPink)
        }
        uiState.error != null -> {
            Text(text = "Greška: ${uiState.error}", color = Color.Red, modifier = Modifier.padding(16.dp))
        }
        uiState.izvodjaci.isEmpty() -> {
            Text(
                text = "Nema izvođača za prikaz u ovom žanru.",
                color = PrimaryDark.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp) // Razmak između kartica
            ) {

                items(uiState.izvodjaci) { item ->
                    IzvodjacUklanjanjeCardStyled(item = item, onRemove = onRemove)
                }
            }
        }
    }
}

@Composable
fun IzvodjacUklanjanjeCardStyled(item: IzvodjaciZanra, onRemove: (Int) -> Unit) {
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(
                color = LightBackground,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = PrimaryDark.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        Text(
            text = item.ime.toString(),
            color = PrimaryDark,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = {
                Toast.makeText(context, "Uklanjam ${item.ime}...", Toast.LENGTH_SHORT).show()
                onRemove(item.id)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = AccentPink,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
            modifier = Modifier.height(40.dp)
        ) {
            Text("Ukloni", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun HandleUklanjanjeIzvodjacaResponse(
    context: Context,
    viewModel: IzvodjaciZanraViewModel,
    uiStateUklanjanje: UiStateUklanjanjeIzvodjaca,
    zanr: Int
){
    LaunchedEffect(uiStateUklanjanje.uklanjanjeIzvodjaca?.odgovor) {
        val odgovor = uiStateUklanjanje.uklanjanjeIzvodjaca?.odgovor
        if (!odgovor.isNullOrEmpty()) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, odgovor, Toast.LENGTH_LONG).show()
            }
            viewModel.fetchIzvodjaciZanra(zanr)
            return@LaunchedEffect
        }
    }
}