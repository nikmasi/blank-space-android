package com.example.blankspace.screens.igraj_u_duelu

import android.content.Context
import android.widget.Toast
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
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.DuelViewModel
import com.example.blankspace.viewModels.UiStateCekanjeRezultata
import com.example.blankspace.viewModels.UiStateD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun Cekanje_rezultata(navController: NavController,viewModelDuel:DuelViewModel,poeni: Int){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))
        Cekanje_rezultata_mainCard(navController,viewModelDuel,poeni)
    }
}

@Composable
fun Cekanje_rezultata_mainCard(navController: NavController,viewModelDuel:DuelViewModel,poeni:Int) {
    val context = LocalContext.current
    val uiStateCekanjeRezultata by viewModelDuel.uiStateCekanjeRezultata.collectAsState()
    val uiStateD by viewModelDuel.uiState.collectAsState()

    HandleCekanjeRezultataResponse(viewModelDuel,poeni,uiStateD)
    HandleCekanjeRezultataOdgovorResponse(navController,context, viewModelDuel,poeni,uiStateCekanjeRezultata)

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
            HeadlineText("Igra je završena, rezultat se obrađuje...:")
            Spacer(modifier = Modifier.height(34.dp))
            BodyText("Čeka se da drugi igrač završi igru...")
        }
    }
}

@Composable
fun HandleCekanjeRezultataResponse(viewModelDuel: DuelViewModel,poeni: Int,uiStateD: UiStateD){
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            viewModelDuel.fetchCekanjeRezultata(
                poeni/10, viewModelDuel.sifraSobe.value.sifra,
                uiStateD.duel!!.rundePoeni, viewModelDuel.redniBroj.value.redniBroj
            )
        }
    }
}

@Composable
fun HandleCekanjeRezultataOdgovorResponse(
    navController: NavController,
    context: Context,
    viewModelDuel: DuelViewModel,
    poeni: Int,
    uiStateCekanjeRezultata: UiStateCekanjeRezultata
){
    LaunchedEffect(uiStateCekanjeRezultata.cekanjeRezultata?.odgovor) {
        val odgovor = uiStateCekanjeRezultata.cekanjeRezultata?.odgovor
        if (!odgovor.isNullOrEmpty()) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, odgovor, Toast.LENGTH_SHORT).show()
            }
            if (odgovor.contains("Čeka se rezultat duela")) {
                return@LaunchedEffect
            }
            viewModelDuel.uiState.value.duel?.let {
                viewModelDuel.fetchCekanjeRezultata(poeni,viewModelDuel.sifraSobe.value.sifra,
                    it.rundePoeni,viewModelDuel.redniBroj.value.redniBroj)
            }
            navController.navigate(Destinacije.Kraj_duela.ruta)
        }

    }
}