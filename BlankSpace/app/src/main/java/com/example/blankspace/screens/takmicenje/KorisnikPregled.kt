package com.example.blankspace.screens.takmicenje

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.navigation.NavController
import com.example.blankspace.data.retrofit.models.Mecevi
import com.example.blankspace.data.retrofit.models.PesmeMeceva
import com.example.blankspace.data.retrofit.models.Protivnik
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.KorisniciViewModel
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.UiStateK
import com.example.blankspace.viewModels.UiStateKorisnikPregled

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)
private val WinColor = Color(0xFF4CAF50) // Zelena za pobedu
private val LossColor = Color(0xFFF44336) // Crvena za poraz
private val DrawColor = Color(0xFFFFC107) // zuta za nereseno

@Composable
fun KorisnikPregled(navController: NavController, viewModelKorisnici: KorisniciViewModel, viewModelLogin:LoginViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        KorisnikPregled_mainCard(
            navController = navController,
            modifier = Modifier.align(Alignment.Center),
            viewModelKorisnici,
            viewModelLogin
        )
    }
}

@Composable
fun KorisnikPregled_mainCard(navController: NavController, modifier: Modifier,viewModelKorisnici: KorisniciViewModel, viewModelLogin:LoginViewModel) {

    val uiState by viewModelKorisnici.uiStateKorisnikPregled.collectAsState()
    val uiStateLog by viewModelLogin.uiState.collectAsState()

    LaunchedEffect(Unit) {
        uiStateLog.login?.let { viewModelKorisnici.fetchInformacijeKorisnikPregled(it.korisnicko_ime) }
    }

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
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderStatistika()
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { UkupniSkorCard(uiState) }
                item { TopProtivniciCard(uiState) }
                item { NedavniMeceviCard(uiState) }
                item { PesmeMecevaCard(uiState) }
            }
        }
    }
}

@Composable
fun HeaderStatistika() {
    Text(
        text = "Duel - Statistika",
        color = PrimaryDark,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(
        text = "Pregled tvojih duela i protivnika.",
        color = PrimaryDark.copy(alpha = 0.8f),
        fontSize = 16.sp,
        textAlign = androidx.compose.ui.text.style.TextAlign.Center
    )
}

@Composable
fun UkupniSkorCard(uiState:UiStateKorisnikPregled) {
    CardStatistika(title = "Ukupno", titleColor = AccentPink) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            SkorKolona("Pobede", uiState.informacije?.ukupnoPobeda.toString(), WinColor)
            SkorKolona("Porazi", uiState.informacije?.ukupnoPoraza.toString(), LossColor)
            SkorKolona("Nerešeno", uiState.informacije?.ukupnoNereseno.toString(), DrawColor)
            SkorKolona("Ukupno", uiState.informacije?.ukupnoDuela.toString(), PrimaryDark)
        }
    }
}

@Composable
fun PesmeMecevaCard(uiState: UiStateKorisnikPregled) {
    CardStatistika(title = "Pesme", titleColor = PrimaryDark) {
        uiState.informacije?.pesmeMeceva?.forEach { match ->
            PesmaMecevaRow(match)
        }
    }
}

@Composable
fun NedavniMeceviCard(uiState: UiStateKorisnikPregled) {
    CardStatistika(title = "Mečevi", titleColor = PrimaryDark) {
        uiState.informacije?.mecevi?.forEach { match ->
            MatchRow(match)
            if (uiState.informacije!!.mecevi.indexOf(match) < 4 && uiState.informacije!!.mecevi.indexOf(match) < uiState.informacije!!.mecevi.size - 1) {
                Divider(color = PrimaryDark.copy(alpha = 0.1f), modifier = Modifier.padding(horizontal = 4.dp))
            }
        }
    }
}

@Composable
fun TopProtivniciCard(uiState:UiStateKorisnikPregled) {
    CardStatistika(title = "Moji Protivnici", titleColor = AccentPink) {
        uiState.informacije?.protivnici?.reversed()?.forEachIndexed { index, opponent ->
            TopOpponentRow(opponent, index + 1)
            if (index < uiState.informacije.protivnici.size - 1) {
                Divider(color = PrimaryDark.copy(alpha = 0.1f), modifier = Modifier.padding(horizontal = 4.dp))
            }
        }
    }
}


@Composable
fun CardStatistika(title: String, titleColor: Color, content: @Composable ColumnScope.() -> Unit) {
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
fun SkorKolona(label: String, value: String, valueColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = valueColor
        )
        Text(
            text = label,
            fontSize = 14.sp,
            color = PrimaryDark.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun MatchRow(match: Mecevi) {
    var resultName=""
    if (match.brojPoenaMoj>match.brojPoenaProtivnika){
        resultName="Win"
    }else if(match.brojPoenaMoj<match.brojPoenaProtivnika){
        resultName="Loss"
    }
    else{
        resultName="Draw"
    }
    val resultColor = when (resultName) {
        "Win" -> WinColor
        "Loss" -> LossColor
        else -> DrawColor
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = match.ime,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = PrimaryDark
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${match.brojPoenaMoj} - ${match.brojPoenaProtivnika}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryDark.copy(alpha = 0.9f),
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = resultName,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(resultColor)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}

@Composable
fun TopOpponentRow(opponent: Protivnik, rank: Int) {
    val winRatePercentage = (opponent.procenatPobede).toInt()
    val rankColor = if (rank == 1) AccentPink else PrimaryDark.copy(alpha = 0.7f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "#$rank",
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = rankColor,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = opponent.ime,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryDark
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "${opponent.brojMeceva} mečeva",
                fontSize = 14.sp,
                color = PrimaryDark.copy(alpha = 0.7f)
            )
            Text(
                text = "$winRatePercentage% pobeda",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = WinColor
            )
        }
    }
}

@Composable
fun PesmaMecevaRow(pesma: PesmeMeceva) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = pesma.naziv,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryDark
            )
            Text(
                text = pesma.izvodjac,
                fontSize = 14.sp,
                color = PrimaryDark.copy(alpha = 0.7f)
            )
        }

        Text(
            text = "${pesma.brojPojavljivanja}x",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = AccentPink
        )
    }
}

@Composable
fun PesmaMecevaCard(pesma: PesmeMeceva) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F0F8)) // svetlo roze pozadina
    ) {
        PesmaMecevaRow(pesma = pesma)
    }
}

@Composable
fun PesmeMecevaList(pesme: List<PesmeMeceva>) {
    Column {
        pesme.forEach { pesma ->
            PesmaMecevaCard(pesma = pesma)
        }
    }
}
