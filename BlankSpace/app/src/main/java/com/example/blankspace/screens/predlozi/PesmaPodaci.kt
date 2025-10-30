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
import androidx.compose.material.icons.filled.MoreVert
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
fun PesmaPodaci(navController: NavController,viewModel: PredloziViewModel,zanr:String,izvodjac:String){
    Box(modifier = Modifier.fillMaxSize().padding(top= 52.dp +16.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))
        PesmaPodaci_mainCardStyled(navController,viewModel,zanr,izvodjac, Modifier.align(Alignment.Center))
    }
}

@Composable
fun PesmaPodaci_mainCardStyled(navController: NavController, viewModel: PredloziViewModel,zanr:String,izvodjac:String, modifier: Modifier) {
    val context = LocalContext.current
    val uiStatePredlog by viewModel.uiStatePredlog.collectAsState()

    var naziv_pesme by remember { mutableStateOf("") }
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
                    delay(500) // Kratko kašnjenje pre navigacije
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
                HeadlineTextStyled("Dodavanje Pesme")
                Spacer(modifier = Modifier.height(3.dp))
                Text("Žanr: $zanr | Izvođač: $izvodjac", color = PrimaryDark.copy(alpha = 0.7f))

                Spacer(modifier = Modifier.height(20.dp))

                StyledOutlinedTextFieldInput(
                    value = naziv_pesme,
                    onValueChange = { naziv_pesme = it },
                    label = "Naziv pesme"
                )
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
                Spacer(modifier = Modifier.height(12.dp))

                BodySectionTitle("Dodaj audio fajl")
                FilePickerStyled(fileName) { uri ->
                    selectedMp3Uri = uri
                    fileName = getFileName(context, uri) ?: "Fajl odabran"
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(14.dp))
                SmallButtonStyled(onClick = {
                    handleDodajPesmu(
                        context, viewModel, selectedMp3Uri, zanr, izvodjac,
                        naziv_pesme, nepoznati_stihovi, poznati_stihovi, selectedDifficulty.value
                    )
                }, text = "Dodaj pesmu")
            }
        }
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

private fun handleDodajPesmu(
    context: Context, viewModel: PredloziViewModel, selectedMp3Uri: Uri?,
    zanr: String, izvodjac: String, nazivPesme: String, nepoznatiStihovi: String,
    poznatiStihovi: String, nivo: String
) {
    if (selectedMp3Uri == null) {
        Toast.makeText(context, "Molimo odaberite MP3 fajl.", Toast.LENGTH_SHORT).show()
        return
    }

    if (nazivPesme.isBlank() || nepoznatiStihovi.isBlank() || poznatiStihovi.isBlank()) {
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


@Composable
fun HeadlineTextStyled(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge.copy(
            color = PrimaryDark,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp
        )
    )
}

@Composable
fun BodySectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge.copy(
            color = PrimaryDark,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        ),
        modifier = Modifier.padding(bottom = 8.dp, top = 16.dp)
    )
}

@Composable
fun StyledOutlinedTextFieldInput(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AccentPink,
            unfocusedBorderColor = InputBorderColor,
            focusedLabelColor = AccentPink,
            cursorColor = AccentPink,
            unfocusedLabelColor = InputBorderColor
        ),
        shape = RoundedCornerShape(12.dp),
        singleLine = true
    )
}

@Composable
fun DifficultySelection(selectedDifficulty: MutableState<String>) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        listOf("easy", "normal", "hard").forEach { level ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { selectedDifficulty.value = level }
            ) {
                RadioButton(
                    selected = selectedDifficulty.value == level,
                    onClick = { selectedDifficulty.value = level },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = AccentPink,
                        unselectedColor = InputBorderColor
                    )
                )
                Text(
                    level.replaceFirstChar { it.uppercase() },
                    color = if (selectedDifficulty.value == level) PrimaryDark else InputBorderColor,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun FilePickerStyled(fileName: String, onFileSelected: (Uri) -> Unit) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onFileSelected(it) }
    }

    Button(
        onClick = { launcher.launch("audio/mpeg") },
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryDark.copy(alpha = 0.8f),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.MoreVert, contentDescription = "Odaberi fajl")
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Odaberi MP3",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
    // Prikaz imena fajla
    Text(
        text = fileName,
        color = PrimaryDark.copy(alpha = 0.6f),
        fontSize = 12.sp,
        modifier = Modifier.padding(top = 4.dp)
    )
}

@Composable
fun SmallButtonStyled(onClick: () -> Unit, text: String) {
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