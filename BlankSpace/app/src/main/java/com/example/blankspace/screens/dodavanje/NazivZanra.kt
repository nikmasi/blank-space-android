package com.example.blankspace.screens.predlaganje

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.dodavanje.DodavanjeButton
import com.example.blankspace.screens.dodavanje.DodavanjeHeader
import com.example.blankspace.screens.dodavanje.DodavanjeInputField
import com.example.blankspace.viewModels.DodavanjeViewModel
import com.example.blankspace.viewModels.ProveraPostojanja
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.blankspace.ui.theme.*

@Composable
fun NazivZanra(navController: NavController, viewModel: DodavanjeViewModel){
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()

        NazivZanra_mainCard(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun NazivZanra_mainCard(navController: NavController, viewModel: DodavanjeViewModel, modifier: Modifier) {
    val context = LocalContext.current
    val uiState by viewModel.uiStateProveraPostojanja.collectAsState()
    var zanr by remember { mutableStateOf("") }

    HandleProveraPostojanjaResponse(uiState, context, navController, zanr)

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.85f)
            .fillMaxHeight(0.4f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            DodavanjeHeader(
                text1 = "Dodavanje Žanra",
                text2 = "Unesite naziv novog muzičkog žanra."
            )

            DodavanjeInputField(
                value = zanr,
                onValueChange = { zanr = it },
                label = "Naziv žanra"
            )

            Spacer(modifier = Modifier.height(8.dp))

            DodavanjeButton(onClick = {
                if (zanr.isBlank()) {
                    Toast.makeText(context, "Unesite naziv žanra.", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.proveraPostojanja(zanr, "zanr")
                }
            }, text = "Dodaj žanr")
        }
    }
}

@Composable
fun HandleProveraPostojanjaResponse(
    uiState: ProveraPostojanja,
    context: android.content.Context,
    navController: NavController,
    zanr: String
) {
    LaunchedEffect(uiState.proveraDaLiPostoji?.odgovor) {
        val odgovor = uiState.proveraDaLiPostoji?.odgovor
        if (!odgovor.isNullOrEmpty()) {
            if (odgovor.contains("U bazi vec postoji zanr sa nazivom:")) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, odgovor, Toast.LENGTH_SHORT).show()
                }
                return@LaunchedEffect
            }
            uiState.proveraDaLiPostoji?.odgovor = ""
            navController.navigate("${Destinacije.ImeIzvodjaca.ruta}/$zanr")
        }
    }
}