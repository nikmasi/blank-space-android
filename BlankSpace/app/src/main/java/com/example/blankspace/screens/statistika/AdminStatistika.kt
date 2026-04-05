package com.example.blankspace.screens.statistika

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.AdminStatistikaViewModel
import com.example.blankspace.viewModels.AdminStatistikaUiState
import com.example.blankspace.ui.theme.*

@Composable
fun AdminStatistika(viewModel: AdminStatistikaViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAdminStatistika()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        AdminStatistikaContent(
            uiState = uiState,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun AdminStatistikaContent(uiState: AdminStatistikaUiState, modifier: Modifier) {
    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.94f)
            .fillMaxHeight(0.85f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AdminStatistikaHeader()

            Spacer(modifier = Modifier.height(12.dp))

            if (uiState.isRefreshing) {
                Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = PrimaryDark)
                }
            } else if (uiState.error != null) {
                Text("Greška: ${uiState.error}", color = Color.Red, modifier = Modifier.padding(16.dp))
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    item {
                        StatistikaSekcija(
                            title = "OSNOVNI SISTEMSKI PODACI",
                            titleColor = PrimaryDark
                        ) {
                            StatistikaRed("Registrovanih korisnika", uiState.informacije?.ukupnoKorisnika.toString())
                            StatistikaRed("Pesama u bazi", uiState.informacije?.ukupnoPesama.toString())
                            StatistikaRed("Predloga pesama", uiState.informacije?.ukupnoPredlogaPesamaNaCekanju.toString())
                            StatistikaRed("Predloga izvođača", uiState.informacije?.ukupnoPredlogaIzvodjacaNaCekanju.toString())
                        }
                    }

                    item {
                        StatistikaSekcija(
                            title = "NAJBOLJI KORISNICI",
                            titleColor = PrimaryDark
                        ) {
                            StatistikaRed(
                                title = "Najviše ličnih poena",
                                value = "${uiState.informacije?.korisnikSaNajviseLicnihPoenaIme ?: "Nema"}\n(${uiState.informacije?.korisnikSaNajviseLicnihPoenaPoeni ?: 0} pts)",
                            )
                            StatistikaRed(
                                title = "Najviši rang",
                                value = "${uiState.informacije?.korisnikSaNajviseRangPoenimaIme ?: "Nema"}\n(${uiState.informacije?.korisnikSaNajviseRangPoenimaPoeni ?: 0} pts)",
                                valueColor = HighlightColor
                            )
                        }
                    }

                    item {
                        StatistikaSekcija(
                            title = "AKTIVNOST IGARA",
                            titleColor = PrimaryDark
                        ) {
                            StatistikaRed("Ukupno odigranih duela", uiState.informacije?.brojDuela.toString(), valueColor = HighlightColor)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminStatistikaHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Sistemski Izveštaj",
            color = PrimaryDark,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 26.sp
        )
        Text(
            text = "Pregled performansi i baze u realnom vremenu",
            color = PrimaryDark.copy(alpha = 0.6f),
            fontSize = 14.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

@Composable
fun StatistikaSekcija(title: String, titleColor: Color, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = titleColor.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 10.dp)
            )
            content()
        }
    }
}

@Composable
fun StatistikaRed(title: String, value: String,valueColor: Color = PrimaryDark) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                color = PrimaryDark.copy(alpha = 0.8f),
                fontWeight = FontWeight.Medium
            )
        }

        Surface(color = PrimaryDark.copy(alpha = 0.05f), shape = RoundedCornerShape(8.dp)) {
            Text(
                text = value,
                fontSize = 14.sp,
                color = valueColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.End
            )
        }
    }
    HorizontalDivider(color = PrimaryDark.copy(alpha = 0.05f), thickness = 1.dp)
}