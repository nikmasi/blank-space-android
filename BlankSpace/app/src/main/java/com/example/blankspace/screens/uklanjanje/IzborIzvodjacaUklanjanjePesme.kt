package com.example.blankspace.screens.uklanjanje


import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.ui.components.SmallButton
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.ui.theme.TEXT_COLOR
import com.example.blankspace.viewModels.UklanjanjeViewModel
import com.example.blankspace.viewModels.ZanrViewModel

@Composable
fun IzborIzvodjacaUklanjanjePesme(navController: NavController,viewModelUklanjanje: UklanjanjeViewModel){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))
        IzborIzvodjacaUklanjanjePesme_mainCard(navController,viewModelUklanjanje)
    }
}

@Composable
fun IzborIzvodjacaUklanjanjePesme_mainCard(navController: NavController,viewModelUklanjanje: UklanjanjeViewModel) {
    val context = LocalContext.current
    val viewModel: ZanrViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val uiStateUklanjanje by viewModelUklanjanje.uiStateIzvodjacaZanra.collectAsState()

    var selectedZanrovi by remember { mutableStateOf(-1) }
    var ime by remember { mutableStateOf("") }

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
            HeadlineText("Kojem žanru pripada")
            HeadlineText("pesma koju uklanjate?")

            Spacer(modifier = Modifier.height(42.dp))

            if (uiState.isRefreshing) {
                CircularProgressIndicator()
            } else {
                if (uiState.error != null) {
                    Text(text = "Greška: ${uiState.error}", color = Color.Red)
                } else {
                    uiStateUklanjanje.izvodjaci.forEach { izvodjaci ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth().padding(start = 53.dp)
                                .clickable {  }
                        ) {
                            RadioButton(
                                selected = selectedZanrovi == izvodjaci.id,
                                onClick = {
                                    selectedZanrovi = izvodjaci.id
                                    ime=izvodjaci.ime
                                }
                            )
                            Text(
                                text = izvodjaci.ime,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 8.dp),
                                color = TEXT_COLOR
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(42.dp))

            SmallButton(onClick = {
                if (selectedZanrovi!=-1) {
                    viewModelUklanjanje.postaviPesmeIzv(selectedZanrovi,ime)
                    viewModelUklanjanje.dohvatiPesmeIzvodjaca(selectedZanrovi,ime,-1)
                    navController.navigate(Destinacije.UklanjanjePesme.ruta)
                } else {
                    Toast.makeText(context, "Niste izabrali nijedan žanr", Toast.LENGTH_SHORT).show()
                }
            },text = "Dalje", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

