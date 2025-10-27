package com.example.blankspace.screens.predlaganje

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.viewModels.DodavanjeViewModel
import com.example.blankspace.viewModels.IzvodjaciZanraViewModel
import com.example.blankspace.viewModels.UiStateIZU
import kotlinx.coroutines.delay

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)
private val LightBackground = Color(0xFFF7F7F7)

@Composable
fun IzborIzvodjaca(navController: NavController, viewModel: DodavanjeViewModel, zanr: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        IzborIzvodjaca_mainCard(
            navController = navController,
            zanr = zanr,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun IzborIzvodjaca_mainCard(navController: NavController, zanr: String, modifier: Modifier) {
    val viewModelZanr: IzvodjaciZanraViewModel = hiltViewModel()
    val uiStateZanr by viewModelZanr.uiState.collectAsState()

    var selectedIzvodjac by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(zanr) {
        viewModelZanr.fetchIzvodjaciZanraNaziv(zanr)
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
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IzvodjacIzborHeader(zanr)
                Spacer(modifier = Modifier.height(16.dp))

                IzvodjaciListStyled(uiStateZanr, selectedIzvodjac) { selectedName ->
                    selectedIzvodjac = selectedName
                }
            }

            IzvodjacIzborButtonStyled(onClick = {
                if (selectedIzvodjac.isNullOrBlank()) {
                    navController.navigate(Destinacije.ImeIzvodjaca.ruta + "/$zanr")
                } else {
                    navController.navigate(Destinacije.PesmaPodaciD.ruta + "/$zanr/$selectedIzvodjac")
                }
            }, selectedIzvodjac = selectedIzvodjac)
        }
    }
}

@Composable
fun IzvodjacIzborHeader(zanr: String) {
    Text(
        text = "Izbor Izvođača",
        color = PrimaryDark,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(
        text = "Odaberite izvođača u žanru: $zanr",
        style = MaterialTheme.typography.bodyMedium,
        color = PrimaryDark.copy(alpha = 0.8f),
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
fun IzvodjaciListStyled(uiStateZanr: UiStateIZU, selectedIzvodjac: String?, onSelect: (String) -> Unit) {
    when {
        uiStateZanr.isRefreshing -> {
            CircularProgressIndicator(color = AccentPink)
        }
        uiStateZanr.error != null -> {
            Text(text = "Greška: ${uiStateZanr.error}", color = Color.Red, modifier = Modifier.padding(16.dp))
        }
        uiStateZanr.izvodjaci.isEmpty() -> {
            Text(
                text = "Nema izvođača za ovaj žanr. Kliknite 'Dodaj izvođača'.",
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
                items(uiStateZanr.izvodjaci) { izv ->
                    val isSelected = selectedIzvodjac == izv.ime

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
                            .clickable { onSelect(izv.ime) }
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = izv.ime,
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
fun IzvodjacIzborButtonStyled(onClick: () -> Unit, selectedIzvodjac: String?) {
    var pressed by remember { mutableStateOf(false) }
    val elevation = if (pressed) 2.dp else 8.dp
    val buttonText = if (selectedIzvodjac.isNullOrBlank()) "Dodaj novog izvođača" else "Dalje"

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
            text = buttonText,
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