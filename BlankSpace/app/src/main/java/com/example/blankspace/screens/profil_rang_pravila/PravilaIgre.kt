package com.example.blankspace.screens.profil_rang_pravila

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.blankspace.ui.theme.LIGTH_BLUE
import com.example.blankspace.ui.theme.TEXT_COLOR
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.shadow
import com.example.blankspace.screens.pocetne.cards.BgCard2

// Definicija palete usklađene sa PocetnaMainCard
val RULES_CARD_BG = Color.White // Bela pozadina glavne kartice
val RULES_ACCENT = Color(0xFFEC8FB7) // Roza akcent
val RULES_TEXT_DARK = Color(0xFF49006B) // Tamno ljubičasta

@Composable
fun PravilaIgre(navController: NavController){
    Box(modifier = Modifier.fillMaxSize()) { // Uklonjen top padding iz Box-a
        BgCard2() // Tamna/šarena pozadina ostaje

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 64.dp), // Veliki vertikalni padding
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(28.dp))
            // Glavna kartica sa senkom i velikom zaobljenošću
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f) // Zauzima skoro ceo preostali prostor
                    .shadow(16.dp, RoundedCornerShape(36.dp)),
                colors = CardDefaults.cardColors(containerColor = RULES_CARD_BG), // Čisto bela kartica
                shape = RoundedCornerShape(36.dp)
            ) {
                // Korišćenje LazyColumn da bi ceo tekst bio skrolujući
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        // 1. GLAVNI NASLOV
                        Text(
                            text = "Pravila Igre",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = RULES_TEXT_DARK,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )
                    }

                    // Sekcije unutar LazyColumn
                    item { Section1Content() }
                    item { Spacer(modifier = Modifier.height(32.dp)) }

                    item { Section2Content() }
                    item { Spacer(modifier = Modifier.height(32.dp)) }

                    item { Section3Content() }
                    item { Spacer(modifier = Modifier.height(32.dp)) }

                    item { Section4Content() }
                    item { Spacer(modifier = Modifier.height(40.dp)) }

                    // 2. DUGME ZA POVRATAK (na dnu skrolujuće liste)
                    item {
                        Button(
                            onClick = { navController.navigate(Destinacije.Login.ruta) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .shadow(8.dp, RoundedCornerShape(50)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = RULES_ACCENT,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = "Započni Igru",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

// --- SEKCIJA 1 (sada bez AnimatedVisibilityScope) ---
@Composable
fun Section1Content() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("1. Opis", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = RULES_TEXT_DARK)
        Divider(modifier = Modifier.padding(vertical = 12.dp), color = RULES_ACCENT.copy(alpha = 0.5f))

        Text(
            "Ova igra je stvorena za sve **ljubitelje muzike** koji žele da testiraju svoje znanje o tekstovima pesama različitih izvođača i žanrova, dok se istovremeno takmiče sa svojim prijateljima.",
            color = Color.DarkGray,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Vaš zadatak je da **dopunite zadati tekst pesme**, pokazavši koliko dobro poznajete omiljene hitove. Pridružite se zabavi i izazovite svoje prijatelje u ovoj uzbudljivoj muzičkoj avanturi!",
            color = Color.DarkGray,
            fontSize = 16.sp
        )
    }
}

// --- SEKCIJA 2 ---
@Composable
fun Section2Content() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("2. Mogućnosti", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = RULES_TEXT_DARK)
        Divider(modifier = Modifier.padding(vertical = 12.dp), color = RULES_ACCENT.copy(alpha = 0.5f))

        BulletPoint("Igraj **sam** ili u **dvoje (duel)**: Izazovite sebe ili se takmičite protiv prijatelja u uzbudljivom duelu.")
        BulletPoint("Odaberi jedan od zadatih **žanrova**: Birajte među raznovrsnim muzičkim žanrovima prema vašim preferencijama.")
        BulletPoint("Odaberi težinu:")

        Column(modifier = Modifier.padding(start = 16.dp, top = 4.dp)) {
            BulletPointSmall("Lakši nivo: Sadrži reči iz **refrena**.")
            BulletPointSmall("Srednji i teži nivoi: Obuhvataju **manje poznate delove** pesama.")
        }
    }
}

