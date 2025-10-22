package com.example.blankspace.screens.pocetne

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.ui.components.MyImage
import com.example.blankspace.ui.components.SmallButton
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.LoginViewModel

@Composable
fun PocetnaAdmin(modifier: Modifier = Modifier,navController: NavController,viewModelLogin:LoginViewModel) {
    Box(modifier = modifier.fillMaxSize()) {
        BgCard2()

        checkLoginState(viewModelLogin,navController)
        PocetnaAdmin_mainCard(navController,viewModelLogin)
    }
}

@Composable
fun PocetnaAdmin_mainCard(navController:NavController,viewModelLogin:LoginViewModel) {
    val uiStateLogin by viewModelLogin.uiState.collectAsState()
    val ime by viewModelLogin.ime.collectAsState()

    LaunchedEffect(uiStateLogin.login?.odgovor) {
        val odgovor = uiStateLogin.login?.odgovor
        if (!odgovor.isNullOrEmpty()) {
            if (odgovor.contains("Logout")){
                navController.navigate(Destinacije.Login.ruta)
            }
        }
    }

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight(0.88f),
        shape = RoundedCornerShape(60.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MyImage(ContentScale.Fit,8)

            HeadlineText("Ulogovani ste kao ${ime.ime}!")

            Spacer(modifier = Modifier.height(22.dp))

            AdminActionButtons(navController)
            Spacer(modifier = Modifier.height(12.dp))

            AdminRemovalButtons(navController)
            Spacer(modifier = Modifier.height(12.dp))

            AdminSuggestionButtons(navController)
        }
    }
}

@Composable
fun AdminActionButtons(navController: NavController) {
    val buttonStyle = MaterialTheme.typography.bodyMedium

    SmallButton(onClick = {
        navController.navigate(Destinacije.NazivZanra.ruta)
    }, text = "Dodaj žanr", style = buttonStyle)

    SmallButton(onClick = {
        navController.navigate(Destinacije.IzborZanra.ruta)
    }, text = "Dodaj izvođača", style = buttonStyle)

    SmallButton(onClick = {
        navController.navigate(Destinacije.IzborZanra2.ruta)
    }, text = "Dodaj pesmu", style = buttonStyle)
}

@Composable
fun AdminRemovalButtons(navController: NavController) {
    val buttonStyle = MaterialTheme.typography.bodyMedium

    SmallButton(onClick = {
        navController.navigate(Destinacije.UklanjanjeZanra.ruta)
    }, text = "Ukloni žanrove", style = buttonStyle)

    SmallButton(onClick = {
        navController.navigate(Destinacije.IzborZanraUklanjanjeIzvodjaca.ruta)
    }, text = "Ukloni izvođače", style = buttonStyle)

    SmallButton(onClick = {
        navController.navigate(Destinacije.IzborZanraUklanjanjePesme.ruta)
    }, text = "Ukloni pesme", style = buttonStyle)

    SmallButton(onClick = {
        navController.navigate(Destinacije.UklanjanjeKorisnika.ruta)
    }, text = "Ukloni korisnike", style = buttonStyle)
}

@Composable
fun AdminSuggestionButtons(navController: NavController) {
    val buttonStyle = MaterialTheme.typography.bodyMedium

    SmallButton(onClick = {
        navController.navigate(Destinacije.PredloziIzvodjaca.ruta)
    }, text = "Predlozi izvođača", style = buttonStyle)

    SmallButton(onClick = {
        navController.navigate(Destinacije.PredloziPesme.ruta)
    }, text = "Predloži pesmu", style = buttonStyle)
}