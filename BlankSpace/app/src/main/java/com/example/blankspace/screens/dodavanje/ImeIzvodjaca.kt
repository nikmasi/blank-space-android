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
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.dodavanje.DodavanjeButton
import com.example.blankspace.screens.dodavanje.DodavanjeHeader
import com.example.blankspace.screens.dodavanje.DodavanjeInputField
import com.example.blankspace.viewModels.DodavanjeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.blankspace.ui.theme.*

@Composable
fun ImeIzvodjaca(viewModel: DodavanjeViewModel, onProvera: (String) -> Unit) {
    val context = LocalContext.current
    val uiState by viewModel.uiStateProveraPostojanja.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()

        ImeIzvodjaca_mainCard(
            modifier = Modifier.align(Alignment.Center),
            onProvera  = onProvera,
            onClick = { izvodjac ->
                if (izvodjac.isBlank()) {
                    Toast.makeText(context, "Unesite ime izvođača.", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.proveraPostojanja(izvodjac, "izvodjac")
                }
            },
            odgovor = uiState.proveraDaLiPostoji?.odgovor
        )
    }
}

@Composable
fun ImeIzvodjaca_mainCard(
    modifier: Modifier, onProvera: (String) -> Unit, onClick: (String) -> Unit, odgovor:String?
) {
    var izvodjac by remember { mutableStateOf("") }

    odgovor?.let {
        HandleProveraPostojanjaResponse(odgovor = it, onProvera = { onProvera(izvodjac) })
    }

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
                text1 = "Novi Izvođač",
                text2 = "Unesite ime izvođača za žanr koji ste odabrali."
            )

            DodavanjeInputField(
                value = izvodjac, onValueChange = { izvodjac = it }, label = "Ime izvođača"
            )

            Spacer(modifier = Modifier.height(8.dp))

            DodavanjeButton(onClick = { onClick(izvodjac) }, text = "Proveri i nastavi")
        }
    }
}

@Composable
fun HandleProveraPostojanjaResponse(odgovor: String, onProvera: () -> Unit) {
    val context = LocalContext.current

    LaunchedEffect(odgovor) {
        if (!odgovor.isNullOrEmpty() && odgovor!="") {
            if (odgovor.contains("U bazi vec postoji izvodjac sa imenom:")) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, odgovor, Toast.LENGTH_SHORT).show()
                }
                return@LaunchedEffect
            }
            onProvera()
        }
    }
}