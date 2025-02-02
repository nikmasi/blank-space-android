package com.example.blankspace.screens.predlaganje

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.blankspace.ui.components.OutlinedTextFieldInput
import com.example.blankspace.ui.components.SmallButton
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.ui.theme.TEXT_COLOR
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.PredlaganjeIzvodjacaViewModel
import com.example.blankspace.viewModels.UiStateL
import com.example.blankspace.viewModels.UiStatePI
import com.example.blankspace.viewModels.ZanrViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun PredlaganjeIzvodjaca(navController: NavController,viewModelLogin:LoginViewModel){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))
        PredlaganjeIzvodjaca_mainCard(navController,viewModelLogin)
    }
}

@Composable
fun PredlaganjeIzvodjaca_mainCard(navController: NavController,viewModelLogin:LoginViewModel) {
    val viewModel: ZanrViewModel = hiltViewModel()
    val viewModelPredlaganje: PredlaganjeIzvodjacaViewModel= hiltViewModel()

    val uiStatePredlaganjeIzvodjaca by viewModelPredlaganje.uiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val uiStateLogin by viewModelLogin.uiState.collectAsState()

    val context = LocalContext.current
    var selectedZanr by remember { mutableStateOf("") }

    HandlePredlaganjeIzvodjacaResponse(uiStatePredlaganjeIzvodjaca, context, navController)

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
            HeadlineText("Ime izvođača")

            var izvodjac by remember { mutableStateOf("") }
            ImeIzvodjacaInput(value = izvodjac, onValueChange = {izvodjac=it})

            Spacer(modifier = Modifier.height(22.dp))
            HeadlineText("Naziv žanra ")

            if (uiState.isRefreshing) {
                CircularProgressIndicator()
            } else {
                if (uiState.error != null) {
                    Text(text = "Greška: ${uiState.error}", color = Color.Red)
                } else {
                    Box(modifier = Modifier.height(150.dp)) {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().padding(start = 53.dp),
                            contentPadding = PaddingValues(vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(0.dp)
                        ) {
                            items(uiState.zanrovi) { zanr ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    RadioButton(
                                        selected = selectedZanr == zanr.id.toString(),
                                        onClick = { selectedZanr = zanr.id.toString() }
                                    )
                                    Text(text = zanr.naziv, color = TEXT_COLOR)
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(22.dp))

            PredlaganjeIzvodjacaButton(selectedZanr,izvodjac,uiStateLogin,viewModelPredlaganje,context)
        }
    }
}

@Composable
fun HandlePredlaganjeIzvodjacaResponse(
    uiStatePredlaganjeIzvodjaca: UiStatePI,
    context:Context,
    navController: NavController
){
    LaunchedEffect(uiStatePredlaganjeIzvodjaca.predlaganjeIzvodjaca?.odgovor) {
        val odgovor = uiStatePredlaganjeIzvodjaca.predlaganjeIzvodjaca?.odgovor
        if (!odgovor.isNullOrEmpty()) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, odgovor, Toast.LENGTH_SHORT).show()
            }
            if (odgovor.contains("U bazi vec postoji izvodjac")) {
                return@LaunchedEffect
            }
            delay(3000)
            navController.popBackStack()
        }
    }
}

@Composable
fun ImeIzvodjacaInput(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextFieldInput(
        value = value,
        onValueChange = onValueChange,
        label = "Ime izvođača"
    )
}

@Composable
fun PredlaganjeIzvodjacaButton(
    selectedZanr: String,
    izvodjac: String,
    uiStateLogin: UiStateL,
    viewModelPredlaganje: PredlaganjeIzvodjacaViewModel,
    context: Context
) {
    SmallButton(onClick = {
        if (selectedZanr.isNotEmpty()) {
            uiStateLogin.login?.korisnicko_ime?.let {
                viewModelPredlaganje.fetchPredlaganjeIzvodjaca(it, izvodjac, selectedZanr)
            }
        } else {
            Toast.makeText(context, "Niste izabrali nijedan žanr", Toast.LENGTH_SHORT).show()
        }
    }, text = "Predloži", style = MaterialTheme.typography.bodyMedium)
}