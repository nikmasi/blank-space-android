package com.example.blankspace.screens.igra_challenge

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
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
import com.example.blankspace.viewModels.IgraSamLista
import com.example.blankspace.viewModels.IgraSamViewModel
import com.example.blankspace.viewModels.UiStateI
import kotlinx.coroutines.delay

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)
private val InfoBarColor = Color(0xFFE0BBE4)
private val TextMain = PrimaryDark
private val TextAccent = AccentPink
private val TimeWarningColor = Color(0xFFD32F2F)

val LIGTH_BLUE = InfoBarColor
val TEXT_COLOR = PrimaryDark


@Composable
fun Igra_challenge(navController: NavController, selectedZanrovi: String, selectedNivo: String,runda:Int,poeni:Int,viewModelIgraSam:IgraSamViewModel) {
    Box(modifier = Modifier.fillMaxSize().padding(top = 52.dp)) {
        BgCard2()

        val selectedNivoList = selectedNivo.split(",").map { it.trim() }
        val selectedZanroviList = selectedZanrovi.split(",").map { it.trim() }

        Igra_challenge_mainCardStyled(
            navController,
            selectedZanroviList,
            selectedNivoList,
            selectedZanrovi,
            selectedNivo,
            runda,
            poeni,
            viewModelIgraSam,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Igra_challenge_mainCardStyled(
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

    val count = remember { mutableStateOf(runda) }
    val isAudioP = remember { mutableStateOf(false) }
    var crta= remember { mutableStateOf("") }

    TimerEffectStyled(count, navController, poeni)
    FetchDataEffect(selectedZanrovi, selectedNivo, runda, poeni, viewModel, context, igraSamLista)

    LaunchedEffect(viewModel.uiState.value.igrasam?.crtice) {
        crta.value = viewModel.uiState.value.igrasam?.crtice ?: ""
    }

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
                        text = "Challenge Runda",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextAccent
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LoadingStateIgraSamStyled(uiState)
                    DisplayContentIgraSamStyled(uiState, crta)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    UserInputSectionIgraChallengeStyled(uiState, context, navController, sZ, sN, poeni, isAudioP, count, viewModel)
                    Spacer(modifier = Modifier.height(20.dp))
                    HelpButtonsSectionIgraSamStyled(crta, uiState, context)
                }
            }

            BottomInfoBarChallenge(poeni = poeni, count = count.value)
        }
    }
}

@Composable
fun TimerEffectStyled(
    count: MutableState<Int>,
    navController: NavController,
    poeni: Int
) {
    LaunchedEffect(Unit) {
        while (count.value > 0) {
            delay(1000)
            count.value -= 1
        }
        navController.navigate(Destinacije.Kraj_challenge.ruta + "/$poeni")
    }
}

@Composable
fun LoadingStateIgraSamStyled(uiState: UiStateI) {
    if (uiState.isRefreshing) {
        CircularProgressIndicator(color = TextAccent)
    } else {
        uiState.error?.let {
            Text(text = "Greška: $it", color = TimeWarningColor)
        }
    }
}

