package com.example.blankspace.screens.predlaganje

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.ui.components.OutlinedTextFieldInput
import com.example.blankspace.ui.components.SmallButton
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.viewModels.DodavanjeViewModel
import com.example.blankspace.viewModels.ProveraPostojanja
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun NazivZanra(navController: NavController,viewModel: DodavanjeViewModel){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))
        NazivZanra_mainCard(navController,viewModel)
    }
}

@Composable
fun NazivZanra_mainCard(navController: NavController,viewModel: DodavanjeViewModel) {
    val context = LocalContext.current
    val uiState by viewModel.uiStateProveraPostojanja.collectAsState()
    var zanr by remember { mutableStateOf("") }

    HandleProveraPostojanjaResponse(uiState, context, navController, zanr)

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
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
            Spacer(modifier = Modifier.height(22.dp))
            HeadlineText("Naziv žanra")

            OutlinedTextFieldInput(
                value = zanr,
                onValueChange = { zanr = it },
                label = "Naziv žanra"
            )

            Spacer(modifier = Modifier.height(22.dp))

            SmallButton(onClick = {
                viewModel.proveraPostojanja(zanr,"zanr")
            },text = "Dodaj žanr", style = MaterialTheme.typography.bodyMedium)
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