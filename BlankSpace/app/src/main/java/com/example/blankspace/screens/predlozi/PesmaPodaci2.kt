package com.example.blankspace.screens.dodavanje

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.ui.components.OutlinedTextFieldInput
import com.example.blankspace.ui.components.SmallButton
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.theme.TEXT_COLOR
import com.example.blankspace.viewModels.DodavanjeViewModel
import com.example.blankspace.viewModels.PredloziViewModel
import kotlinx.coroutines.delay
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

@Composable
fun PesmaPodaci2(navController: NavController,viewModel: PredloziViewModel,zanr:String,izvodjac:String,pesma:String){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))
        PesmaPodaci2_mainCard(navController,viewModel,zanr,izvodjac,pesma)
    }
}

@Composable
fun PesmaPodaci2_mainCard(navController: NavController, viewModel: PredloziViewModel,zanr:String,izvodjac:String,pesma:String) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    var selectedIzvodjac by remember { mutableStateOf("") }
    var selectedMp3Uri by remember { mutableStateOf<Uri?>(null) }
    val uiStatePredlog by viewModel.uiStatePredlog.collectAsState()

    LaunchedEffect(key1 = true) {
        snapshotFlow { uiStatePredlog.predlog }
            .collect { response ->
                response?.let {
                    Toast.makeText(context, it.odgovor, Toast.LENGTH_SHORT).show()
                    delay(3000)
                    navController.navigate(Destinacije.PocetnaAdmin.ruta) {
                        popUpTo(0) // Ovo čisti ceo stack
                        launchSingleTop = true
                    }
                    viewModel.resetDodajZanr()
                }
            }
    }

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).fillMaxHeight(0.7f),
        shape = RoundedCornerShape(60.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(22.dp))
            HeadlineText("Podaci o pesmi")

            var nepoznati_stihovi by remember { mutableStateOf("") }
            var poznati_stihovi by remember { mutableStateOf("") }
            var nivo by remember { mutableStateOf("") }

            OutlinedTextFieldInput(
                value = nepoznati_stihovi,
                onValueChange = { nepoznati_stihovi = it },
                label = "Nepoznati stihovi"
            )

            OutlinedTextFieldInput(
                value = poznati_stihovi,
                onValueChange = { poznati_stihovi = it },
                label = "Poznati stihovi"
            )

            Text(
                text = "Nivo",
                style = MaterialTheme.typography.bodyLarge,
                color = TEXT_COLOR
            )

            val selectedDifficulty = remember { mutableStateOf("easy") }

            // Stilizovani RadioButton u jednom redu sa boljim razmacima
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp), // Razmak između RadioButton-a
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                // Easy Option
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { selectedDifficulty.value = "easy" }
                ) {
                    RadioButton(
                        selected = selectedDifficulty.value == "easy",
                        onClick = { selectedDifficulty.value = "easy" },
                    )
                    Text("Easy", color = if (selectedDifficulty.value == "easy") Color.Black else Color.Gray)
                }

                // Normal Option
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { selectedDifficulty.value = "normal" }
                ) {
                    RadioButton(
                        selected = selectedDifficulty.value == "normal",
                        onClick = { selectedDifficulty.value = "normal" },
                    )
                    Text("Normal", color = if (selectedDifficulty.value == "normal") Color.Black else Color.Gray)
                }

                // Hard Option
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { selectedDifficulty.value = "hard" }
                ) {
                    RadioButton(
                        selected = selectedDifficulty.value == "hard",
                        onClick = { selectedDifficulty.value = "hard" },
                    )
                    Text("Hard", color = if (selectedDifficulty.value == "hard") Color.Black else Color.Gray)
                }
            }

            Text(
                text = "Dodaj zvuk",
                style = MaterialTheme.typography.bodyLarge,
                color = TEXT_COLOR
            )

            FilePicker { uri ->
                selectedMp3Uri =uri
            }

            Spacer(modifier = Modifier.height(22.dp))

            SmallButton(onClick = {
                selectedMp3Uri?.let { uri ->
                    val inputStream = context.contentResolver.openInputStream(uri)
                    val requestBody = inputStream?.readBytes()?.let { bytes ->
                        bytes.toRequestBody("audio/mpeg".toMediaType())
                    }


                    if (requestBody != null) {

                        viewModel.dodajZanrSaFajlom(
                            zanr = zanr,
                            izvodjac = izvodjac,
                            nazivPesme = pesma,
                            nepoznatiStihovi = nepoznati_stihovi,
                            poznatiStihovi = poznati_stihovi,
                            nivo = selectedDifficulty.toString(),
                            audioFile = requestBody
                        )
                    } else {
                        println("Neuspešno otvaranje MP3 fajla.")
                    }
                }
            }, text = "Dodaj pesmu", style = MaterialTheme.typography.bodyMedium)
        }
    }
}