@Composable
fun DisplayContentIgraSamStyled(uiState: UiStateI, crta: MutableState<String>) {
    uiState.igrasam?.let { igrasam ->
        val fontSize = if ((igrasam.stihpoznat.firstOrNull()?.length ?: 0) > 36 || crta.value.length > 36) 14.sp else 16.sp

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            igrasam.stihpoznat.forEach { stih ->
                Text(
                    text = stih,
                    color = TEXT_COLOR.copy(alpha = 0.8f),
                    fontSize = fontSize,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "${crta.value}",
                color = AccentPink,
                fontSize = if (crta.value.length > 36) 16.sp else 18.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInputSectionIgraChallengeStyled(
    uiState: UiStateI,
    context: Context,
    navController: NavController,
    sZ: String,
    sN: String,
    poeni: Int,
    isAudioP: MutableState<Boolean>,
    count: MutableState<Int>,
    viewModel: IgraSamViewModel
) {
    var odgovor by remember { mutableStateOf("") }
    var isListening by remember { mutableStateOf(false) }
    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }

    val recognitionListener = remember {
        object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() { isListening = false }
            override fun onError(error: Int) {
                isListening = false
                Toast.makeText(context, "Greška: $error", Toast.LENGTH_SHORT).show()
            }
            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) { odgovor = matches[0] }
                isListening = false
            }
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        }
    }

    LaunchedEffect(Unit) {
        speechRecognizer.setRecognitionListener(recognitionListener)
    }

    OutlinedTextField(
        value = odgovor,
        onValueChange = { odgovor = it },
        label = { Text("Tvoj odgovor") },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AccentPink,
            unfocusedBorderColor = PrimaryDark.copy(alpha = 0.5f),
            cursorColor = AccentPink
        ),
        singleLine = true,
        shape = RoundedCornerShape(12.dp)
    )

    Spacer(modifier = Modifier.height(20.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SpeechButton(
            context = context,
            speechRecognizer = speechRecognizer,
            isListening = isListening,
            onListeningChange = { isListening = it },
            modifier = Modifier.weight(1.5f)
        )

        Spacer(modifier = Modifier.width(4.dp))

        ActionButton(
            onClick = {
                if (!isAudioP.value) {
                    uiState.igrasam?.zvuk?.let { zvukUrl ->
                        viewModel.downloadAudio(zvukUrl, context)
                    }
                    isAudioP.value = true
                }
            },
            text = "Pusti",
            icon = Icons.Default.PlayArrow,
            modifier = Modifier.weight(1.5f)
        )
        Spacer(modifier = Modifier.width(4.dp))

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

                    val cleanedAnswer = normalize(odgovor)
                    val correctTarget = normalize(uiState.igrasam!!.tacno)
                    val isCorrect = cleanedAnswer == correctTarget

                    if (isCorrect) {
                        Toast.makeText(context, "Tačan odgovor!", Toast.LENGTH_SHORT).show()
                        viewModel.stopAudio()
                        navController.navigate(Destinacije.Igra_challenge.ruta + "/$sZ/$sN/${count.value}/${poeni + 10}")
                    } else {
                        Toast.makeText(context, "Netačan odgovor", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            text = "Proveri",
            icon = Icons.Default.Check,
            containerColor = PrimaryDark,
            modifier = Modifier.weight(1.5f)
        )
    }

    Spacer(modifier = Modifier.height(12.dp))

    ActionButton(
        onClick = {
            viewModel.stopAudio()
            if (count.value > 0) {
                navController.navigate(Destinacije.Igra_challenge.ruta + "/$sZ/$sN/${count.value}/$poeni")
            } else {
                navController.navigate(Destinacije.Kraj_challenge.ruta + "/$poeni")
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
        Text(if (isListening) "Slušam" else "Govori", fontSize = 14.sp)
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
fun HelpButtonsSectionIgraSamStyled(crta: MutableState<String>, uiState: UiStateI, context: Context) {
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
fun BottomInfoBarChallenge(poeni: Int, count: Int) {
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
                Text(text = "Vreme: ", color = PrimaryDark, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Text(
                    text = "$count s",
                    color = if (count <= 10) TimeWarningColor else PrimaryDark,
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
                Text(text = " \uD834\uDD1E", color = Color.Black) // Nota
            }
        }
    }
}

@Composable
fun FetchDataEffect(
    selectedZanrovi: List<String>,
    selectedNivo: List<String>,
    runda: Int,
    poeni: Int,
    viewModel: IgraSamViewModel,
    context: Context,
    igraSamLista: IgraSamLista
) {
    LaunchedEffect(key1 = selectedZanrovi, key2 = selectedNivo) {
        if (!selectedZanrovi.isNullOrEmpty()) {
            val url = BASE_URL+"get_audio/"
            //viewModel.downloadAudio(url, context)
            igraSamLista.igraSamLista?.let {
                viewModel.fetchIgraSamData(selectedZanrovi, selectedNivo.toString(),runda,poeni,
                    it,context)
            }
        }
    }
}