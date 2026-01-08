package com.example.blankspace.screens.igra_offline.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.igra_offline.models.Tezina
import com.example.blankspace.screens.igra_sam.LevelButton
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.modifiers.columnMainStyle
import com.example.blankspace.ui.modifiers.mainCardStyle
import  com.example.blankspace.ui.theme.*


@Composable
fun Nivo_igra_offline(navController: NavController){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Nivo_igra_offline_mainCard(
            modifier = Modifier.align(Alignment.Center),
            onLevelSelected = { nivo ->
                navController.navigate("${Destinacije.Zanr_igra_offline.ruta}/$nivo")
            }
        )
    }
}

@Composable
fun Nivo_igra_offline_mainCard(modifier: Modifier = Modifier,onLevelSelected: (String) -> Unit) {
    Surface(
        color = CardContainerColor,
        modifier = modifier.mainCardStyle(),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.columnMainStyle(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            // Nivo header
            NivoIgreHeader()

            // Nivo body
            Column(
                verticalArrangement = Arrangement.spacedBy(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {

                Tezina.sviNivoi.forEach { nivo ->
                    LevelButton(text = nivo.naziv, color = nivo.boja) {
                        onLevelSelected(nivo.ruta)
                    }
                }
            }
        }
    }
}

@Composable
fun NivoIgreHeader() {
    Text(
        text = "Izaberite težinu",
        fontSize = 28.sp,
        fontWeight = FontWeight.ExtraBold,
        color = PrimaryDark
    )
}