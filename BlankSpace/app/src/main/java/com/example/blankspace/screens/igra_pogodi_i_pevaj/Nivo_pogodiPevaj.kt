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
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blankspace.ui.components.SmallButton
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.igra_offline.NivoIgreHeader

@Composable
fun Nivo_pogodiPevaj(navController: NavController){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))
        Nivo_pogodiPevaj_mainCard(navController)
    }
}

@Composable
fun Nivo_pogodiPevaj_mainCard(navController: NavController) {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxWidth()
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
            NivoIgreHeader()
            Spacer(modifier = Modifier.height(42.dp))
            NivoPogodiPevajButtons(navController)
        }
    }
}

@Composable
fun NivoPogodiPevajButtons(navController: NavController) {
    SmallButton(
        onClick = {
            navController.navigate(Destinacije.Zanr_pogodiPevaj.ruta + "/easy")
        },
        text = "Easy",
        style = MaterialTheme.typography.bodyMedium
    )

    Spacer(modifier = Modifier.height(22.dp))

    SmallButton(
        onClick = {
            navController.navigate(Destinacije.Zanr_pogodiPevaj.ruta + "/normal")
        },
        text = "Normal",
        style = MaterialTheme.typography.bodyMedium
    )

    Spacer(modifier = Modifier.height(22.dp))

    SmallButton(
        onClick = {
            navController.navigate(Destinacije.Zanr_pogodiPevaj.ruta + "/hard")
        },
        text = "Hard",
        style = MaterialTheme.typography.bodyMedium
    )
}