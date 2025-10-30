package com.example.blankspace.screens.igra_sam

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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.data.retrofit.BASE_URL
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.viewModels.DuelViewModel
import com.example.blankspace.viewModels.UiStateD
import kotlinx.coroutines.delay

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)
private val InfoBarColor = Color(0xFFE0BBE4)
private val TimeWarningColor = Color(0xFFD32F2F)

@Composable
fun SpeechButton2(
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
fun ActionButton2(
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
fun Duel(navController: NavController,runda:Int,poeni:Int,viewModel: DuelViewModel,sifra:Int) {
    Box(modifier = Modifier.fillMaxSize().padding(top = 52.dp)) {
        BgCard2()
        Duel_mainCard(navController,runda,poeni,viewModel,sifra, modifier = Modifier.align(Alignment.Center))
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Duel_mainCard(navController: NavController,runda:Int,poeni:Int,viewModel: DuelViewModel,sifra: Int, modifier: Modifier) {
    val uiState by viewModel.uiState.collectAsState()
    val uiStateSifra by viewModel.uiStateSifSobe.collectAsState()
    val context = LocalContext.current

    val count = remember { mutableStateOf(0) }
    val isAudioP = remember { mutableStateOf(false) }
    var crta= remember { mutableStateOf("") }
    var odgovor by remember { mutableStateOf("") }
    var isListening by remember { mutableStateOf(false) } // State za glas

    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }
    SetupSpeechRecognition(speechRecognizer, onResults = { odgovor = it }, onListeningChange = { isListening = it })

    LaunchedEffect(runda) {
        count.value = 0
        while (count.value < 60) {
            delay(1000)
            count.value += 1
        }

        //if (uiState.duel?.runda == runda) {
            viewModel.stopAudio()
            viewModel.dodaj(0) // Dodajemo 0 poena jer je isteklo vreme

            if(runda < 7){
                uiStateSifra.sifraResponse?.stihovi?.let {
                    viewModel.fetchDuel(runda , poeni, it, uiState.duel!!.rundePoeni, context)
                }
                navController.navigate(Destinacije.Duel.ruta+"/${runda+1}/${poeni}/${sifra}")
            } else {
                viewModel.fetchCekanjeRezultata(poeni, viewModel.sifraSobe.value.sifra, uiState.duel!!.rundePoeni, viewModel.redniBroj.value.redniBroj)
                navController.navigate("${Destinacije.Cekanje_rezultata.ruta}/${poeni}/${sifra}")
            }
        //}
    }

    LaunchedEffect(viewModel.uiState.value.duel?.crtice) {
        crta.value = viewModel.uiState.value.duel?.crtice ?: ""
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
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(80.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (uiState.isRefreshing) {
                        CircularProgressIndicator(color = AccentPink)
                    } else {
                        uiState.error?.let {
                            Text(text = "Greška: $it", color = TimeWarningColor)
                        }

                        uiState.duel?.let { duel ->
                            duel.stihpoznat.forEach { stih->
                                Text(
                                    text = stih,
                                    color= TEXT_COLOR,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                            Text(
                                "${crta.value}",
                                color = TEXT_COLOR,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(vertical = 8.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // OutlinedTextField
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
                        SpeechButton2(
                            context = context,
                            speechRecognizer = speechRecognizer,
                            isListening = isListening,
                            onListeningChange = { isListening = it },
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        ActionButton2(
                            onClick = {
                                if(!isAudioP.value){
                                    uiState.duel?.zvuk?.let { zvukUrl ->
                                        viewModel.downloadAudio(zvukUrl, context)
                                    }
                                    isAudioP.value=true
                                }
                            },
                            text = "Pusti",
                            icon = Icons.Default.PlayArrow,
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        ActionButton2(
                            onClick = {
                                val normalize = { text: String ->
                                    text.toLowerCase()
                                        .replace("ć", "c").replace("č", "c")
                                        .replace("đ", "dj")
                                        .replace("ž", "z").replace("š", "s")
                                        .filter { it.isLetterOrDigit() || it.isWhitespace() }
                                }

                                if (uiState.duel != null) {
                                    val cleanedAnswer = normalize(odgovor)
                                    val correctTarget = normalize(uiState.duel!!.tacno)

                                    if (cleanedAnswer == correctTarget) {
                                        Toast.makeText(context, "Tačan odgovor!", Toast.LENGTH_SHORT).show()
                                        viewModel.stopAudio()
                                        viewModel.dodaj(1)

                                        val nextRunda = runda + 1
                                        if(nextRunda < 7){
                                            uiStateSifra.sifraResponse?.stihovi?.let {
                                                viewModel.fetchDuel(nextRunda, poeni + 10, it, uiState.duel!!.rundePoeni, context)
                                            }
                                            navController.navigate(Destinacije.Duel.ruta+"/${nextRunda}/${poeni+10}/${sifra}")
                                        } else {
                                            viewModel.fetchCekanjeRezultata(poeni + 10, viewModel.sifraSobe.value.sifra, uiState.duel!!.rundePoeni, viewModel.redniBroj.value.redniBroj)
                                            navController.navigate("${Destinacije.Cekanje_rezultata.ruta}/${poeni+10}/${sifra}")
                                        }
                                    } else {
                                        Toast.makeText(context, "Netačan odgovor", Toast.LENGTH_SHORT).show()
                                        viewModel.dodaj(0)
                                        viewModel.fetchCekanjeRezultata(poeni, viewModel.sifraSobe.value.sifra, uiState.duel!!.rundePoeni, viewModel.redniBroj.value.redniBroj)
                                        navController.navigate("${Destinacije.Cekanje_rezultata.ruta}/${poeni}/${sifra}")
                                    }
                                }

                            },
                            text = "Proveri",
                            icon = Icons.Default.Check,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    ActionButton2(
                        onClick = {
                            viewModel.stopAudio()
                            viewModel.dodaj(0) // 0 poena za preskakanje

                            val nextRunda = runda + 1
                            if(nextRunda < 7){
                                uiStateSifra.sifraResponse?.stihovi?.let {
                                    viewModel.fetchDuel(nextRunda, poeni, it, uiState.duel!!.rundePoeni, context)
                                }
                                navController.navigate(Destinacije.Duel.ruta+"/${nextRunda}/${poeni}/${sifra}")
                            } else {
                                viewModel.fetchCekanjeRezultata(poeni, viewModel.sifraSobe.value.sifra, uiState.duel!!.rundePoeni, viewModel.redniBroj.value.redniBroj)
                                navController.navigate("${Destinacije.Cekanje_rezultata.ruta}/${poeni}/${sifra}")
                            }
                        },
                        text = "Preskoči/Dalje",
                        icon = Icons.Default.ArrowForward,
                        containerColor = PrimaryDark.copy(alpha = 0.8f),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            BottomInfoBarDuel(runda = uiState.duel?.runda ?: runda, poeni = poeni, count = count.value)
        }
    }
}

@Composable
fun SetupSpeechRecognition(
    speechRecognizer: SpeechRecognizer,
    onResults: (String) -> Unit,
    onListeningChange: (Boolean) -> Unit
) {
    LaunchedEffect(Unit) {
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() { onListeningChange(false) }
            override fun onError(error: Int) { onListeningChange(false) }
            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) { onResults(matches[0]) }
                onListeningChange(false)
            }
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
    }
}

@Composable
fun BottomInfoBarDuel(runda: Int, poeni: Int, count: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            //.align(Alignment.BottomCenter)
            .background(InfoBarColor, RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .height(IntrinsicSize.Min)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Runda
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Runda: ", color = PrimaryDark, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Text(text = "$runda", color = PrimaryDark, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
            }

            // Vreme
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Vreme: ", color = PrimaryDark, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Text(
                    text = "$count s",
                    color = if (count >= 50) TimeWarningColor else PrimaryDark,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp
                )
            }

            // Poeni
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