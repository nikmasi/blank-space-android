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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.IzvodjacZanrViewModel
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.PredlaganjePesmeViewModel
import com.example.blankspace.viewModels.UiStateIZ
import com.example.blankspace.viewModels.UiStateL
import com.example.blankspace.viewModels.UiStatePP
import com.example.blankspace.viewModels.UiStateZ
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)
private val LightBackground = Color(0xFFF7F7F7)

@Composable
fun PredlaganjePesme(navController: NavController, viewModelLogin: LoginViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        PredlaganjePesme_mainCard(
            navController = navController,
            viewModelLogin = viewModelLogin,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun PredlaganjePesme_mainCard(navController: NavController, viewModelLogin: LoginViewModel, modifier: Modifier) {
    val viewModel: IzvodjacZanrViewModel = hiltViewModel()
    val viewModelPredlaganje: PredlaganjePesmeViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsState()
    val uiStateLogin by viewModelLogin.uiState.collectAsState()
    val uiStatePredlaganjePesme by viewModelPredlaganje.uiState.collectAsState()

    val context = LocalContext.current
    var selectedZanrId by remember { mutableStateOf("") }
    var pesma by remember { mutableStateOf("") }

    var selectedIzvodjacName by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        //viewModel.fetchIzvodjaciZanr() // Pretpostavljamo da ovo puni listu izvođača i žanrova
    }

    LaunchedEffect(uiState.izvodjaci) {
        if (uiState.izvodjaci.isNotEmpty() && selectedIzvodjacName.isEmpty()) {
            selectedIzvodjacName = uiState.izvodjaci.first().ime
        }
    }

    HandlePredlaganjePesmeResponse(navController, context, viewModelPredlaganje, uiStatePredlaganjePesme)
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
                    text = "Predloži Pesmu",
                    color = PrimaryDark,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Unesite naziv pesme, izaberite izvođača i žanr.",
                    color = PrimaryDark.copy(alpha = 0.8f),
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(14.dp))
            }

            Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {

                HeadlineText("Naziv pesme")
                InputTextFieldStyled(
                    value = pesma,
                    onValueChange = { pesma = it },
                    label = "Unesite naziv"
                )
                Spacer(modifier = Modifier.height(10.dp))

                HeadlineTextPesme("Ime izvođača")
                IzvodjacDropdown(
                    uiState = uiState,
                    selectedOption = selectedIzvodjacName,
                    onOptionSelected = { selectedIzvodjacName = it }
                )
                Spacer(modifier = Modifier.height(10.dp))

                HeadlineText("Naziv žanra")
                Spacer(modifier = Modifier.height(8.dp))
                ZanrSelectionListStyledPesme(
                    uiState = uiState,
                    selectedZanrId = selectedZanrId,
                    onZanrSelected = { selectedZanrId = it }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            PredlaganjePesmeButtonFull(
                pesma = pesma,
                selectedZanrId = selectedZanrId,
                selectedIzvodjacName = selectedIzvodjacName,
                uiStateLogin = uiStateLogin,
                viewModelPredlaganje = viewModelPredlaganje,
                context = context
            )
        }
    }
}

@Composable
fun HeadlineTextPesme(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = PrimaryDark,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
    )
}

@Composable
fun InputTextFieldStyled(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
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
fun IzvodjacDropdown(
    uiState: UiStateIZ,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    if (uiState.isRefreshing) {
        CircularProgressIndicator(color = AccentPink)
        return
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            label = { Text("Odaberite izvođača") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Prikaži opcije",
                        tint = PrimaryDark
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AccentPink,
                unfocusedBorderColor = PrimaryDark.copy(alpha = 0.5f),
                cursorColor = AccentPink
            ),
            shape = RoundedCornerShape(12.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(0.85f)
        ) {
            uiState.izvodjaci.forEach { izvodjac ->
                DropdownMenuItem(
                    text = { Text(izvodjac.ime, color = PrimaryDark) },
                    onClick = {
                        onOptionSelected(izvodjac.ime)
                        expanded = false
                    },
                    modifier = Modifier.background(if (selectedOption == izvodjac.ime) AccentPink.copy(alpha = 0.1f) else Color.Transparent)
                )
            }
        }
    }
}


@Composable
fun ZanrSelectionListStyledPesme(
    uiState: UiStateIZ,
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
                    .height(140.dp)
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
fun HandlePredlaganjePesmeResponse(
    navController: NavController,
    context: Context,
    viewModelPredlaganje: PredlaganjePesmeViewModel,
    uiStatePredlaganjePesme: UiStatePP
){
    LaunchedEffect(uiStatePredlaganjePesme.predlaganjepesme?.odgovor) {
        val odgovor = uiStatePredlaganjePesme.predlaganjepesme?.odgovor
        if (!odgovor.isNullOrEmpty()) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, odgovor, Toast.LENGTH_LONG).show()
            }
            if (odgovor.contains("U bazi vec postoji pesma sa imenom")) {
                return@LaunchedEffect
            }
            //viewModelPredlaganje.resetState()
            delay(1500)
            navController.popBackStack()
        }
    }
}


@Composable
fun PredlaganjePesmeButtonFull(
    pesma: String,
    selectedZanrId: String,
    selectedIzvodjacName: String,
    uiStateLogin: UiStateL,
    viewModelPredlaganje: PredlaganjePesmeViewModel,
    context: Context
) {
    var pressed by remember { mutableStateOf(false) }
    val elevation = if (pressed) 2.dp else 8.dp

    val isEnabled = pesma.isNotBlank() && selectedZanrId.isNotEmpty() && selectedIzvodjacName.isNotBlank()

    Button(
        onClick = {
            if (!isEnabled) {
                Toast.makeText(context, "Popunite sva polja.", Toast.LENGTH_SHORT).show()
                return@Button
            }

            pressed = true
            uiStateLogin.login?.korisnicko_ime?.let {
                viewModelPredlaganje.fetchPredlaganjePesme(it, pesma, selectedIzvodjacName, selectedZanrId)
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = AccentPink,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        //enabled = isEnabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(elevation, RoundedCornerShape(16.dp))
    ) {
        Text(
            text = "Predloži Pesmu",
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