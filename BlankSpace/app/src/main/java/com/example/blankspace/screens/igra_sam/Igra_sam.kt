package com.example.blankspace.screens.igra_sam

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.widget.Toast
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
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.viewModels.IgraSamLista
import com.example.blankspace.viewModels.IgraSamViewModel
import com.example.blankspace.viewModels.UiStateI
import kotlinx.coroutines.delay
import com.example.blankspace.ui.theme.*
private val InfoBarColor = Color(0xFFE0BBE4)
private val TimeWarningColor = Color(0xFFD32F2F)

val LIGTH_BLUE = InfoBarColor
val TEXT_COLOR = PrimaryDark


@Composable
fun Igra_sam(navController: NavController, selectedZanrovi: String, selectedNivo: String, runda: Int, poeni: Int, viewModelIgraSam: IgraSamViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()

        val selectedNivoList = selectedNivo.split(",").map { it.trim() }
        val selectedZanroviList = selectedZanrovi.split(",").map { it.trim() }

        Igra_sam_mainCard(
            navController,
            selectedZanroviList,
            selectedNivoList,
            selectedZanrovi,
            selectedNivo,
            runda,
            poeni,
            viewModelIgraSam,
            modifier = Modifier.align(Alignment.Center) // Centriranje
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Igra_sam_mainCard(
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

    TimerEffect(count, navController, sZ, sN, uiState, runda, poeni)
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
                        text = "Runda ${uiState.igrasam?.runda ?: runda}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = AccentPink
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LoadingStateIgraSam(uiState)
                    DisplayContentIgraSam(uiState, crta)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    UserInputSectionIgraSam(uiState, context, navController, sZ, sN, runda, poeni, isAudioP, count, viewModel)
                    Spacer(modifier = Modifier.height(20.dp))
                    HelpButtonsSectionIgraSam(crta, uiState, context)
                }
            }

            BottomInfoBar(runda = uiState.igrasam?.runda ?: runda, poeni = poeni, count = count.value)
        }
    }
}

@Composable
fun TimerEffect(
    count: MutableState<Int>,
    navController: NavController,
    sZ: String,
    sN: String,
    uiState: UiStateI,
    runda: Int,
    poeni: Int
) {
    LaunchedEffect(runda) {
        count.value = 0
        while (count.value < 60) {
            delay(1000)
            count.value += 1
        }

        // Navigacija tek nakon isteka vremena
        //if (uiState.igrasam?.runda == runda) {
            val nextRunda = runda + 1
            if (nextRunda < 7) {
                navController.navigate(Destinacije.Igra_sam.ruta + "/$sZ/$sN/$nextRunda/$poeni")
            } else {
                navController.navigate(Destinacije.Kraj_igre_igre_sam.ruta + "/$poeni")
            }
        //}
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
    LaunchedEffect(key1 = selectedZanrovi, key2 = selectedNivo, key3 = runda) { // Dodata runda
        if (!selectedZanrovi.isNullOrEmpty()) {
            igraSamLista.igraSamLista?.let {
                viewModel.fetchIgraSamData(selectedZanrovi, selectedNivo.toString(),runda,poeni,
                    it,context)
            }
        }
    }
}

@Composable
fun LoadingStateIgraSam(uiState: UiStateI) {
    if (uiState.isRefreshing) {
        CircularProgressIndicator(color = AccentPink)
    } else {
        uiState.error?.let {
            Text(text = "Greška: $it", color = TimeWarningColor)
        }
    }
}

@Composable
fun DisplayContentIgraSam(uiState: UiStateI, crta: MutableState<String>) {
    uiState.igrasam?.let { igrasam ->
        var flag by remember { mutableStateOf(false) }
        igrasam.stihpoznat.forEach { stih ->
            flag = stih.length > 36
        }
        igrasam.stihpoznat.forEach { stih ->
            Text(
                text = stih,
                color = TEXT_COLOR,
                fontSize = if (flag) 14.sp else 16.sp,
                textAlign = TextAlign.Center
            )

        }
        Text(
            text = "${crta.value}",
            color = TEXT_COLOR,
            fontSize = if (crta.value.length > 36) 16.sp else 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInputSectionIgraSam(
    uiState: UiStateI,
    context: Context,
    navController: NavController,
    sZ: String,
    sN: String,
    runda: Int,
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

                    if (cleanedAnswer == correctTarget) {
                        Toast.makeText(context, "Tačan odgovor!", Toast.LENGTH_SHORT).show()
                        viewModel.stopAudio()
                        val nextRunda = runda + 1 // Koristi runda argument
                        if (nextRunda < 7) {
                            navController.navigate(Destinacije.Igra_sam.ruta + "/$sZ/$sN/$nextRunda/${poeni + 10}")
                        } else {
                            navController.navigate(Destinacije.Kraj_igre_igre_sam.ruta + "/${poeni + 10}")
                        }
                    } else {
                        Toast.makeText(context, "Netačan odgovor", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            text = "Proveri",
            icon = Icons.Default.Check,
            modifier = Modifier.weight(1.5f)
        )
    }

    Spacer(modifier = Modifier.height(12.dp))

    ActionButton(
        onClick = {
            viewModel.stopAudio()
            val nextRunda = runda + 1
            if (nextRunda < 7) {
                navController.navigate(Destinacije.Igra_sam.ruta + "/$sZ/$sN/$nextRunda/$poeni")
            } else {
                navController.navigate(Destinacije.Kraj_igre_igre_sam.ruta + "/$poeni")
            }
        },
        text = "Preskoči/Dalje",
        icon = Icons.Default.ArrowForward,
        containerColor = PrimaryDark.copy(alpha = 0.8f),
        modifier = Modifier.fillMaxWidth()
    )
}