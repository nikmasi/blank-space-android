package com.example.blankspace.screens.profil_rang_pravila

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
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

import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2

@Composable
fun PravilaIgre(navController: NavController){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        // Pozadinska karta
        BgCard2()
        Spacer(Modifier.padding(top=22.dp))
        var currentSection by remember { mutableStateOf(1) }
        val totalSections = 4  // Broj sekcija
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .fillMaxHeight(0.8f),
            shape = RoundedCornerShape(60.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility(
                    visible = currentSection == 1,
                    enter = fadeIn(tween(500)) + slideInVertically(tween(500)),
                    exit = fadeOut(tween(500)) + slideOutVertically(tween(500))
                ) {
                    Section1()
                }


                AnimatedVisibility(
                    visible = currentSection == 2,
                    enter = fadeIn(tween(500)) + slideInVertically(tween(500)),
                    exit = fadeOut(tween(500)) + slideOutVertically(tween(500))
                ) {
                    Section2()
                }

                AnimatedVisibility(
                    visible = currentSection == 3,
                    enter = fadeIn(tween(500)) + slideInVertically(tween(500)),
                    exit = fadeOut(tween(500)) + slideOutVertically(tween(500))
                ) {
                    Section3()
                }

                AnimatedVisibility(
                    visible = currentSection == 4,
                    enter = fadeIn(tween(500)) + slideInVertically(tween(500)),
                    exit = fadeOut(tween(500)) + slideOutVertically(tween(500))
                ) {
                    Section4()
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Dugme za prelazak na sledeću sekciju
                Button(
                    onClick = {
                        // Povećajte currentSection ako nije poslednja sekcija
                        if (currentSection < totalSections) {
                            currentSection++
                        }
                        else{
                            navController.navigate(Destinacije.Login.ruta)
                        }

                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF69B4),
                        contentColor = Color.White
                    )
                ) {
                    Text("Dalje")
                }
            }
        }
    }
}

@Composable
fun Section1() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier = Modifier.background(color = LIGTH_BLUE)) {
            Text(text = " 1. Opis ", style = MaterialTheme.typography.bodyMedium, color = TEXT_COLOR, modifier = Modifier.fillMaxWidth())
        }
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        Text("Ova igra je stvorena za sve ljubitelje muzike koji žele da testiraju svoje znanje o tekstovima pesama različitih izvođača i žanrova, dok se istovremeno takmiče sa svojim prijateljima."
            , color = TEXT_COLOR)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Vaš zadatak je da dopunite zadati tekst pesme, pokazavši koliko dobro poznajete omiljene hitove. Pridružite se zabavi i izazovite svoje prijatelje u ovoj uzbudljivoj muzičkoj avanturi!"
            , color = TEXT_COLOR)
        Spacer(modifier = Modifier.height(8.dp))
        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Composable
fun Section2() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier = Modifier.background(color = LIGTH_BLUE)) {
            Text(text = " 2. Mogućnosti ", style = MaterialTheme.typography.bodyMedium, color = TEXT_COLOR, modifier = Modifier.fillMaxWidth())
        }
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        Text("Igraj sam ili u dvoje (duel): Izazovite sebe ili se takmičite protiv prijatelja u uzbudljivom duelu.", color = TEXT_COLOR)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Odaberi jedan od zadatih žanrova: Birajte među raznovrsnim muzičkim žanrovima prema vašim preferencijama.", color = TEXT_COLOR)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Odaberi težinu:", color = TEXT_COLOR)
        Column {
            Text(" - Lakši nivo: Sadrži reči iz refrena.", color = TEXT_COLOR)
            Text(" - Srednji i teži nivoi: Obuhvataju manje poznate delove pesama.", color = TEXT_COLOR)
        }
        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Composable
fun Section3() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier = Modifier.background(color = LIGTH_BLUE)) {
            Text(" 3. Tok igre", style = MaterialTheme.typography.bodyLarge, color = TEXT_COLOR, modifier = Modifier.fillMaxWidth())
        }
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        Text("Tokom igre, na ekranu se pojavljuje jedan ili više stihova nasumično odabrane pesme, a isti stihovi se i emituju.", color = TEXT_COLOR)
        Spacer(modifier = Modifier.height(4.dp))
        Text("1. Na ekranu se prikazuju prazne linije gde treba upisati nedostajući tekst.", color = TEXT_COLOR)
        Text("2. Igrači prikupljaju poene za svaki tačno popunjen stih.", color = TEXT_COLOR)
        Text("3. Na kraju, korisnici se rangiraju na rang listi na osnovu osvojenih bodova u duelima.", color = TEXT_COLOR)
        Spacer(modifier = Modifier.height(4.dp))
        Text("Uronite u svet muzike, testirajte svoje znanje i zabavite se uz ovu dinamičnu igru!", color = TEXT_COLOR)
        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Composable
fun Section4() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier = Modifier.background(color = LIGTH_BLUE)) {
            Text(" 4. Sakupljaj poene", style = MaterialTheme.typography.bodyMedium, color = TEXT_COLOR, modifier = Modifier.fillMaxWidth())
        }
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        Text("Sakupljaj poene i pređi u sledeću kategoriju.", color = TEXT_COLOR)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Kategorije:", color = TEXT_COLOR)
        Spacer(modifier = Modifier.height(8.dp))
        // Kategorije tabele
        Column {
            TableRow("Brucoš", "Student", "Master",Color(0xFFF0DAE7))
            TableRow("niža kategorija", "srednja kategorija", "viša kategorija",Color(0xFFD0E4F1))
            TableRow("-", "mogućnost predlaganja novih izvođača", "mogućnost predlaganja novih izvođača i novih pesama",Color(0xFFF0DAE7))
            TableRow("-", "50 poena", "100 poena",Color(0xFFD0E4F1))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Korisnik mora biti registrovan!", color = TEXT_COLOR)
    }
}

@Composable
fun TableRow(col1: String, col2: String, col3: String,color:Color) {
    //Color(0xFFF0DAE7)
    Row(modifier = Modifier.fillMaxWidth().background(color = color), horizontalArrangement = Arrangement.SpaceEvenly) {
        Text(text = col1, modifier = Modifier.weight(1f), textAlign = TextAlign.Center, color = TEXT_COLOR)
        Text(text = col2, modifier = Modifier.weight(1f), textAlign = TextAlign.Center, color = TEXT_COLOR)
        Text(text = col3, modifier = Modifier.weight(1f), textAlign = TextAlign.Center, color = TEXT_COLOR)
    }
    Divider(modifier = Modifier.padding(vertical = 2.dp))
}



