package com.example.blankspace.screens.pocetne

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.ui.components.HeadlineText // Pretpostavljam da ste HeadlineText zamenili sa stilizovanim Text
import com.example.blankspace.ui.components.MyImage
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.LoginViewModel
import kotlinx.coroutines.delay

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)

@Composable
fun PocetnaAdmin(modifier: Modifier = Modifier, navController: NavController, viewModelLogin: LoginViewModel) {
    Box(modifier = modifier.fillMaxSize()) {
        BgCard2()

        // checkLoginState(viewModelLogin,navController)
        PocetnaAdmin_mainCard(navController, viewModelLogin, modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun PocetnaAdmin_mainCard(navController:NavController, viewModelLogin:LoginViewModel, modifier: Modifier) {
    val uiStateLogin by viewModelLogin.uiState.collectAsState()
    val ime by viewModelLogin.ime.collectAsState()

    LaunchedEffect(uiStateLogin.login?.odgovor) {
        val odgovor = uiStateLogin.login?.odgovor
        if (!odgovor.isNullOrEmpty() && odgovor.contains("Logout")){
            navController.navigate(Destinacije.Login.ruta)
        }
    }

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.9f)
            .shadow(16.dp, RoundedCornerShape(32.dp)),
        shape = RoundedCornerShape(32.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item { ime.ime?.let { AdminHeader(ime = it.ifBlank { "Administrator" }) } }
            /*
            item { MyImage(ContentScale.Fit, 8) }*/

            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    SectionTitle("Statistika")
                    Spacer(modifier = Modifier.height(8.dp))
                    AdminStatistikaButtons(navController)
                }
            }

            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    SectionTitle("Pregled Sadržaja")
                    Spacer(modifier = Modifier.height(8.dp))
                    AdminContentButtons(navController)
                }
            }

            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    SectionTitle("Dodavanje Sadržaja")
                    Spacer(modifier = Modifier.height(8.dp))
                    AdminActionButtons(navController)
                }
            }

            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    SectionTitle("Uklanjanje Sadržaja i Korisnika")
                    Spacer(modifier = Modifier.height(8.dp))
                    AdminRemovalButtons(navController)
                }
            }

            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    SectionTitle("Pregled Predloga")
                    Spacer(modifier = Modifier.height(8.dp))
                    AdminSuggestionButtons(navController)
                }
            }

        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        color = PrimaryDark,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    Divider(color = PrimaryDark.copy(alpha = 0.2f), thickness = 1.dp, modifier = Modifier.fillMaxWidth(0.8f))
}

@Composable
fun AdminHeader(ime: String) {
    Text(
        text = "\uD83D\uDCBB Admin Panel",
        color = PrimaryDark,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(
        text = "Ulogovani ste kao ${ime}!",
        color = PrimaryDark.copy(alpha = 0.8f),
        fontSize = 16.sp,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
fun AdminActionButton(onClick: () -> Unit, text: String) {
    var pressed by remember { mutableStateOf(false) }
    val elevation = if (pressed) 2.dp else 6.dp

    Button(
        onClick = {
            pressed = true
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = AccentPink,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .height(48.dp)
            .shadow(elevation, RoundedCornerShape(12.dp))
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
    LaunchedEffect(pressed) {
        if (pressed) {
            // Kratak vizuelni feedbak
            delay(100)
            pressed = false
        }
    }
}

@Composable
fun AdminStatistikaButtons(navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AdminActionButton(onClick = { navController.navigate(Destinacije.AdminStatistika.ruta) }, text = "Statistika")
        //AdminActionButton(onClick = { navController.navigate(Destinacije.SadrzajKorisnici.ruta) }, text = "Pregled korisnika")
        //AdminActionButton(onClick = { navController.navigate(Destinacije.IzborZanra2.ruta) }, text = "Dodaj pesmu")
    }
}

@Composable
fun AdminContentButtons(navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AdminActionButton(onClick = { navController.navigate(Destinacije.SadrzajZanrova.ruta) }, text = "Pregled žanrova")
        AdminActionButton(onClick = { navController.navigate(Destinacije.SadrzajKorisnici.ruta) }, text = "Pregled korisnika")
        AdminActionButton(onClick = { navController.navigate(Destinacije.SadrzajIzvodjaci.ruta) }, text = "Pregled izvodjaca")
        AdminActionButton(onClick = { navController.navigate(Destinacije.SadrzajPesme.ruta) }, text = "Pregled pesama")
        AdminActionButton(onClick = { navController.navigate(Destinacije.SadrzajStihovi.ruta) }, text = "Pregled stihova")
        AdminActionButton(onClick = { navController.navigate(Destinacije.SadrzajSoba.ruta) }, text = "Pregled soba")
    }
}

@Composable
fun AdminActionButtons(navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AdminActionButton(onClick = { navController.navigate(Destinacije.NazivZanra.ruta) }, text = "Dodaj žanr")
        AdminActionButton(onClick = { navController.navigate(Destinacije.IzborZanra.ruta) }, text = "Dodaj izvođača")
        AdminActionButton(onClick = { navController.navigate(Destinacije.IzborZanra2.ruta) }, text = "Dodaj pesmu")
    }
}

@Composable
fun AdminRemovalButtons(navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AdminActionButton(onClick = { navController.navigate(Destinacije.UklanjanjeZanra.ruta) }, text = "Ukloni žanrove")
        AdminActionButton(onClick = { navController.navigate(Destinacije.IzborZanraUklanjanjeIzvodjaca.ruta) }, text = "Ukloni izvođače")
        AdminActionButton(onClick = { navController.navigate(Destinacije.IzborZanraUklanjanjePesme.ruta) }, text = "Ukloni pesme")
        AdminActionButton(onClick = { navController.navigate(Destinacije.UklanjanjeKorisnika.ruta) }, text = "Ukloni korisnike")
    }
}

@Composable
fun AdminSuggestionButtons(navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AdminActionButton(onClick = { navController.navigate(Destinacije.PredloziIzvodjaca.ruta) }, text = "Predlozi izvođača")
        AdminActionButton(onClick = { navController.navigate(Destinacije.PredloziPesme.ruta) }, text = "Predlozi pesama")
    }
}

@Composable
fun LogoutButton(onClick: () -> Unit) {
    Text(
        text = "Odjavi se",
        color = PrimaryDark.copy(alpha = 0.8f),
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
    )
}