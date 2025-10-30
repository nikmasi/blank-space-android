package com.example.blankspace.screens.igra_pogodi_i_pevaj

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.blankspace.data.retrofit.BASE_URL
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.igra_sam.DisplayContentIgraSam
import com.example.blankspace.screens.igra_sam.LoadingStateIgraSam
import com.example.blankspace.viewModels.IgraSamLista
import com.example.blankspace.viewModels.IgraSamViewModel
import com.example.blankspace.viewModels.UiStateI
import kotlinx.coroutines.delay

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)
private val InfoBarColor = Color(0xFFE0BBE4)
private val TextAccent = AccentPink
private val TimeWarningColor = Color(0xFFD32F2F)

val LIGTH_BLUE = InfoBarColor
val TEXT_COLOR = PrimaryDark

@Composable
fun Igra_pogodiPevaj(navController: NavController, selectedZanrovi: String, selectedNivo: String,runda:Int,poeni:Int,viewModelIgraSam:IgraSamViewModel) {
    Box(modifier = Modifier.fillMaxSize().padding(top = 52.dp)) {
        BgCard2()

        val selectedNivoList = selectedNivo.split(",").map { it.trim() }
        val selectedZanroviList = selectedZanrovi.split(",").map { it.trim() }

        Igra_pogodiPevaj_mainCard(
            navController,
            selectedZanroviList,
            selectedNivoList,
            selectedZanrovi, // sZ
            selectedNivo, // sN
            runda,
            poeni,
            viewModelIgraSam,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Igra_pogodiPevaj_mainCard(
    navController: NavController,
    selectedZanrovi: List<String>,
    selectedNivo: List<String>,
    sZ: String,
    sN: String,
    runda: Int,
    poeni: Int,
    viewModel: IgraSamViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val igraSamLista by viewModel.IgraSamLista.collectAsState()

    val count = remember { mutableStateOf(0) }
    val isAudioP = remember { mutableStateOf(false) }
    var crta = remember { mutableStateOf("") }
    val odgovor = remember { mutableStateOf("") }
    val isListening = remember { mutableStateOf(false) }

    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }
    val recognitionListener = remember {
        object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() { isListening.value = false }
            override fun onError(error: Int) {
                isListening.value = false
                Log.e("SpeechRecognizer", "Error: $error")
                Toast.makeText(context, "Greška: $error", Toast.LENGTH_SHORT).show()
            }
            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) { odgovor.value = matches[0] }
                isListening.value = false
            }
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        }
    }

    LaunchedEffect(Unit) {
        speechRecognizer.setRecognitionListener(recognitionListener)
    }
    TimerEffectPogodiPevaj(count, navController, sZ, sN, poeni, uiState.igrasam?.runda ?: runda)
    FetchDataEffectPogodiPevaj(selectedZanrovi, selectedNivo, runda, poeni, viewModel, context, igraSamLista)

    LaunchedEffect(viewModel.uiState.value.igrasam?.crtice) {
        crta.value = viewModel.uiState.value.igrasam?.crtice ?: ""
    }

    LaunchedEffect(uiState.igrasam?.zvuk) {
        val zvukUrl = uiState.igrasam?.zvuk
        if (zvukUrl != null) {
            viewModel.stopAudio()
            viewModel.downloadAudio(zvukUrl, context)
            count.value = 0
            isAudioP.value = true
            odgovor.value = ""
        } else {
            isAudioP.value = false
        }
        crta.value = uiState.igrasam?.crtice ?: ""
    }

    val currentRunda = uiState.igrasam?.runda ?: runda

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.75f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Runda ${currentRunda}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextAccent
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LoadingStateIgraSam(uiState)
                    DisplayContentIgraSam(uiState, crta)
                    // Prikaz prepoznatog teksta
                    if (odgovor.value.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "\"${odgovor.value}\"",
                            color = PrimaryDark.copy(alpha = 0.8f),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Uklonjen OutlinedTextField
                    Spacer(modifier = Modifier.height(20.dp))

                    UserInputSectionPogodiPevaj(
                        uiState, context, navController, sZ, sN, poeni,
                        isAudioP, odgovor, isListening, speechRecognizer, viewModel
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    HelpButtonsSectionIgraSam(crta, uiState, context)
                }
            }

            BottomInfoBar(runda = currentRunda, poeni = poeni, count = count.value)
        }
    }
}


