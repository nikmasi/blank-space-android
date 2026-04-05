package com.example.blankspace.screens.takmicenje

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blankspace.data.retrofit.models.Mecevi
import com.example.blankspace.data.retrofit.models.PesmeMeceva
import com.example.blankspace.data.retrofit.models.Protivnik
import com.example.blankspace.ui.theme.AccentPink
import com.example.blankspace.ui.theme.DrawColor
import com.example.blankspace.ui.theme.LossColor
import com.example.blankspace.ui.theme.PrimaryDark
import com.example.blankspace.ui.theme.WinColor
import com.example.blankspace.viewModels.UiStateKorisnikPregled

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