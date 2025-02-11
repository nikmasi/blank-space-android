package com.example.blankspace.screens.dodavanje

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
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
import com.example.blankspace.ui.components.BodyLargeText
import com.example.blankspace.viewModels.DodavanjeViewModel
import com.example.blankspace.viewModels.PredloziViewModel

@Composable
fun PesmaPodaci(navController: NavController,viewModel: PredloziViewModel){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))
        PesmaPodaci_mainCard(navController,viewModel)
    }
}

@Composable
fun PesmaPodaci_mainCard(navController: NavController, viewModel: PredloziViewModel) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    var selectedIzvodjac by remember { mutableStateOf("") }

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight(0.7f),
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
            HeadlineText("Podaci o pesmi")

            var naziv_pesme by remember { mutableStateOf("") }
            var nepoznati_stihovi by remember { mutableStateOf("") }
            var poznati_stihovi by remember { mutableStateOf("") }
            var nivo by remember { mutableStateOf("") }

            OutlinedTextFieldInput(
                value = naziv_pesme,
                onValueChange = { naziv_pesme = it },
                label = "Naziv pesme"
            )

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

            BodyLargeText("Nivo")

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


            BodyLargeText("Dodaj zvuk")

            val filePickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent(),
                onResult = { uri: Uri? ->
                    if (uri != null) {
                        // Ovdje se može obraditi odabrani fajl
                        val filePath = getFilePath(context, uri)

                        // Proveravamo da li je fajl MP3
                        if (filePath?.endsWith(".mp3") == true) {
                            // Učitaj MP3 fajl
                            println("MP3 file selected: $filePath")
                        } else {
                            println("Selected file is not an MP3 file.")
                        }
                    }
                }
            )

            // Sekcija sa stilizovanim dugmetom za izbor fajla
            Column(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = {
                        // Otvorite dijalog za odabir fajla
                        filePickerLauncher.launch("audio/mp3")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFADD8E6))
                ) {
                    Text("Choose MP3 File", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            SmallButton(onClick = {
                //navController.navigate(Destinacije.PesmaPodaciD.ruta)
            }, text = "Dodaj pesmu", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

// Funkcija koja konvertuje Uri u putanju fajla, van Composable-a
fun getFilePath(context: Context, uri: Uri): String? {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.let {
        if (it.moveToFirst()) {
            val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            return it.getString(index)
        }
    }
    return null
}