@Composable
fun TimerEffectPogodiPevaj(
    count: MutableState<Int>,
    navController: NavController,
    sZ: String,
    sN: String,
    poeni: Int,
    runda: Int
) {
    LaunchedEffect(runda) {
        count.value = 0
        while (count.value < 60) {
            delay(1000)
            count.value += 1
        }
        val nextRunda = runda
        if (nextRunda < 7) {
            navController.navigate(Destinacije.Igra_pogodiPevaj.ruta + "/$sZ/$sN/$nextRunda/$poeni") {
              //  popUpTo(Destinacije.Igra_pogodiPevaj.ruta) { inclusive = true }
            }
        } else {
            navController.navigate(Destinacije.Kraj_pogodiPevaj.ruta + "/$poeni") {
             //   popUpTo(Destinacije.Igra_pogodiPevaj.ruta) { inclusive = true }
            }
        }
    }
}

@Composable
fun FetchDataEffectPogodiPevaj(
    selectedZanrovi: List<String>,
    selectedNivo: List<String>,
    runda: Int,
    poeni: Int,
    viewModel: IgraSamViewModel,
    context: Context,
    igraSamLista: IgraSamLista
) {
    LaunchedEffect(key1 = selectedZanrovi, key2 = selectedNivo, key3 = runda) {
        if (!selectedZanrovi.isNullOrEmpty()) {
            igraSamLista.igraSamLista?.let {
                viewModel.fetchIgraSamData(selectedZanrovi, selectedNivo.toString(),runda,poeni,
                    it,context)
            }
        }
    }
}

