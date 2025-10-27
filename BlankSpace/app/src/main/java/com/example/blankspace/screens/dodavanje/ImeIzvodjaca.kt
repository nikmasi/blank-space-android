package com.example.blankspace.screens.predlaganje

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.viewModels.DodavanjeViewModel
import com.example.blankspace.viewModels.ProveraPostojanja
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)

@Composable
fun ImeIzvodjaca(navController: NavController, viewModel: DodavanjeViewModel, zanr: String){
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()

        ImeIzvodjaca_mainCard(
            navController = navController,
            viewModel = viewModel,
            zanr = zanr,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ImeIzvodjaca_mainCard(navController: NavController, viewModel: DodavanjeViewModel, zanr: String, modifier: Modifier) {
    val context = LocalContext.current
    val uiState by viewModel.uiStateProveraPostojanja.collectAsState()
    var izvodjac by remember { mutableStateOf("") }

    HandleProveraPostojanjaResponse(uiState, context, navController, zanr, izvodjac)

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.85f)
            .fillMaxHeight(0.4f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            IzvodjacHeader()

            IzvodjacInputField(
                value = izvodjac,
                onValueChange = { izvodjac = it },
                label = "Ime izvođača"
            )

            // Smanjen razmak
            Spacer(modifier = Modifier.height(8.dp))

            IzvodjacButton(onClick = {
                if (izvodjac.isBlank()) {
                    Toast.makeText(context, "Unesite ime izvođača.", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.proveraPostojanja(izvodjac, "izvodjac")
                }
            }, text = "Proveri i nastavi")
        }
    }
}

@Composable
fun HandleProveraPostojanjaResponse(
    uiState: ProveraPostojanja,
    context: android.content.Context,
    navController: NavController,
    zanr: String,
    izvodjac: String
) {
    LaunchedEffect(uiState.proveraDaLiPostoji?.odgovor) {
        val odgovor = uiState.proveraDaLiPostoji?.odgovor
        if (!odgovor.isNullOrEmpty() && odgovor!="") {
            if (odgovor.contains("U bazi vec postoji izvodjac sa imenom:")) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, odgovor, Toast.LENGTH_SHORT).show()
                }
                return@LaunchedEffect
            }
            navController.navigate("${Destinacije.PesmaPodaciD.ruta}/$zanr/$izvodjac")
        }
    }
}

@Composable
fun IzvodjacHeader() {
    Text(
        text = "Novi Izvođač",
        color = PrimaryDark,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(
        text = "Unesite ime izvođača za žanr koji ste odabrali.",
        style = MaterialTheme.typography.bodyMedium,
        color = PrimaryDark.copy(alpha = 0.8f),
        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
fun IzvodjacInputField(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = PrimaryDark) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AccentPink,
            unfocusedBorderColor = PrimaryDark.copy(alpha = 0.5f),
            cursorColor = AccentPink,
            focusedTextColor = PrimaryDark,
            unfocusedTextColor = PrimaryDark
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun IzvodjacButton(onClick: () -> Unit, text: String) {
    var pressed by remember { mutableStateOf(false) }
    val elevation = if (pressed) 2.dp else 8.dp

    Button(
        onClick = {
            pressed = true
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = AccentPink,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(elevation, RoundedCornerShape(16.dp))
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
    LaunchedEffect(pressed) {
        if (pressed) {
            delay(100)
            pressed = false
        }
    }
}