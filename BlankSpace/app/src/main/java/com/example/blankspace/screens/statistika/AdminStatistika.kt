package com.example.blankspace.screens.statistika


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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.AdminStatistikaViewModel
import com.example.blankspace.ui.components.SmallButton // Pretpostavljena komponenta
import com.example.blankspace.viewModels.AdminStatistikaUiState

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)
private val HighlightColor = Color(0xFF66BB6A)

@Composable
fun AdminStatistika(navController: NavController, viewModel: AdminStatistikaViewModel = hiltViewModel()) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        AdminStatistika_mainCard(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun AdminStatistika_mainCard(navController: NavController, viewModel: AdminStatistikaViewModel, modifier: Modifier) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAdminStatistika()
    }
    Spacer(modifier = Modifier.height(28.dp))
    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.8f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AdminStatistikaHeader()
            Spacer(modifier = Modifier.height(16.dp))

            when {
                uiState.isRefreshing -> CircularProgressIndicator(color = AccentPink)
                uiState.error != null -> Text("Greška pri učitavanju: ${uiState.error}", color = Color.Red)
                else -> StatistikaContent(uiState)
            }
        }
    }
}

@Composable
fun AdminStatistikaHeader() {
    Text(
        text = "Kompletna Statistika",
        color = PrimaryDark,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(
        text = "Pregled ključnih metrika sistema.",
        color = PrimaryDark.copy(alpha = 0.8f),
        fontSize = 16.sp,
        textAlign = androidx.compose.ui.text.style.TextAlign.Center
    )
}

@Composable
fun StatistikaContent(uiState: AdminStatistikaUiState) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { GlavniPodaciCard(uiState) }
        item { KorisniciStatistikaCard(uiState) }
        item { SadrzajStatistikaCard(uiState) }
    }
}

@Composable
fun GlavniPodaciCard(uiState: AdminStatistikaUiState) {
    StatistikaKard(title = "Glavni Podaci", titleColor = AccentPink) {
        StatistikaRed("Ukupno registrovanih korisnika:", uiState.informacije?.ukupnoKorisnika.toString())
        Divider(color = PrimaryDark.copy(alpha = 0.1f))
        StatistikaRed("Ukupno pesama u bazi:", uiState.informacije?.ukupnoPesama.toString())
        Divider(color = PrimaryDark.copy(alpha = 0.1f))
        StatistikaRed("Ukupno predloga pesama na čekanju:", uiState.informacije?.ukupnoPredlogaPesamaNaCekanju.toString())
        Divider(color = PrimaryDark.copy(alpha = 0.1f))
        StatistikaRed("Ukupno predloga izvodjaca na čekanju:",
            uiState.informacije?.ukupnoPredlogaIzvodjacaNaCekanju.toString())

    }
}

@Composable
fun KorisniciStatistikaCard(uiState: AdminStatistikaUiState) {
    StatistikaKard(title = "Korisnička Aktivnost", titleColor = PrimaryDark) {
        StatistikaRed("Korisnicko ime korisnika sa najvise licnih poena:",
            uiState.informacije?.korisnikSaNajviseLicnihPoenaIme ?: "Nema podataka")
        StatistikaRed("Korisnicko ime korisnika sa najvise rang poena:",
            uiState.informacije?.korisnikSaNajviseLicnihPoenaIme?.toString() ?: "Nema podataka", isValueLeft = false, valueColor = HighlightColor)
        Divider(color = PrimaryDark.copy(alpha = 0.1f))
        //StatistikaRed("Prosečan rezultat po igri:", String.format("%.2f", uiState.averageScore), valueColor = HighlightColor)
        //Divider(color = PrimaryDark.copy(alpha = 0.1f))
        //StatistikaRed("Korisnika registrovanih danas:", uiState.newUsersToday.toString(), valueColor = TextColor(uiState.newUsersToday > 0))
    }
}

@Composable
fun SadrzajStatistikaCard(uiState: AdminStatistikaUiState) {
    StatistikaKard(title = "Statistika Sadržaja", titleColor = AccentPink) {
        //StatistikaRed("Najpopularniji žanr:", uiState.mostPopularGenre ?: "Nema podataka")
        //Divider(color = PrimaryDark.copy(alpha = 0.1f))
        StatistikaRed("Ukupno odigranih duel igara:", uiState.informacije?.brojDuela.toString(), valueColor = HighlightColor)
        //Divider(color = PrimaryDark.copy(alpha = 0.1f))
        //StatistikaRed("Pesme dodate od strane admina:", uiState.adminAddedSongs.toString())
    }
}

@Composable
fun StatistikaKard(title: String, titleColor: Color, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = titleColor,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            content()
        }
    }
}

@Composable
fun StatistikaRed(title: String, value: String, isValueLeft: Boolean = true, valueColor: Color = PrimaryDark) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = if (isValueLeft) Arrangement.SpaceBetween else Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = PrimaryDark.copy(alpha = 0.9f),
            fontWeight = FontWeight.Medium,
            modifier = if (!isValueLeft) Modifier.weight(1f) else Modifier
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = valueColor,
            fontWeight = FontWeight.Bold,
            textAlign = if (isValueLeft) androidx.compose.ui.text.style.TextAlign.End else androidx.compose.ui.text.style.TextAlign.Start
        )
    }
}

@Composable
fun TextColor(isPositive: Boolean): Color = if (isPositive) HighlightColor else PrimaryDark.copy(alpha = 0.8f)