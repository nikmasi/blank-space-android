package com.example.blankspace.screens.igra_sam

import android.annotation.SuppressLint
import android.speech.SpeechRecognizer
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.blankspace.screens.igraj_u_duelu.ActionButton2
import com.example.blankspace.screens.igraj_u_duelu.BottomInfoBarDuel
import com.example.blankspace.screens.igraj_u_duelu.SetupSpeechRecognition
import com.example.blankspace.screens.igraj_u_duelu.SpeechButton2
import com.example.blankspace.viewModels.DuelViewModel
import kotlinx.coroutines.delay
import com.example.blankspace.ui.theme.*

val InfoBarColor = Color(0xFFE0BBE4)
val TimeWarningColor = Color(0xFFD32F2F)

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