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

// --- BOJE ---
private val PrimaryDark = Color(0xFF49006B) // Tamno ljubičasta/Bordo
private val AccentPink = Color(0xFFEC8FB7) // Akcent roza
private val CardContainerColor = Color(0xFFF0DAE7) // Svetlo roza za karticu
private val TextMain = PrimaryDark
private val TextAccent = AccentPink
private val TimeWarningColor = Color(0xFFD32F2F) // Crvena za upozorenje

// ODRŽAVANJE ORIGINALNIH NAZIVA KOMPONENTI (Radi kompatibilnosti)
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


// --- GLAVNE KOMPONENTE ---

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

    // Prvo generisanje šifre (ako već nije)
    // NAPOMENA: Pretpostavljam da se generisanje dešava na nekom prethodnom ekranu,
    // a ovde samo prikazujemo rezultat uiStateSifra.

    HandleSifraResponse(navController, viewModelDuel, context) // Poziv bez viška uiState-ova

    Surface(
        color = CardContainerColor, // Svetlo roza
        modifier = modifier
            .fillMaxWidth(0.9f) // Uže
            .fillMaxHeight(0.5f) // Kraće
            .shadow(16.dp, RoundedCornerShape(24.dp)), // Dodavanje senke
        shape = RoundedCornerShape(24.dp) // Zaobljeni uglovi
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

            // Prikaz šifre
            Text(
                text = "${uiStateSifra.sifraResponse?.sifra}",
                fontSize = 48.sp,
                fontWeight = FontWeight.Black,
                color = AccentPink // Akcentna boja za šifru
            )

            Spacer(modifier = Modifier.height(22.dp))

            // Status čekanja
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

            // Dugme Odustani
            ActionButton(
                onClick = {
                    // Pre navigacije, očisti stanje sobe na serveru (opciono, ali preporučeno)
                    // viewModelDuel.ocistiSobu(uiStateSifra.sifraResponse?.sifra)
                    navController.navigate(Destinacije.Login.ruta)
                },
                text = "Odustani",
                containerColor = TimeWarningColor, // Crvena boja za odustajanje
                modifier = Modifier.fillMaxWidth(0.7f)
            )
        }
    }
}

// --- LOGIKA POLLINGA ---

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

        // Polling loop
        while (true) {
            // POZOVI FUNKCIJU koja šalje zahtev na backend
            viewModelDuel.stigaoIgrac(sifra)

            // Sačekaj pre sledećeg poziva
            delay(3000)

            val stigaoIgrac = viewModelDuel.uiStateStigaoIgrac.value.stigaoIgrac?.stigao

            // OBAVEZNO KORISTI JEDNU NAVIGACIJU
            if (stigaoIgrac == "true") {
                // Prekinimo audio ako postoji (iz predostrožnosti)
                viewModelDuel.stopAudio()

                // POKRENI FETCH DUEL PODATAKA PRE NAVIGACIJE
                val stihovi = uiStateSifra.sifraResponse?.stihovi
                if (stihovi != null) {
                    viewModelDuel.fetchDuel(0, 0, stihovi, rundaPoeni = emptyList(), context)
                }

                // AŽURIRAJ LOKALNO STANJE
                viewModelDuel.upisiRedniBroj(1)
                viewModelDuel.upisiSifruSobe(sifra)

                // NAVIGACIJA
                navController.navigate(Destinacije.Duel.ruta + "/" + 0 + "/" + 0 + "/${sifra}")
                break
            }
        }
    }
}