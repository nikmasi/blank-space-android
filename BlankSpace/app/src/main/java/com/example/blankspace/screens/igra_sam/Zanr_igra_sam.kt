package com.example.blankspace.screens.igra_sam

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
private val LightBackground = Color(0xFFF7F7F7)

@Composable
fun Zanr_igra_sam(navController: NavController, selectedNivo: String, viewModelIgraSam: IgraSamViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        Zanr_igra_sam_mainCard(
            navController = navController,
            selectedNivo = selectedNivo,
            viewModelIgraSam = viewModelIgraSam,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun Zanr_igra_sam_mainCard(navController: NavController, selectedNivo: String, viewModelIgraSam: IgraSamViewModel, modifier: Modifier) {
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
                .padding(horizontal = 32.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            ZanrIgreHeaderStyled()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 16.dp)
            ) {
                LoadingOrErrorState(uiState)

                if (!uiState.isRefreshing && uiState.error == null) {
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

            ZanrIgraSamButton(
                selectedZanrovi = selectedZanrovi,
                selectedNivo = selectedNivo,
                viewModelIgraSam = viewModelIgraSam,
                navController = navController,
                context = context
            )
        }
    }
}


@Composable
fun ZanrIgreHeaderStyled() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Izaberite žanr(ove)",
            color = PrimaryDark,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Možete odabrati jedan ili više muzičkih žanrova za igru.",
            color = PrimaryDark.copy(alpha = 0.8f),
            fontSize = 16.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun LoadingOrErrorState(uiState: UiStateZ) {
    when {
        uiState.isRefreshing -> {
            CircularProgressIndicator(color = AccentPink, modifier = Modifier)
        }
        uiState.error != null -> {
            Text(text = "Greška: ${uiState.error}", color = Color.Red, modifier = Modifier)
        }
        uiState.zanrovi.isEmpty() -> {
            Text(
                text = "Nema dostupnih žanrova.",
                color = PrimaryDark.copy(alpha = 0.8f),
                modifier = Modifier
            )
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
                        .clickable { onZanrSelected(zanr, !isSelected) } // Toggle on click
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Checkbox(
                        checked = isSelected,
                        onCheckedChange = { isChecked -> onZanrSelected(zanr, isChecked) },
                        colors = CheckboxDefaults.colors(
                            checkedColor = AccentPink,
                            uncheckedColor = PrimaryDark.copy(alpha = 0.7f)
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = zanr.naziv,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        color = PrimaryDark
                    )
                }
                Divider(color = PrimaryDark.copy(alpha = 0.05f), thickness = 1.dp)
            }
        }
    }
}

@Composable
fun ZanrIgraSamButton(
    selectedZanrovi: Set<Zanr>,
    selectedNivo: String,
    viewModelIgraSam: IgraSamViewModel,
    navController: NavController,
    context: Context
) {
    Button(
        onClick = {
            if (selectedZanrovi.isNotEmpty()) {
                val zanroviIds = selectedZanrovi.joinToString(",") { it.id.toString() }
                // Napomena: Proveriti da li je selectedNivo String ili List<String> pre slanja rute
                val nivoString = selectedNivo.replace("[", "").replace("]", "").replace(" ", "") // Čišćenje ako je prosleđen kao lista-string

                viewModelIgraSam.postaviListu(emptyList()) // Resetovanje liste za novu igru
                navController.navigate(Destinacije.Igra_sam.ruta + "/$zanroviIds/$nivoString/0/0")
            } else {
                Toast.makeText(context, "Morate izabrati bar jedan žanr", Toast.LENGTH_SHORT).show()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = AccentPink, contentColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        //enabled = selectedZanrovi.isNotEmpty()
    ) {
        Text("Dalje", fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}