@Composable
fun UserInputSectionPogodiPevaj(
    uiState: UiStateI,
    context: Context,
    navController: NavController,
    sZ: String,
    sN: String,
    poeni: Int,
    isAudioP: MutableState<Boolean>,
    odgovor: MutableState<String>,
    isListening: MutableState<Boolean>,
    speechRecognizer: SpeechRecognizer,
    viewModel: IgraSamViewModel
) {
    val runda = uiState.igrasam?.runda ?: 0

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SpeechButton(
            context = context,
            speechRecognizer = speechRecognizer,
            isListening = isListening.value,
            onListeningChange = { isListening.value = it },
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        ActionButton(
            onClick = {
                if (!isAudioP.value) {
                    uiState.igrasam?.zvuk?.let { zvukUrl ->
                        viewModel.downloadAudio(zvukUrl, context)
                    }
                    isAudioP.value = true
                } else {
                    viewModel.stopAudio()
                    isAudioP.value = false
                }
            },
            text = if (isAudioP.value) "Zaustavi" else "Pusti",
            icon = if (isAudioP.value) Icons.Default.Check else Icons.Default.PlayArrow,
            containerColor = AccentPink.copy(alpha = 0.8f),
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        ActionButton(
            onClick = {
                if (uiState.igrasam != null) {
                    val normalize = { text: String ->
                        text.toLowerCase()
                            .replace("ć", "c").replace("č", "c")
                            .replace("đ", "dj")
                            .replace("ž", "z").replace("š", "s")
                            .filter { it.isLetterOrDigit() || it.isWhitespace() }
                    }

                    val cleanedAnswer = normalize(odgovor.value)
                    val correctTarget = normalize(uiState.igrasam!!.tacno)
                    val isCorrect = cleanedAnswer == correctTarget
                    val nextRunda = runda

                    if (isCorrect) {
                        Toast.makeText(context, "Tačan odgovor!", Toast.LENGTH_SHORT).show()
                        viewModel.stopAudio()
                        if (nextRunda < 7) {
                            navController.navigate(Destinacije.Igra_pogodiPevaj.ruta + "/$sZ/$sN/$nextRunda/${poeni + 10}") {
                                //popUpTo(Destinacije.Igra_pogodiPevaj.ruta) { inclusive = true }
                            }
                        } else {
                            navController.navigate(Destinacije.Kraj_pogodiPevaj.ruta + "/${poeni + 10}") {
                                //popUpTo(Destinacije.Igra_pogodiPevaj.ruta) { inclusive = true }
                            }
                        }
                    } else {
                        Toast.makeText(context, "Netačan odgovor", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            text = "Proveri",
            icon = Icons.Default.Check,
            containerColor = PrimaryDark,
            modifier = Modifier.weight(1f)
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    ActionButton(
        onClick = {
            viewModel.stopAudio()
            val nextRunda = runda
            if (nextRunda < 7) {
                navController.navigate(Destinacije.Igra_pogodiPevaj.ruta + "/$sZ/$sN/$nextRunda/$poeni") {
                   // popUpTo(Destinacije.Igra_pogodiPevaj.ruta) { inclusive = true }
                }
            } else {
                navController.navigate(Destinacije.Kraj_pogodiPevaj.ruta + "/$poeni") {
                   // popUpTo(Destinacije.Igra_pogodiPevaj.ruta) { inclusive = true }
                }
            }
        },
        text = "Preskoči/Dalje",
        icon = Icons.Default.ArrowForward,
        containerColor = PrimaryDark.copy(alpha = 0.5f),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun SpeechButton(
    context: Context,
    speechRecognizer: SpeechRecognizer,
    isListening: Boolean,
    onListeningChange: (Boolean) -> Unit,
    modifier: Modifier
) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            Toast.makeText(context, "Dozvola za mikrofon je potrebna.", Toast.LENGTH_SHORT).show()
        }
    }

    Button(
        onClick = {
            if (androidx.core.content.ContextCompat.checkSelfPermission(context, android.Manifest.permission.RECORD_AUDIO) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                permissionLauncher.launch(android.Manifest.permission.RECORD_AUDIO)
                return@Button
            }

            if (!isListening) {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE, "sr-RS")
                }
                speechRecognizer.startListening(intent)
                onListeningChange(true)
            } else {
                speechRecognizer.stopListening()
                onListeningChange(false)
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isListening) TimeWarningColor else AccentPink,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.height(52.dp)
    ) {
        Icon(Icons.Default.PlayArrow, contentDescription = if (isListening) "Slušam" else "Govori", modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(if (isListening) "Govori" else "Govori", fontSize = 14.sp)
    }
}

@Composable
fun ActionButton(
    onClick: () -> Unit,
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier,
    containerColor: Color = AccentPink,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.height(52.dp)
    ) {
        Icon(icon, contentDescription = text, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, fontSize = 14.sp)
    }
}


@Composable
fun HelpButtonsSectionIgraSam(crta: MutableState<String>, uiState: UiStateI, context: Context) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Pomoć: ",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = TEXT_COLOR,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 8.dp)
        )

        OutlinedHelpButton(
            onClick = {
                val i=uiState.igrasam?.izvodjac ?: "Nema podataka"
                Toast.makeText(context, "Izvođač: $i", Toast.LENGTH_SHORT).show()
            },
            text = "Izvođač"
        )

        OutlinedHelpButton(
            onClick = {
                val p = uiState.igrasam?.pesma ?: "Nema podataka"
                Toast.makeText(context, "Pesma: $p", Toast.LENGTH_LONG).show()
            },
            text = "Pesma"
        )

        OutlinedHelpButton(
            onClick = {
                if (crta.value.contains("_")){
                    val words = uiState.igrasam?.tacno?.split(" ") ?: return@OutlinedHelpButton
                    val newCrta = words.joinToString(" ") { word ->
                        if (word.isNotEmpty()) {
                            val firstLetter = word.first().toString()
                            val underscores = "_".repeat(word.length - 1)
                            "$firstLetter$underscores"
                        } else ""
                    }
                    crta.value= newCrta
                }
            },
            text = "Slova"
        )
    }
}

@Composable
fun OutlinedHelpButton(onClick: () -> Unit, text: String) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = PrimaryDark
        ),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            width = 1.dp,
            brush = androidx.compose.ui.graphics.SolidColor(PrimaryDark.copy(alpha = 0.6f))
        ),
        modifier = Modifier.height(36.dp)
    ) {
        Text(text, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun BottomInfoBar(runda: Int, poeni: Int, count: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(LIGTH_BLUE, RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .height(IntrinsicSize.Min)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Runda: ", color = PrimaryDark, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Text(
                    text = "$runda / 7",
                    color = PrimaryDark,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Vreme: ", color = PrimaryDark, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Text(
                    text = "$count s",
                    color = if (count >= 50) TimeWarningColor else PrimaryDark,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Poeni: ", color = PrimaryDark, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Text(
                    text = "$poeni",
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp
                )
                Text(text = " \uD834\uDD1E", color = Color.Black)
            }
        }
    }
}