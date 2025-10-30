package com.example.blankspace.screens.igra_pogodi_i_pevaj

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
import com.example.blankspace.data.retrofit.models.Zanr
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.viewModels.IgraSamViewModel
import com.example.blankspace.viewModels.UiStateZ
import com.example.blankspace.viewModels.ZanrViewModel

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)
private val TimeWarningColor = Color(0xFFD32F2F)
private val LightBackground = Color(0xFFFFFFFF)

@Composable
fun Zanr_PogodiPevaj(navController: NavController,selectedNivo:String,viewModelIgraSam:IgraSamViewModel){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Zanr_pogodiPevaj_mainCardStyled(navController,selectedNivo,viewModelIgraSam, modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun Zanr_pogodiPevaj_mainCardStyled(navController: NavController,selectedNivo: String,viewModelIgraSam:IgraSamViewModel, modifier: Modifier = Modifier) {
    val selectedNivoValue = selectedNivo.replace("[", "").replace("]", "").trim()
    val context = LocalContext.current

    val viewModel: ZanrViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    var selectedZanrovi by remember { mutableStateOf(setOf<Zanr>()) }

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.7f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ZanrIgreHeaderStyled()
            LoadingOrErrorStateStyled(uiState)

            if (!uiState.isRefreshing && uiState.error == null) {
                Column(modifier = Modifier.weight(1f).padding(vertical = 16.dp)) {
                    ZanroviCheckboxListStyled(
                        zanrovi = uiState.zanrovi,
                        selectedZanrovi = selectedZanrovi,
                        onZanrSelected = { zanr, isChecked ->
                            selectedZanrovi = if (isChecked) {
                                selectedZanrovi + zanr
                            } else {
                                selectedZanrovi - zanr
                            }
                        }
                    )
                }
            }

            Button(
                onClick = {
                    if (selectedZanrovi.isNotEmpty()) {
                        val zanroviIds = selectedZanrovi.joinToString(",") { it.id.toString() }
                        viewModelIgraSam.postaviListu(emptyList())
                        navController.navigate(Destinacije.Igra_pogodiPevaj.ruta + "/$zanroviIds/$selectedNivoValue/0/0")
                    } else {
                        Toast.makeText(context, "Izaberite bar jedan žanr", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentPink,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp)
                    .shadow(8.dp, RoundedCornerShape(16.dp), spotColor = AccentPink.copy(alpha = 0.5f))
            ) {
                Text("Dalje", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ZanrIgreHeaderStyled() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Izaberi Žanrove",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = PrimaryDark
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Dozvoljeno je više izbora.",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = PrimaryDark.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun LoadingOrErrorStateStyled(uiState: UiStateZ) {
    when {
        uiState.isRefreshing -> {
            CircularProgressIndicator(color = AccentPink)
        }
        uiState.error != null -> {
            Text(text = "Greška pri učitavanju žanrova: ${uiState.error}", color = TimeWarningColor, modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun ZanroviCheckboxListStyled(
    zanrovi: List<Zanr>,
    selectedZanrovi: Set<Zanr>,
    onZanrSelected: (Zanr, Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, PrimaryDark.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
            .background(LightBackground, RoundedCornerShape(12.dp))
            .padding(vertical = 4.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            items(zanrovi) { zanr ->
                val isSelected = selectedZanrovi.contains(zanr)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (isSelected) AccentPink.copy(alpha = 0.1f) else Color.Transparent)
                        .clickable { onZanrSelected(zanr, !isSelected) }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Checkbox(
                        checked = isSelected,
                        onCheckedChange = { isChecked -> onZanrSelected(zanr, isChecked) },
                        colors = CheckboxDefaults.colors(
                            checkedColor = AccentPink,
                            uncheckedColor = PrimaryDark.copy(alpha = 0.7f),
                            checkmarkColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = zanr.naziv,
                        fontSize = 17.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        color = PrimaryDark
                    )
                }
                if (zanrovi.lastOrNull() != zanr) {
                    Divider(color = PrimaryDark.copy(alpha = 0.05f), thickness = 1.dp)
                }
            }
        }
    }
}