// --- SEKCIJA 3 ---
@Composable
fun Section3Content() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("3. Tok igre", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = RULES_TEXT_DARK)
        Divider(modifier = Modifier.padding(vertical = 12.dp), color = RULES_ACCENT.copy(alpha = 0.5f))

        Text(
            "Tokom igre, na ekranu se pojavljuje jedan ili više stihova nasumično odabrane pesme, a isti stihovi se i emituju.",
            color = Color.DarkGray,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(12.dp))

        BulletPoint("Na ekranu se prikazuju prazne linije gde treba upisati **nedostajući tekst**.")
        BulletPoint("Igrači prikupljaju **poene** za svaki tačno popunjen stih.")
        BulletPoint("Na kraju, korisnici se rangiraju na **rang listi** na osnovu osvojenih bodova u duelima.")

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Uronite u svet muzike, testirajte svoje znanje i zabavite se uz ovu dinamičnu igru!",
            color = Color.DarkGray,
            fontSize = 16.sp
        )
    }
}

// --- SEKCIJA 4 (SA TABELOM) ---
@Composable
fun Section4Content() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("4. Sakupljaj poene", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = RULES_TEXT_DARK)
        Divider(modifier = Modifier.padding(vertical = 12.dp), color = RULES_ACCENT.copy(alpha = 0.5f))

        Text("Sakupljaj poene i pređi u sledeću **kategoriju**.", color = Color.DarkGray, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))

        // MODERNA TABELA
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Zaglavlje Tabele
            TableRowModern("Kategorija", "Brucoš", "Student", "Master", RULES_ACCENT)

            // Redovi Podataka
            TableRowModern("Nivo", "Niža", "Srednja", "Viša", Color(0xFFF0DAE7))
            TableRowModern("Predlaganje", "-", "Izvođači", "Sve", RULES_CARD_BG)
            TableRowModern("Bonus poeni", "-", "50", "100", Color(0xFFF0DAE7))
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "Korisnik mora biti **registrovan** da bi se takmičio i skupljao poene!",
            color = RULES_TEXT_DARK,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

// --- Pomoćne Composable funkcije za čistiji kod ---

@Composable
fun BulletPoint(text: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text("• ", color = RULES_ACCENT, fontWeight = FontWeight.Bold)
        Text(text = text, color = Color.DarkGray, fontSize = 16.sp)
    }
}

@Composable
fun BulletPointSmall(text: String) {
    Row(modifier = Modifier.padding(vertical = 2.dp)) {
        Text("- ", color = RULES_ACCENT.copy(alpha = 0.8f), fontWeight = FontWeight.SemiBold)
        Text(text = text, color = Color.Gray, fontSize = 14.sp)
    }
}

@Composable
fun TableRowModern(
    col1: String,
    col2: String,
    col3: String,
    col4: String,
    bgColor: Color
) {
    val isHeader = bgColor == RULES_ACCENT

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = bgColor)
            .padding(vertical = if (isHeader) 12.dp else 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Kolona za Opis/Redni broj
        Text(
            text = col1,
            modifier = Modifier
                .weight(1.5f)
                .padding(start = 16.dp),
            textAlign = TextAlign.Start,
            color = if (isHeader) Color.White else RULES_TEXT_DARK.copy(alpha = 0.8f),
            fontWeight = if (isHeader) FontWeight.ExtraBold else FontWeight.Normal,
            fontSize = if (isHeader) 16.sp else 14.sp
        )
        // Kategorije
        Text(
            text = col2,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            color = if (isHeader) Color.White else RULES_TEXT_DARK,
            fontWeight = FontWeight.Bold,
            fontSize = if (isHeader) 16.sp else 14.sp
        )
        Text(
            text = col3,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            color = if (isHeader) Color.White else RULES_TEXT_DARK,
            fontWeight = FontWeight.Bold,
            fontSize = if (isHeader) 16.sp else 14.sp
        )
        Text(
            text = col4,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            color = if (isHeader) Color.White else RULES_TEXT_DARK,
            fontWeight = FontWeight.Bold,
            fontSize = if (isHeader) 16.sp else 14.sp
        )
    }
    // Samo linija razdvajanja za redove sa podacima
    if (!isHeader) {
        Divider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp)
    }
}