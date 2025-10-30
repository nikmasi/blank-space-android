package com.example.blankspace.screens.dodavanje

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.components.OutlinedTextFieldInput
import com.example.blankspace.viewModels.DodavanjeViewModel
import kotlinx.coroutines.delay
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)
private val LightBackground = Color(0xFFF7F7F7)


@Composable
fun PesmaPodaciD(navController: NavController, viewModel: DodavanjeViewModel, zanr: String, izvodjac: String){
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        PesmaPodaciD_mainCard(
            navController = navController,
            viewModel = viewModel,
            zanr = zanr,
            izvodjac = izvodjac,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun PesmaPodaciD_mainCard(navController: NavController,viewModel: DodavanjeViewModel,zanr:String,izvodjac:String, modifier: Modifier){
    val uiState by viewModel.uiStateDodajZanr.collectAsState()
    val context = LocalContext.current
    var selectedMp3Uri by remember { mutableStateOf<Uri?>(null) }

    var naziv_pesme by remember { mutableStateOf("") }
    var nepoznati_stihovi by remember { mutableStateOf("") }
    var poznati_stihovi by remember { mutableStateOf("") }
    var nivo by remember { mutableStateOf("normal") }

    LaunchedEffect(key1 = true) {
        snapshotFlow { uiState.dodajZanr }
            .collect { response ->
                response?.let {
                    Toast.makeText(context, it.odgovor, Toast.LENGTH_LONG).show()
                    if (it.odgovor.contains("Uspešno")) {
                        delay(1000)
                        navController.navigate(Destinacije.PocetnaAdmin.ruta) {
                            popUpTo(Destinacije.PocetnaAdmin.ruta) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                    viewModel.resetDodajZanr()
                }
            }
    }

    Spacer(modifier = Modifier.height(28.dp))

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.80f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { SongDetailsHeader(zanr, izvodjac) }

            item {
                SongDetailsInputs(
                    nazivPesme = naziv_pesme,
                    nepoznatiStihovi = nepoznati_stihovi,
                    poznatiStihovi = poznati_stihovi,
                    onNazivPesmeChange = { naziv_pesme = it },
                    onNepoznatiStihoviChange = { nepoznati_stihovi = it },
                    onPoznatiStihoviChange = { poznati_stihovi = it }
                )
            }

            item { DifficultyLevelSelectorStyled(nivo = nivo, onNivoChange = { nivo = it }) }

            item {
                FilePickerStyled(selectedMp3Uri) { uri ->
                    selectedMp3Uri = uri
                }
            }


            item { Spacer(modifier = Modifier.height(8.dp)) }

            item {

                val AccentPink = Color(0xFFEC8FB7)

                var pressed by remember { mutableStateOf(false) }
                val elevation = if (pressed) 2.dp else 8.dp

                Button(
                    onClick = {
                        if (naziv_pesme.isBlank() || nepoznati_stihovi.isBlank() || poznati_stihovi.isBlank() || nivo.isBlank() || selectedMp3Uri == null) {
                            Toast.makeText(context, "Morate uneti sve podatke i odabrati MP3 fajl!", Toast.LENGTH_LONG).show()
                            return@Button
                        }

                        pressed = true

                        selectedMp3Uri?.let { uri ->
                            val inputStream = context.contentResolver.openInputStream(uri)
                            val requestBody = inputStream?.readBytes()?.let { bytes ->
                                bytes.toRequestBody("audio/mpeg".toMediaType())
                            }


                            if (requestBody != null) {
                                viewModel.dodajZanrSaFajlom(
                                    zanr = zanr,
                                    izvodjac = izvodjac,
                                    nazivPesme = naziv_pesme,
                                    nepoznatiStihovi = nepoznati_stihovi,
                                    poznatiStihovi = poznati_stihovi,
                                    nivo = nivo,
                                    audioFile = requestBody
                                )
                            } else {
                                Toast.makeText(context, "Neuspešno čitanje MP3 fajla.", Toast.LENGTH_SHORT).show()
                                println("Neuspešno otvaranje MP3 fajla.")
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentPink,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(elevation, RoundedCornerShape(16.dp))
                ) {
                    Text(
                        text = "Dodaj pesmu",
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
            item { Spacer(modifier = Modifier.height(16.dp)) }
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
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
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
}

@Composable
fun FilePickerStyled(selectedMp3Uri: Uri?, onFileSelected: (Uri) -> Unit) {
    val context = LocalContext.current
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                val type = context.contentResolver.getType(uri)
                if (type == "audio/mpeg" || uri.toString().endsWith(".mp3")) {
                    onFileSelected(uri)
                } else {
                    Toast.makeText(context, "Molimo odaberite MP3 fajl (audio/mpeg).", Toast.LENGTH_SHORT).show()
                }
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Audio fajl (.mp3)",
            color = PrimaryDark,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Button(
            onClick = { filePickerLauncher.launch("audio/mpeg") },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(48.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryDark.copy(alpha = 0.9f),
                contentColor = Color.White
            )
        ) {
            Text("Izaberite MP3 fajl", fontWeight = FontWeight.Bold)
        }

        if (selectedMp3Uri != null) {
            Text(
                text = "Izabran: ...${selectedMp3Uri.path?.takeLast(30)}",
                style = MaterialTheme.typography.bodySmall,
                color = PrimaryDark.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        // Originalni poziv komponente FilePicker bi išao ovde:
        // FilePicker { uri -> selectedMp3Uri = uri }
        // implementirano  sa Button/Launcher logikom.
    }
}

@Composable
fun SongDetailsHeader(zanr: String, izvodjac: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Dodavanje Pesme",
            color = PrimaryDark,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Žanr: $zanr | Izvođač: $izvodjac",
            style = MaterialTheme.typography.titleSmall,
            color = AccentPink,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun DifficultyLevelSelectorStyled(nivo: String, onNivoChange: (String) -> Unit) {
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Nivo težine",
            color = PrimaryDark,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DifficultyRadioButtonStyled("easy", nivo, onNivoChange)
            DifficultyRadioButtonStyled("normal", nivo, onNivoChange)
            DifficultyRadioButtonStyled("hard", nivo, onNivoChange)
        }
    }
}

@Composable
fun DifficultyRadioButtonStyled(value: String, selectedValue: String, onValueChange: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onValueChange(value) }
    ) {
        RadioButton(
            selected = selectedValue == value,
            onClick = { onValueChange(value) },
            colors = RadioButtonDefaults.colors(
                selectedColor = AccentPink,
                unselectedColor = PrimaryDark.copy(alpha = 0.5f)
            )
        )
        Text(
            value.capitalize(),
            color = PrimaryDark,
            fontWeight = if (selectedValue == value) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun FilePicker(onFileSelected: (Uri) -> Unit) {
    val context = LocalContext.current
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                val type = context.contentResolver.getType(uri)
                if (type == "audio/mpeg" || uri.toString().endsWith(".mp3")) {
                    onFileSelected(uri)
                } else {
                    println("Selected file is not an MP3.")
                }
            }
        }
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Button(
            onClick = { filePickerLauncher.launch("audio/*") },
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
