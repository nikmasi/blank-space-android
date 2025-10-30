package com.example.blankspace.screens.igra_pogodi_i_pevaj

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.igra_sam.LevelButton

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)

@Composable
fun Nivo_pogodiPevaj(navController: NavController){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Nivo_pogodiPevaj_mainCardStyled(navController, modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun Nivo_pogodiPevaj_mainCardStyled(navController: NavController, modifier: Modifier = Modifier) {
    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.65f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            NivoIgreHeaderPogodiPevaj()
            Spacer(modifier = Modifier.height(42.dp))
            NivoPogodiPevajButtonsStyled(navController)
        }
    }
}

@Composable
fun NivoIgreHeaderPogodiPevaj() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Izaberi težinu",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = PrimaryDark
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Pogodi i Pevaj",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = AccentPink
        )
    }
}

@Composable
fun NivoPogodiPevajButtonsStyled(navController: NavController) {
    LevelButton(text = "Easy", color = Color(0xFF5AB1BB)) {
        navController.navigate(Destinacije.Zanr_pogodiPevaj.ruta + "/easy")
    }
    Spacer(modifier = Modifier.height(20.dp))
    LevelButton(text = "Normal", color = AccentPink) {
        navController.navigate(Destinacije.Zanr_pogodiPevaj.ruta + "/normal")
    }
    Spacer(modifier = Modifier.height(20.dp))
    LevelButton(text = "Hard", color = PrimaryDark) {
        navController.navigate(Destinacije.Zanr_pogodiPevaj.ruta + "/hard")
    }
}