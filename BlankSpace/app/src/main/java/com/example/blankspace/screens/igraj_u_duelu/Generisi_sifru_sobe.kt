package com.example.blankspace.screens.igraj_u_duelu

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blankspace.ui.components.BodyText
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.ui.components.SmallButton
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.DuelViewModel
import com.example.blankspace.viewModels.UiStateSifSobe
import com.example.blankspace.viewModels.UiStateStigaoIgrac
import kotlinx.coroutines.delay

@Composable
fun Generisi_sifru_sobe(navController: NavController,viewModelDuel:DuelViewModel){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))
        Generisi_sifru_sobe_mainCard(navController,viewModelDuel)
    }
}

@Composable
fun Generisi_sifru_sobe_mainCard(navController: NavController,viewModelDuel:DuelViewModel) {
    val context = LocalContext.current
    val uiStateStigaoIgrac by viewModelDuel.uiStateStigaoIgrac.collectAsState()
    val uiStateSifra by viewModelDuel.uiStateSifSobe.collectAsState()
    val uiStateDuel by viewModelDuel.uiStateSifSobe.collectAsState()

    LaunchedEffect(uiStateSifra.sifraResponse?.sifra) {
        uiStateSifra.sifraResponse?.sifra?.let { viewModelDuel.stigaoIgrac(it) }
    }

    HandleSifraResponse(navController, viewModelDuel, context, uiStateStigaoIgrac, uiStateSifra, uiStateDuel)

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight(0.6f),
        shape = RoundedCornerShape(60.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HeadlineText("Generisana šifra sobe:")

            Spacer(modifier = Modifier.height(22.dp))
            HeadlineText("${uiStateDuel.sifraResponse?.sifra}")

            Spacer(modifier = Modifier.height(22.dp))

            BodyText("Kada drugi igrač unese šifru, počeće igra...")

            Spacer(modifier = Modifier.height(42.dp))

            SmallButton(onClick = {},
                text = "Odustani", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun HandleSifraResponse(
    navController: NavController,
    viewModelDuel: DuelViewModel,
    context:Context,
    uiStateStigaoIgrac: UiStateStigaoIgrac,
    uiStateSifra: UiStateSifSobe,
    uiStateDuel:UiStateSifSobe
){
    LaunchedEffect(true) {
        while (true) {
            delay(3000)
            uiStateSifra.sifraResponse?.sifra?.let { viewModelDuel.stigaoIgrac(it) }
            if (uiStateStigaoIgrac.stigaoIgrac?.stigao == "true") {
                // Kada je stigao, predji na sledecu stranicu
                uiStateSifra.sifraResponse?.stihovi?.let {
                    viewModelDuel.fetchDuel(0, 0, it, rundaPoeni = emptyList(), context)
                }
                viewModelDuel.upisiRedniBroj(redniBroj = 1)
                uiStateDuel.sifraResponse?.sifra?.let { viewModelDuel.upisiSifruSobe(it) }
                navController.navigate(Destinacije.Duel.ruta + "/" + 0 + "/" + 0)
                break // Prekini polling kada je igrac stigao
            }
        }
    }
}