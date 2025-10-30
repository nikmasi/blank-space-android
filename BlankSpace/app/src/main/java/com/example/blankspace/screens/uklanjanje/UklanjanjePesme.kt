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
import androidx.compose.runtime.*
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
import com.example.blankspace.data.retrofit.models.PesmeIzvodjaca
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.UiStateUklanjanjePesme
import com.example.blankspace.viewModels.UklanjanjeViewModel
import com.example.blankspace.viewModels.ZanrViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)
private val LightBackground = Color(0xFFF7F7F7)

@Composable
fun UklanjanjePesme(navController: NavController, viewModelUklanjanje: UklanjanjeViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        UklanjanjePesme_mainCard(
            navController = navController,
            viewModelUklanjanje = viewModelUklanjanje,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun UklanjanjePesme_mainCard(navController: NavController, viewModelUklanjanje: UklanjanjeViewModel, modifier: Modifier) {
    val uiStateUklanjanje by viewModelUklanjanje.uiStatePesma.collectAsState()
    val uiStatePesmeIzv by viewModelUklanjanje.uiStatePesmeIzv.collectAsState()
    val uiState by viewModelUklanjanje.uiStatePesmeIzvodjaca.collectAsState()

    val context = LocalContext.current
    var izv by remember { mutableStateOf(-1) }
    var ime by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (uiStatePesmeIzv.izvodjac_id != null && uiStatePesmeIzv.ime != null) {
            izv = uiStatePesmeIzv.izvodjac_id!!
            ime = uiStatePesmeIzv.ime!!
            viewModelUklanjanje.dohvatiPesmeIzvodjaca(izv, ime, -1)
        }
    }

    HandleUklanjanjePesmeResponse(viewModelUklanjanje, context, uiStateUklanjanje, izv, ime)

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
            PesmaUklanjanjeHeader(izvodjacIme = ime)
            Spacer(modifier = Modifier.height(16.dp))

            // --- STILIZOVANA LISTA KARTICA ZA PESME ---
            PesmeListaZaUklanjanjeStyled(
                pesme = uiState.pesmeIzvodjaca,
                isLoading = uiState.isRefreshing,
                error = uiState.error,
                onRemove = { id -> viewModelUklanjanje.fetchUklanjanjePesme(id) }
            )
        }
    }
}

@Composable
fun PesmaUklanjanjeHeader(izvodjacIme: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Uklanjanje Pesme",
            color = PrimaryDark,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Pesme izvođača: ${if (izvodjacIme.isBlank()) "..." else izvodjacIme}",
            style = MaterialTheme.typography.bodyMedium,
            color = PrimaryDark.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun PesmeListaZaUklanjanjeStyled(
    pesme: List<PesmeIzvodjaca>,
    isLoading: Boolean,
    error: String?,
    onRemove: (Int) -> Unit
) {
    when {
        isLoading -> {
            CircularProgressIndicator(color = AccentPink)
        }
        error != null -> {
            Text(text = "Greška: $error", color = Color.Red, modifier = Modifier.padding(16.dp))
        }
        pesme.isEmpty() -> {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp)
            ) {
                Icon(Icons.Filled.Warning, contentDescription = "Upozorenje", tint = AccentPink)
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Nema pesama za uklanjanje.",
                    color = PrimaryDark.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )
            }
        }
        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(pesme) { item ->
                    PesmaUklanjanjeCard(item = item, onRemove = onRemove)
                }
            }
        }
    }
}

@Composable
fun PesmaUklanjanjeCard(item: PesmeIzvodjaca, onRemove: (Int) -> Unit) {
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
            text = item.naziv ?: "Nepoznato",
            color = PrimaryDark,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = {
                Toast.makeText(context, "Uklanjam ${item.naziv}...", Toast.LENGTH_SHORT).show()
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
fun HandleUklanjanjePesmeResponse(
    viewModelUklanjanje: UklanjanjeViewModel,
    context: Context,
    uiStateUklanjanje: UiStateUklanjanjePesme,
    izv: Int,
    ime: String
) {
    LaunchedEffect(uiStateUklanjanje.uklanjanjePesme?.odgovor) {
        val odgovor = uiStateUklanjanje.uklanjanjePesme?.odgovor
        if (!odgovor.isNullOrEmpty()) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, odgovor, Toast.LENGTH_LONG).show()
            }
            viewModelUklanjanje.dohvatiPesmeIzvodjaca(izv, ime, -1)
            return@LaunchedEffect
        }
    }
}