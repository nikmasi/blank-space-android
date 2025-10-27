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
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            ZanrHeader()

            ZanrInputField(
                value = zanr,
                onValueChange = { zanr = it },
                label = "Naziv žanra"
            )

            Spacer(modifier = Modifier.height(8.dp))

            ZanrButton(onClick = {
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

@Composable
fun ZanrHeader() {
    Text(
        text = "Dodavanje Žanra",
        color = PrimaryDark,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(
        text = "Unesite naziv novog muzičkog žanra.",
        style = MaterialTheme.typography.bodyMedium,
        color = PrimaryDark.copy(alpha = 0.8f),
        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
fun ZanrInputField(value: String, onValueChange: (String) -> Unit, label: String) {
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
fun ZanrButton(onClick: () -> Unit, text: String) {
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