package com.example.blankspace.screens.pocetne

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.ui.theme.*

@Composable
fun PocetnaAdmin(modifier: Modifier = Modifier, navController: NavController, viewModelLogin: LoginViewModel) {
    Box(modifier = modifier.fillMaxSize()) {
        BgCard2()

        // checkLoginState(viewModelLogin,navController)
        PocetnaAdmin_mainCard(navController, viewModelLogin, modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun PocetnaAdmin_mainCard(navController: NavController, viewModelLogin: LoginViewModel, modifier: Modifier) {
    val uiStateLogin by viewModelLogin.uiState.collectAsState()
    val ime by viewModelLogin.ime.collectAsState()

    LaunchedEffect(uiStateLogin.login?.odgovor) {
        val odgovor = uiStateLogin.login?.odgovor
        if (!odgovor.isNullOrEmpty() && odgovor.contains("Logout")) {
            navController.navigate(Destinacije.Login.ruta)
        }
    }

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.92f)
            .fillMaxHeight(0.88f)
            .shadow(24.dp, RoundedCornerShape(32.dp)),
        shape = RoundedCornerShape(32.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(20.dp)
        ) {
            item {
                AdminHeader(
                    onLogout = { /* Pozovi logout funkciju iz viewModel-a */ }
                )
            }

            item { AdminSectionHeader("Statistika") }
            item {
                AdminGridRow {
                    AdminCompactButton(
                        text = "Statistika",
                        onClick = { navController.navigate(Destinacije.AdminStatistika.ruta) }
                    )
                }
            }

            item { AdminSectionHeader("Pregled Sadržaja") }
            item {
                AdminGrid(
                    listOf(
                        "Žanrovi" to { navController.navigate(Destinacije.SadrzajZanrova.ruta) },
                        "Korisnici" to { navController.navigate(Destinacije.SadrzajKorisnici.ruta) },
                        "Izvođači" to { navController.navigate(Destinacije.SadrzajIzvodjaci.ruta) },
                        "Pesme" to { navController.navigate(Destinacije.SadrzajPesme.ruta) },
                        "Stihovi" to { navController.navigate(Destinacije.SadrzajStihovi.ruta) },
                        "Sobe" to { navController.navigate(Destinacije.SadrzajSoba.ruta) }
                    )
                )
            }

            item { AdminSectionHeader("Upravljanje bazom") }
            item {
                AdminGrid(
                    listOf(
                        "Dodaj Žanr" to { navController.navigate(Destinacije.NazivZanra.ruta) },
                        "Ukloni Žanr" to { navController.navigate(Destinacije.UklanjanjeZanra.ruta) },
                        "Dodaj Izvođača" to { navController.navigate(Destinacije.IzborZanra.ruta) },
                        "Ukloni Izvođača" to { navController.navigate(Destinacije.IzborZanraUklanjanjeIzvodjaca.ruta) },
                        "Dodaj Pesmu" to { navController.navigate(Destinacije.IzborZanra2.ruta) },
                        "Ukloni Pesmu" to { navController.navigate(Destinacije.IzborZanraUklanjanjePesme.ruta) }
                    )
                )
            }

            item { AdminSectionHeader("Predlozi Korisnika") }
            item {
                AdminGrid(
                    listOf(
                        "Izvođači" to { navController.navigate(Destinacije.PredloziIzvodjaca.ruta) },
                        "Pesme" to { navController.navigate(Destinacije.PredloziPesme.ruta) }
                    )
                )
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}

@Composable
fun AdminSectionHeader(title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 12.dp)
    ) {
        Text(
            text = title.uppercase(),
            color = PrimaryDark.copy(alpha = 0.6f),
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
        )
        Divider(
            color = PrimaryDark.copy(alpha = 0.1f),
            thickness = 1.dp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun AdminGrid(items: List<Pair<String, () -> Unit>>) {
    val chunks = items.chunked(2)
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        chunks.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rowItems.forEach { item ->
                    Box(modifier = Modifier.weight(1f)) {
                        AdminCompactButton(text = item.first, onClick = item.second)
                    }
                }
                if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun AdminGridRow(content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        content = content
    )
}

@Composable
fun AdminCompactButton(
    text: String,
    icon: String? = null,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        border = BorderStroke(1.dp, PrimaryDark.copy(alpha = 0.05f))
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Text(text = icon, fontSize = 18.sp)
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                color = PrimaryDark,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
    }
}

@Composable
fun AdminHeader(onLogout: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Admin Panel",
                color = AccentPink,
                fontWeight = FontWeight.Black,
                fontSize = 28.sp
            )
        }

        IconButton(
            onClick = onLogout,
            modifier = Modifier.background(PrimaryDark.copy(alpha = 0.05f), RoundedCornerShape(12.dp))
        ) {
            Text("X")
        }
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