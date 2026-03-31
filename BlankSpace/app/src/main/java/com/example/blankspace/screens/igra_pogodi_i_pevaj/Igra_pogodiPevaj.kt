package com.example.blankspace.screens.igra_pogodi_i_pevaj

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.igra_sam.ActionButton
import com.example.blankspace.screens.igra_sam.BottomInfoBar
import com.example.blankspace.screens.igra_sam.DisplayContentIgraSam
import com.example.blankspace.screens.igra_sam.HelpButtonsSectionIgraSam
import com.example.blankspace.screens.igra_sam.LoadingStateIgraSam
import com.example.blankspace.screens.igra_sam.SpeechButton
import com.example.blankspace.viewModels.IgraSamLista
import com.example.blankspace.viewModels.IgraSamViewModel
import com.example.blankspace.viewModels.UiStateI
import kotlinx.coroutines.delay
import com.example.blankspace.ui.theme.*

@Composable
fun Igra_pogodiPevaj(navController: NavController, selectedZanrovi: String, selectedNivo: String,runda:Int,poeni:Int,viewModelIgraSam:IgraSamViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
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
                        color = AccentPink
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