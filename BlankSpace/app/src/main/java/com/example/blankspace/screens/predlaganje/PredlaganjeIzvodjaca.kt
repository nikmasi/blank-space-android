package com.example.blankspace.screens.predlaganje

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.blankspace.ui.components.OutlinedTextFieldInput
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.PredlaganjeIzvodjacaViewModel
import com.example.blankspace.viewModels.UiStateL
import com.example.blankspace.viewModels.UiStatePI
import com.example.blankspace.viewModels.UiStateZ
import com.example.blankspace.viewModels.ZanrViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

// Boje za usklađivanje
private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)
private val TextSecondary = Color(0xFF5A5A5A)
private val LightBackground = Color(0xFFF7F7F7)

@Composable
fun PredlaganjeIzvodjaca(navController: NavController, viewModelLogin: LoginViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        PredlaganjeIzvodjaca_mainCard(
            navController = navController,
            viewModelLogin = viewModelLogin,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun PredlaganjeIzvodjaca_mainCard(navController: NavController, viewModelLogin: LoginViewModel, modifier: Modifier) {
    val viewModel: ZanrViewModel = hiltViewModel()
    val viewModelPredlaganje: PredlaganjeIzvodjacaViewModel = hiltViewModel()

    val uiStatePredlaganjeIzvodjaca by viewModelPredlaganje.uiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val uiStateLogin by viewModelLogin.uiState.collectAsState()

    val context = LocalContext.current
    var selectedZanrId by remember { mutableStateOf("") }
    var izvodjac by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.fetchCategories()
    }

    HandlePredlaganjeIzvodjacaResponse(uiStatePredlaganjeIzvodjaca, context, navController, viewModelPredlaganje)
    Spacer(modifier = Modifier.height(30.dp))
    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.70f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Predloži Izvođača",
                    color = PrimaryDark,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Unesite ime izvođača i odaberite žanr.",
                    color = PrimaryDark.copy(alpha = 0.8f),
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
                HeadlineText("Ime izvođača")
                ImeIzvodjacaInputStyled(value = izvodjac, onValueChange = { izvodjac = it })
                Spacer(modifier = Modifier.height(20.dp))

                HeadlineText("Odaberite žanr")
                Spacer(modifier = Modifier.height(8.dp))

                ZanrSelectionListStyled(
                    uiState = uiState,
                    selectedZanrId = selectedZanrId,
                    onZanrSelected = { selectedZanrId = it }
                )
            }
            Spacer(modifier = Modifier.height(14.dp))

            PredlaganjeIzvodjacaButtonFull(
                selectedZanrId = selectedZanrId,
                izvodjac = izvodjac,
                uiStateLogin = uiStateLogin,
                viewModelPredlaganje = viewModelPredlaganje,
                context = context
            )
        }
    }
}


@Composable
fun HeadlineText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = PrimaryDark,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
    )
}

@Composable
fun ZanrSelectionListStyled(
    uiState: UiStateZ,
    selectedZanrId: String,
    onZanrSelected: (String) -> Unit
) {
    if (uiState.isRefreshing) {
        CircularProgressIndicator(color = AccentPink, modifier = Modifier)
    } else {
        if (uiState.error != null) {
            Text(text = "Greška: ${uiState.error}", color = Color.Red)
        } else {
            Box(
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .border(1.dp, PrimaryDark.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                    .background(Color.White, RoundedCornerShape(12.dp))
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    items(uiState.zanrovi) { zanr ->
                        val isSelected = selectedZanrId == zanr.id.toString()

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(if (isSelected) AccentPink.copy(alpha = 0.1f) else Color.Transparent)
                                .clickable { onZanrSelected(zanr.id.toString()) }
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { onZanrSelected(zanr.id.toString()) },
                                colors = RadioButtonDefaults.colors(selectedColor = AccentPink, unselectedColor = PrimaryDark)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = zanr.naziv,
                                color = if (isSelected) AccentPink else PrimaryDark,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HandlePredlaganjeIzvodjacaResponse(
    uiStatePredlaganjeIzvodjaca: UiStatePI,
    context: Context,
    navController: NavController,
    viewModelPredlaganje: PredlaganjeIzvodjacaViewModel
) {
    LaunchedEffect(uiStatePredlaganjeIzvodjaca.predlaganjeIzvodjaca?.odgovor) {
        val odgovor = uiStatePredlaganjeIzvodjaca.predlaganjeIzvodjaca?.odgovor
        if (!odgovor.isNullOrEmpty()) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, odgovor, Toast.LENGTH_LONG).show()
            }
            if (odgovor.contains("U bazi vec postoji izvodjac")) {
                return@LaunchedEffect
            }
            //viewModelPredlaganje.resetState()
            delay(1500)
            navController.popBackStack()
        }
    }
}

@Composable
fun ImeIzvodjacaInputStyled(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Unesite ime") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AccentPink,
            unfocusedBorderColor = PrimaryDark.copy(alpha = 0.5f),
            cursorColor = AccentPink
        ),
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
fun PredlaganjeIzvodjacaButtonFull(
    selectedZanrId: String,
    izvodjac: String,
    uiStateLogin: UiStateL,
    viewModelPredlaganje: PredlaganjeIzvodjacaViewModel,
    context: Context
) {
    var pressed by remember { mutableStateOf(false) }
    val elevation = if (pressed) 2.dp else 8.dp

    Button(
        onClick = {
            if (izvodjac.isBlank()) {
                Toast.makeText(context, "Molimo unesite ime izvođača.", Toast.LENGTH_SHORT).show()
                return@Button
            }
            if (selectedZanrId.isEmpty()) {
                Toast.makeText(context, "Molimo odaberite žanr.", Toast.LENGTH_SHORT).show()
                return@Button
            }

            pressed = true // Simulacija pritiska
            uiStateLogin.login?.korisnicko_ime?.let {
                viewModelPredlaganje.fetchPredlaganjeIzvodjaca(it, izvodjac, selectedZanrId)
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = AccentPink,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        //enabled = !izvodjac.isBlank() && selectedZanrId.isNotEmpty(),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(elevation, RoundedCornerShape(16.dp))
    ) {
        Text(
            text = "Predloži Izvođača",
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