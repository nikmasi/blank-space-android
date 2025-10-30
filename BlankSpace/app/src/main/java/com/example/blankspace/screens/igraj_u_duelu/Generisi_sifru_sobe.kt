package com.example.blankspace.screens.igraj_u_duelu

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.DuelViewModel
import com.example.blankspace.viewModels.UiStateSifSobe
import com.example.blankspace.viewModels.UiStateStigaoIgrac
import kotlinx.coroutines.delay

private val PrimaryDark = Color(0xFF49006B) // Tamno ljubičasta/Bordo
private val AccentPink = Color(0xFFEC8FB7) // Akcent roza
private val CardContainerColor = Color(0xFFF0DAE7) // Svetlo roza za karticu
private val TextMain = PrimaryDark
private val TextAccent = AccentPink
private val TimeWarningColor = Color(0xFFD32F2F) // Crvena za upozorenje

@Composable
fun HeadlineText(text: String) {
    Text(text, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = TextMain)
}

@Composable
fun BodyText(text: String) {
    Text(text, fontSize = 16.sp, color = TextMain.copy(alpha = 0.8f))
}

@Composable
fun ActionButton(onClick: () -> Unit, text: String, modifier: Modifier, containerColor: Color = AccentPink) {
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
fun Generisi_sifru_sobe(navController: NavController,viewModelDuel:DuelViewModel){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Generisi_sifru_sobe_mainCard(
            navController = navController,
            viewModelDuel = viewModelDuel,
            modifier = Modifier.align(Alignment.Center) // Centriranje kartice
        )
    }
}

@Composable
fun Generisi_sifru_sobe_mainCard(
    navController: NavController,
    viewModelDuel: DuelViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiStateStigaoIgrac by viewModelDuel.uiStateStigaoIgrac.collectAsState()
    val uiStateSifra by viewModelDuel.uiStateSifSobe.collectAsState()

    HandleSifraResponse(navController, viewModelDuel, context)

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f) // Uže
            .fillMaxHeight(0.5f) // Kraće
            .shadow(16.dp, RoundedCornerShape(24.dp)), // Dodavanje senke
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp), // Povećan padding
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HeadlineText("Generisana šifra sobe:")

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "${uiStateSifra.sifraResponse?.sifra}",
                fontSize = 48.sp,
                fontWeight = FontWeight.Black,
                color = AccentPink
            )

            Spacer(modifier = Modifier.height(22.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (uiStateStigaoIgrac.isRefreshing) {
                    CircularProgressIndicator(
                        modifier = Modifier.height(20.dp),
                        color = TextAccent
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                BodyText("Kada drugi igrač unese šifru, počeće igra...")
            }

            Spacer(modifier = Modifier.height(42.dp))

            ActionButton(
                onClick = {

                    navController.navigate(Destinacije.Login.ruta)
                },
                text = "Odustani",
                containerColor = TimeWarningColor, // Crvena boja za odustajanje
                modifier = Modifier.fillMaxWidth(0.7f)
            )
        }
    }
}


@Composable
fun HandleSifraResponse(
    navController: NavController,
    viewModelDuel: DuelViewModel,
    context: Context
){
    val sifraSobe by viewModelDuel.sifraSobe.collectAsState()
    val uiStateStigaoIgrac by viewModelDuel.uiStateStigaoIgrac.collectAsState()
    val uiStateSifra by viewModelDuel.uiStateSifSobe.collectAsState()

    LaunchedEffect(sifraSobe.sifra) {
        val sifra = sifraSobe.sifra
        if (sifra == -1) return@LaunchedEffect

        while (true) {
            viewModelDuel.stigaoIgrac(sifra)

            delay(3000)

            val stigaoIgrac = viewModelDuel.uiStateStigaoIgrac.value.stigaoIgrac?.stigao

            if (stigaoIgrac == "true") {
                viewModelDuel.stopAudio()

                val stihovi = uiStateSifra.sifraResponse?.stihovi
                if (stihovi != null) {
                    viewModelDuel.fetchDuel(0, 0, stihovi, rundaPoeni = emptyList(), context)
                }

                viewModelDuel.upisiRedniBroj(1)
                viewModelDuel.upisiSifruSobe(sifra)

                navController.navigate(Destinacije.Duel.ruta + "/" + 0 + "/" + 0 + "/${sifra}")
                break
            }
        }
    }
}