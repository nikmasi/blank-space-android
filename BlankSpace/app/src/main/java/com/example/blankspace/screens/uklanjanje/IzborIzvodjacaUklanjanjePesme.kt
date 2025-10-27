package com.example.blankspace.screens.uklanjanje

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
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
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.viewModels.UiStateIZU
import com.example.blankspace.viewModels.UiStateZ
import com.example.blankspace.viewModels.UklanjanjeViewModel
import com.example.blankspace.viewModels.ZanrViewModel
import kotlinx.coroutines.delay

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)
private val LightBackground = Color(0xFFF7F7F7)

@Composable
fun IzborIzvodjacaUklanjanjePesme(navController: NavController, viewModelUklanjanje: UklanjanjeViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        IzborIzvodjacaUklanjanjePesme_mainCard(
            navController = navController,
            viewModelUklanjanje = viewModelUklanjanje,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun IzborIzvodjacaUklanjanjePesme_mainCard(navController: NavController, viewModelUklanjanje: UklanjanjeViewModel, modifier: Modifier) {
    val context = LocalContext.current
    val viewModel: ZanrViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val uiStateUklanjanje by viewModelUklanjanje.uiStateIzvodjacaZanra.collectAsState()

    var selectedIzvodjacId by remember { mutableStateOf(-1) }
    var selectedIzvodjacIme by remember { mutableStateOf("") }

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
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IzvodjacUklanjanjeHeader()
                Spacer(modifier = Modifier.height(16.dp))

                IzvodjaciZaUklanjanjeListStyled(
                    uiState = uiState,
                    uiStateUklanjanje = uiStateUklanjanje,
                    selectedId = selectedIzvodjacId,
                    onSelect = { id, ime ->
                        selectedIzvodjacId = id
                        selectedIzvodjacIme = ime
                    }
                )
            }

            IzvodjacUklanjanjeButtonStyled(onClick = {
                if (selectedIzvodjacId != -1) {
                    viewModelUklanjanje.postaviPesmeIzv(selectedIzvodjacId, selectedIzvodjacIme)
                    viewModelUklanjanje.dohvatiPesmeIzvodjaca(selectedIzvodjacId, selectedIzvodjacIme, -1)
                    navController.navigate(Destinacije.UklanjanjePesme.ruta)
                } else {
                    Toast.makeText(context, "Niste izabrali nijednog izvođača", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}


@Composable
fun IzvodjacUklanjanjeHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Uklanjanje Pesme",
            color = PrimaryDark,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Odaberite izvođača čiju pesmu uklanjate.",
            style = MaterialTheme.typography.bodyMedium,
            color = PrimaryDark.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun IzvodjaciZaUklanjanjeListStyled(
    uiState: UiStateZ,
    uiStateUklanjanje: UiStateIZU,
    selectedId: Int,
    onSelect: (Int, String) -> Unit
) {
    when {
        uiStateUklanjanje.isRefreshing -> {
            CircularProgressIndicator(color = AccentPink)
        }
        uiStateUklanjanje.error != null -> {
            Text(text = "Greška: ${uiStateUklanjanje.error}", color = Color.Red, modifier = Modifier.padding(16.dp))
        }
        uiStateUklanjanje.izvodjaci.isEmpty() -> {
            Text(
                text = "Nema izvođača za prikaz.",
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
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiStateUklanjanje.izvodjaci) { izvodjac ->
                    val isSelected = selectedId == izvodjac.id

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(if (isSelected) 4.dp else 2.dp, RoundedCornerShape(12.dp))
                            .background(
                                color = LightBackground,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) AccentPink else PrimaryDark.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { onSelect(izvodjac.id, izvodjac.ime) }
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = izvodjac.ime,
                            color = PrimaryDark,
                            fontSize = 18.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold
                        )

                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = "Izabrano",
                                tint = AccentPink,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Spacer(modifier = Modifier.size(24.dp))
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun IzvodjacUklanjanjeButtonStyled(onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val elevation = if (pressed) 2.dp else 8.dp

    Button(
        onClick = {
            pressed = true
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = AccentPink,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(elevation, RoundedCornerShape(16.dp))
    ) {
        Text(
            text = "Dalje",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
    LaunchedEffect(pressed) {
        if (pressed) {
            delay(100)
            pressed = false
        }
    }
}