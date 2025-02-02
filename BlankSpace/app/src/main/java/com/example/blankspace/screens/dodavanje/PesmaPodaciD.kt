package com.example.blankspace.screens.dodavanje

import android.net.Uri
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
import androidx.navigation.NavController
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.ui.components.OutlinedTextFieldInput
import com.example.blankspace.ui.components.SmallButton
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.theme.TEXT_COLOR
import com.example.blankspace.viewModels.DodavanjeViewModel

@Composable
fun PesmaPodaciD(navController: NavController,viewModel: DodavanjeViewModel,zanr:String,izvodjac:String){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))
        PesmaPodaciD_mainCard(navController,viewModel,zanr,izvodjac)
    }
}

@Composable
fun PesmaPodaciD_mainCard(navController: NavController,viewModel: DodavanjeViewModel,zanr:String,izvodjac:String){
    val uiState by viewModel.uiState.collectAsState()

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

            SongDetailsInputs(
                nazivPesme = naziv_pesme,
                nepoznatiStihovi = nepoznati_stihovi,
                poznatiStihovi = poznati_stihovi,
                onNazivPesmeChange = { naziv_pesme = it },
                onNepoznatiStihoviChange = { nepoznati_stihovi = it },
                onPoznatiStihoviChange = { poznati_stihovi = it }
            )

            DifficultyLevelSelector(nivo = nivo, onNivoChange = { nivo = it })

            Text(
                text = "Dodaj zvuk",
                style = MaterialTheme.typography.bodyLarge,
                color = TEXT_COLOR
            )

            FilePicker()
            Spacer(modifier = Modifier.height(22.dp))

            SmallButton(onClick = {
                viewModel.dodajZanr(zanr,izvodjac,naziv_pesme,nepoznati_stihovi,poznati_stihovi,nivo,"")
            }, text = "Dodaj pesmu", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun SongDetailsInputs(
    nazivPesme: String,
    nepoznatiStihovi: String,
    poznatiStihovi: String,
    onNazivPesmeChange: (String) -> Unit,
    onNepoznatiStihoviChange: (String) -> Unit,
    onPoznatiStihoviChange: (String) -> Unit
) {
    OutlinedTextFieldInput(
        value = nazivPesme,
        onValueChange = onNazivPesmeChange,
        label = "Naziv pesme"
    )

    OutlinedTextFieldInput(
        value = nepoznatiStihovi,
        onValueChange = onNepoznatiStihoviChange,
        label = "Nepoznati stihovi"
    )

    OutlinedTextFieldInput(
        value = poznatiStihovi,
        onValueChange = onPoznatiStihoviChange,
        label = "Poznati stihovi"
    )
}

@Composable
fun DifficultyLevelSelector(nivo: String, onNivoChange: (String) -> Unit) {
    Text(
        text = "Nivo",
        style = MaterialTheme.typography.bodyLarge,
        color = TEXT_COLOR
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        DifficultyRadioButton("easy", nivo, onNivoChange)
        DifficultyRadioButton("normal", nivo, onNivoChange)
        DifficultyRadioButton("hard", nivo, onNivoChange)
    }
}

@Composable
fun DifficultyRadioButton(value: String, selectedValue: String, onValueChange: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onValueChange(value) }
    ) {
        RadioButton(
            selected = selectedValue == value,
            onClick = { onValueChange(value) }
        )
        Text(value.capitalize(), color = if (selectedValue == value) Color.Black else Color.Gray)
    }
}

@Composable
fun FilePicker() {
    val context = LocalContext.current
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                val filePath = getFilePath(context, uri)
                if (filePath?.endsWith(".mp3") == true) {
                    println("MP3 file selected: $filePath")
                } else {
                    println("Selected file is not an MP3 file.")
                }
            }
        }
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Button(
            onClick = { filePickerLauncher.launch("audio/mp3") },
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
}