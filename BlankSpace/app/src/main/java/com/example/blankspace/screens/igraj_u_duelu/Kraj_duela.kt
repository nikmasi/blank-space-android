package com.example.blankspace.screens.igraj_u_duelu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.DuelViewModel
import com.example.blankspace.ui.theme.*

private val WinnerColor = Color(0xFF4CAF50)

@Composable
fun ActionButtonCekanjeRezultata(onClick: () -> Unit, text: String, modifier: Modifier, containerColor: Color = AccentPink) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.height(48.dp)
    ) {
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}


@Composable
fun Kraj_duela(navController: NavController,viewModelDuel:DuelViewModel,sifra:Int){
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        Kraj_duela_mainCard(
            navController = navController,
            viewModelDuel = viewModelDuel,
            sifra = sifra,
            modifier = Modifier.align(Alignment.Center) // Centriranje kartice
        )
    }
}
@Composable
fun Kraj_duela_mainCard(
    navController: NavController,
    viewModelDuel: DuelViewModel,
    sifra: Int,
    modifier: Modifier = Modifier
) {
    val uiState by viewModelDuel.uiState.collectAsState()
    val uiStateKraj by viewModelDuel.uiStateKrajDuela.collectAsState()

    LaunchedEffect(Unit) {
        uiState.duel?.poeni?.let {
            viewModelDuel.fetchKrajDuela(
                it, sifra, uiState.duel!!.rundePoeni, uiState.duel!!.runda, "true"
            )
        }
    }

    val rezultati = uiStateKraj.krajDuela?.poeni_runde ?: emptyList()
    val ukupno1 = rezultati.sumOf { it.getOrElse(0) { 0 } }
    val ukupno2 = rezultati.sumOf { it.getOrElse(1) { 0 } }

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.92f)
            .wrapContentHeight()
            .shadow(24.dp, RoundedCornerShape(32.dp)),
        shape = RoundedCornerShape(32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Duel Završen!",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentPink,
                    letterSpacing = 2.sp
                )
                Text(
                    "KONAČAN REZULTAT",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = PrimaryDark
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            DuelResultsTable(rezultati, uiStateKraj.krajDuela?.igrac1, uiStateKraj.krajDuela?.igrac2)

            Spacer(modifier = Modifier.height(16.dp))

            WinnerBanner(ukupno1, ukupno2)

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ActionButtonCekanjeRezultata(
                    onClick = { navController.navigate(Destinacije.Generisi_sifru_sobe.ruta) },
                    text = "IGRAJ PONOVO",
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Kraj igre",
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable { navController.navigate(Destinacije.Login.ruta) },
                    color = PrimaryDark.copy(alpha = 0.6f),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun DuelResultsTable(runde: List<List<Int>>, igrac1: String?, igrac2: String?) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.5f)),
        border = BorderStroke(1.dp, PrimaryDark.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Zaglavlje
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PrimaryDark)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("#", color = Color.White.copy(alpha = 0.7f), modifier = Modifier.width(30.dp), fontWeight = FontWeight.Bold)
                Text(igrac1 ?: "Igrač 1", color = Color.White, modifier = Modifier.weight(1f), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
                Text("VS", color = AccentPink, fontWeight = FontWeight.Black, fontSize = 12.sp)
                Text(igrac2 ?: "Igrač 2", color = Color.White, modifier = Modifier.weight(1f), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
            }

            // Redovi
            runde.forEachIndexed { index, poeni ->
                val p1 = poeni.getOrElse(0) { 0 }
                val p2 = poeni.getOrElse(1) { 0 }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (index % 2 == 0) Color.Transparent else PrimaryDark.copy(alpha = 0.03f))
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("${index + 1}.", color = PrimaryDark.copy(alpha = 0.4f), modifier = Modifier.width(30.dp), fontSize = 12.sp)

                    ResultCell(p1, p1 > p2, Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(20.dp))
                    ResultCell(p2, p2 > p1, Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun ResultCell(poeni: Int, isWinner: Boolean, modifier: Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = poeni.toString(),
            fontSize = 18.sp,
            fontWeight = if (isWinner) FontWeight.Black else FontWeight.Medium,
            color = if (isWinner) WinnerColor else PrimaryDark
        )
        if (isWinner) {
            Text(" ⭐", fontSize = 10.sp)
        }
    }
}

@Composable
fun WinnerBanner(ukupno1: Int, ukupno2: Int) {
    val (text, color) = when {
        ukupno1 > ukupno2 -> "POBEDNIK: IGRAČ 1" to WinnerColor
        ukupno2 > ukupno1 -> "POBEDNIK: IGRAČ 2" to WinnerColor
        else -> "REMI!" to PrimaryDark.copy(alpha = 0.6f)
    }

    Surface(
        color = color.copy(alpha = 0.15f),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, color.copy(alpha = 0.3f))
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
            color = color,
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center
        )
    }
}
@Composable
fun DisplayWinner(ukupno1: Int, ukupno2: Int) {
    val winnerText = when {
        ukupno1 > ukupno2 -> "Pobedio je Igrač 1!"
        ukupno2 > ukupno1 -> "Pobedio je Igrač 2!"
        else -> "Nerešeno je!"
    }

    val winnerColor = when {
        ukupno1 != ukupno2 -> WinnerColor
        else -> PrimaryDark.copy(alpha = 0.7f)
    }

    Text(
        text = winnerText,
        fontSize = 22.sp,
        fontWeight = FontWeight.ExtraBold,
        color = winnerColor,
        modifier = Modifier.padding(vertical = 16.dp)
    )
}