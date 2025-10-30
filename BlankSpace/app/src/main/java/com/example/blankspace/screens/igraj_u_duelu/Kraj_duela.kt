package com.example.blankspace.screens.igraj_u_duelu

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.DuelViewModel

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7) // Svetlo roza za pozadinu kartice
private val TextMain = PrimaryDark
private val TableHeaderBg = AccentPink.copy(alpha = 0.8f)
private val TableRowOddBg = CardContainerColor.copy(alpha = 0.7f)
private val TableRowEvenBg = Color.White.copy(alpha = 0.8f)
private val WinnerColor = Color(0xFF4CAF50) // Zelena


@Composable
fun HeadlineTextCekanjeRezultata(text: String) {
    Text(text, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = TextMain)
}

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
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
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
    val context = LocalContext.current
    val uiState by viewModelDuel.uiState.collectAsState()
    val uiStateKraj by viewModelDuel.uiStateKrajDuela.collectAsState()

    LaunchedEffect(Unit) {
        uiState.duel?.poeni?.let {
            viewModelDuel.fetchKrajDuela(
                it,
                sifra,
                uiState.duel!!.rundePoeni,
                uiState.duel!!.runda,
                "true"
            )
        }
    }


    Surface(
        color = CardContainerColor, // Svetlo roza
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.8f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HeadlineTextCekanjeRezultata("Rezultati duela \uD83C\uDFC6")

            DuelResultsTable(uiStateKraj.krajDuela?.poeni_runde ?: emptyList(), uiStateKraj.krajDuela?.igrac1, uiStateKraj.krajDuela?.igrac2)

            val runde = uiStateKraj.krajDuela?.poeni_runde ?: emptyList()
            val ukupno1 = runde.sumOf { it.getOrElse(0) { 0 } }
            val ukupno2 = runde.sumOf { it.getOrElse(1) { 0 } }
            DisplayWinner(ukupno1, ukupno2)

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ActionButtonCekanjeRezultata(
                    onClick = {
                        navController.navigate(Destinacije.Generisi_sifru_sobe.ruta)
                    },
                    text = "Igraj ponovo",
                    modifier = Modifier.fillMaxWidth(0.7f)
                )
                Spacer(modifier = Modifier.height(12.dp))
                ActionButtonCekanjeRezultata(
                    onClick = {

                        navController.navigate(Destinacije.Login.ruta)
                    },
                    text = "Kraj",
                    containerColor = PrimaryDark.copy(alpha = 0.8f),
                    modifier = Modifier.fillMaxWidth(0.7f)
                )
            }
        }
    }
}

@Composable
fun DuelResultsTable(runde: List<List<Int>>, igrac1: String?, igrac2: String?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = TableRowEvenBg // Boja pozadine celog bloka
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .border(2.dp, TextMain.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
        ) {
            // Zaglavlje Tabele
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TableHeaderBg, RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Runda", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
                Text(igrac1 ?: "Igrač 1", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                Text(igrac2 ?: "Igrač 2", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
            }

            LazyColumn(contentPadding = PaddingValues(vertical = 4.dp), modifier = Modifier.height(250.dp)) {
                itemsIndexed(runde) { index, poeni ->
                    val isIgrac1Winner = poeni.getOrElse(0) { 0 } > poeni.getOrElse(1) { 0 }
                    val isIgrac2Winner = poeni.getOrElse(1) { 0 } > poeni.getOrElse(0) { 0 }

                    val backgroundColor = if (index % 2 == 0) TableRowEvenBg else TableRowOddBg

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(backgroundColor)
                            .padding(vertical = 8.dp, horizontal = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "${index + 1}", color = TextMain, fontSize = 14.sp)

                        // Poeni Igrača 1
                        Text(
                            text = "${poeni.getOrElse(0) { 0 }}",
                            fontWeight = if (isIgrac1Winner) FontWeight.ExtraBold else FontWeight.Normal,
                            color = if (isIgrac1Winner) WinnerColor else TextMain,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )

                        // Poeni Igrača 2
                        Text(
                            text = "${poeni.getOrElse(1) { 0 }}",
                            fontWeight = if (isIgrac2Winner) FontWeight.ExtraBold else FontWeight.Normal,
                            color = if (isIgrac2Winner) WinnerColor else TextMain,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                item {
                    val ukupno1 = runde.sumOf { it.getOrElse(0) { 0 } }
                    val ukupno2 = runde.sumOf { it.getOrElse(1) { 0 } }

                    val isIgrac1OverallWinner = ukupno1 > ukupno2
                    val isIgrac2OverallWinner = ukupno2 > ukupno1

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(TableHeaderBg.copy(alpha = 0.9f))
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "UKUPNO", fontWeight = FontWeight.Black, color = Color.White, fontSize = 15.sp)
                        Text(
                            text = "$ukupno1",
                            fontWeight = FontWeight.Black,
                            color = if (isIgrac1OverallWinner) WinnerColor else Color.White,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "$ukupno2",
                            fontWeight = FontWeight.Black,
                            color = if (isIgrac2OverallWinner) WinnerColor else Color.White,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
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
        else -> TextMain.copy(alpha = 0.7f)
    }

    Text(
        text = winnerText,
        fontSize = 22.sp,
        fontWeight = FontWeight.ExtraBold,
        color = winnerColor,
        modifier = Modifier.padding(vertical = 16.dp)
    )
}