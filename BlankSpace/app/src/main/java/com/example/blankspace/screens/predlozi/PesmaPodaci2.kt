package com.example.blankspace.screens.dodavanje

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.PredloziViewModel
import kotlinx.coroutines.delay
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)
private val TextMain = PrimaryDark
private val TextAccent = AccentPink
private val InputBorderColor = PrimaryDark.copy(alpha = 0.5f)


@Composable
fun SmallButtonStyled2(onClick: () -> Unit, text: String) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = AccentPink,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .height(56.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp), spotColor = AccentPink.copy(alpha = 0.5f))
    ) {
        Text(text, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
    }
}

private fun getFileName(context: Context, uri: Uri): String? {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    return cursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        it.moveToFirst()
        it.getString(nameIndex)
    }
}

@Composable
fun PesmaPodaci2(navController: NavController,viewModel: PredloziViewModel,zanr:String,izvodjac:String,pesma:String){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))
        PesmaPodaci2_mainCardStyled(navController,viewModel,zanr,izvodjac,pesma, Modifier.align(Alignment.Center))
    }
}

@Composable
fun PesmaPodaci2_mainCardStyled(navController: NavController, viewModel: PredloziViewModel,zanr:String,izvodjac:String,pesma:String, modifier: Modifier) {
    val context = LocalContext.current
    val uiStatePredlog by viewModel.uiStatePredlog.collectAsState()

    var nepoznati_stihovi by remember { mutableStateOf("") }
    var poznati_stihovi by remember { mutableStateOf("") }
    val selectedDifficulty = remember { mutableStateOf("easy") }
    var selectedMp3Uri by remember { mutableStateOf<Uri?>(null) }
    var fileName by remember { mutableStateOf("Nije odabran MP3 fajl") }

    LaunchedEffect(key1 = true) {
        snapshotFlow { uiStatePredlog.predlog }
            .collect { response ->
                response?.let {
                    Toast.makeText(context, it.odgovor, Toast.LENGTH_LONG).show()
                    delay(500)
                    navController.navigate(Destinacije.PocetnaAdmin.ruta) {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                    viewModel.resetDodajZanr()
                }
            }
    }

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.8f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                HeadlineTextStyled("Podaci o Pesmi")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Žanr: $zanr | Izvođač: $izvodjac", color = PrimaryDark.copy(alpha = 0.7f), fontSize = 14.sp)
                Text("Pesma: ${pesma}", color = TextAccent, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(20.dp))

                StyledOutlinedTextFieldInput(
                    value = nepoznati_stihovi,
                    onValueChange = { nepoznati_stihovi = it },
                    label = "Nepoznati stihovi"
                )
                StyledOutlinedTextFieldInput(
                    value = poznati_stihovi,
                    onValueChange = { poznati_stihovi = it },
                    label = "Poznati stihovi"
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                BodySectionTitle("Nivo težine")
                DifficultySelection(selectedDifficulty)
                Spacer(modifier = Modifier.height(16.dp))

                BodySectionTitle("Dodaj audio fajl")
                FilePickerStyled(fileName) { uri ->
                    selectedMp3Uri = uri
                    fileName = getFileName(context, uri) ?: "Fajl odabran"
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(24.dp))
                SmallButtonStyled2(onClick = {
                    handleDodajPesmu2(
                        context, viewModel, selectedMp3Uri, zanr, izvodjac,
                        pesma, nepoznati_stihovi, poznati_stihovi, selectedDifficulty.value
                    )
                }, text = "Dodaj pesmu")
            }
        }
    }
}

private fun handleDodajPesmu2(
    context: Context, viewModel: PredloziViewModel, selectedMp3Uri: Uri?,
    zanr: String, izvodjac: String, nazivPesme: String, nepoznatiStihovi: String,
    poznatiStihovi: String, nivo: String
) {
    if (selectedMp3Uri == null) {
        Toast.makeText(context, "Molimo odaberite MP3 fajl.", Toast.LENGTH_SHORT).show()
        return
    }

    if (nepoznatiStihovi.isBlank() || poznatiStihovi.isBlank()) {
        Toast.makeText(context, "Sva tekstualna polja moraju biti popunjena.", Toast.LENGTH_SHORT).show()
        return
    }

    val inputStream = context.contentResolver.openInputStream(selectedMp3Uri)
    val requestBody = inputStream?.readBytes()?.let { bytes ->
        bytes.toRequestBody("audio/mpeg".toMediaType())
    }

    if (requestBody != null) {
        viewModel.dodajZanrSaFajlom(
            zanr = zanr,
            izvodjac = izvodjac,
            nazivPesme = nazivPesme,
            nepoznatiStihovi = nepoznatiStihovi,
            poznatiStihovi = poznatiStihovi,
            nivo = nivo,
            audioFile = requestBody
        )
    } else {
        Toast.makeText(context, "Neuspešno čitanje MP3 fajla.", Toast.LENGTH_SHORT).show()
    }
}