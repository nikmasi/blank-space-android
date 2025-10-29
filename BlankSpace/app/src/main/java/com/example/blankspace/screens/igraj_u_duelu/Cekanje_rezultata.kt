package com.example.blankspace.screens.igraj_u_duelu

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.DuelViewModel
import com.example.blankspace.viewModels.UiStateCekanjeRezultata
import com.example.blankspace.viewModels.UiStateD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

// --- BOJE ---
private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7) // Svetlo roza za pozadinu kartice
private val TextMain = PrimaryDark

// --- POMOĆNE KOMPONENTE (Za konzistentnost) ---

@Composable
fun HeadlineTextCek(text: String) {
    Text(text, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = TextMain, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
}

@Composable
fun BodyTextCek(text: String) {
    Text(text, fontSize = 16.sp, color = TextMain.copy(alpha = 0.8f), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
}

// --- GLAVNE KOMPONENTE ---

@Composable
fun Cekanje_rezultata(navController: NavController,viewModelDuel:DuelViewModel,poeni: Int,sifra:Int){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Cekanje_rezultata_mainCard(
            navController = navController,
            viewModelDuel = viewModelDuel,
            poeni = poeni,
            sifra = sifra,
            modifier = Modifier.align(Alignment.Center) // Centriranje kartice
        )
    }
}

@Composable
fun Cekanje_rezultata_mainCard(
    navController: NavController,
    viewModelDuel: DuelViewModel,
    poeni:Int,
    sifra:Int,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiStateCekanjeRezultata by viewModelDuel.uiStateCekanjeRezultata.collectAsState()
    val uiStateD by viewModelDuel.uiState.collectAsState()

    // Polling za slanje rezultata
    HandleCekanjeRezultataResponse(viewModelDuel,poeni,uiStateD)

    // Polling za odgovor
    HandleCekanjeRezultataOdgovorResponse(navController,context, viewModelDuel,poeni,uiStateCekanjeRezultata,sifra)

    Surface(
        color = CardContainerColor, // Svetlo roza
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.4f) // Manja kartica
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HeadlineTextCek("Igra je završena.")
            HeadlineTextCek("Rezultat se obrađuje...")

            Spacer(modifier = Modifier.height(34.dp))

            CircularProgressIndicator(
                color = AccentPink, // Roza boja za indikator
                modifier = Modifier.height(48.dp).width(48.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            BodyTextCek("Čeka se da drugi igrač završi rundu...")
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HandleCekanjeRezultataResponse(viewModelDuel: DuelViewModel,poeni: Int,uiStateD: UiStateD){
    // Koristimo sifraSobe kao ključ da osiguramo da se polling pokrene kada se šifra postavi
    LaunchedEffect(viewModelDuel.sifraSobe.value.sifra) {
        // Dodajemo inicijalni kratki delay pre prvog poziva da bi se UI učitao
        delay(1000)

        while (true) {
            viewModelDuel.fetchCekanjeRezultata(
                poeni/10,
                viewModelDuel.sifraSobe.value.sifra,
                uiStateD.duel?.rundePoeni ?: emptyList(), // Koristimo safe call
                viewModelDuel.redniBroj.value.redniBroj
            )
            // Polling interval
            delay(3000)
        }
    }
}

@Composable
fun HandleCekanjeRezultataOdgovorResponse(
    navController: NavController,
    context: Context,
    viewModelDuel: DuelViewModel,
    poeni: Int,
    uiStateCekanjeRezultata: UiStateCekanjeRezultata,
    sifra:Int
){
    LaunchedEffect(uiStateCekanjeRezultata.cekanjeRezultata?.odgovor) {
        val odgovor = uiStateCekanjeRezultata.cekanjeRezultata?.odgovor

        // Provera da li postoji odgovor
        if (!odgovor.isNullOrEmpty()) {

            // Prikaz Toast poruke na glavnom threadu
            withContext(Dispatchers.Main) {
                Toast.makeText(context, odgovor, Toast.LENGTH_SHORT).show()
            }

            // Ako odgovor i dalje kaže da se čeka, ne radimo ništa i dozvoljavamo polingu da nastavi
            if (odgovor.contains("Čeka se rezultat duela")) {
                return@LaunchedEffect
            }

            // Ako je stigao finalni odgovor, navigiramo na Kraj duela
            // NAPOMENA: Vaš originalni kod je ponovo zvao fetchCekanjeRezultata pre navigacije,
            // što možda nije neophodno ako backend šalje finalni rezultat.
            // Ostavljam logiku da se koristi odgovor za navigaciju direktno.

            navController.navigate(Destinacije.Kraj_duela.ruta+"/$sifra")
        }

    }
}