package com.example.blankspace.screens.igra_offline


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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.data.retrofit.BASE_URL
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.ui.theme.LIGTH_BLUE
import com.example.blankspace.ui.theme.TEXT_COLOR
import com.example.blankspace.viewModels.DatabaseViewModel
import com.example.blankspace.viewModels.IgraOfflineLista
import com.example.blankspace.viewModels.IgraSamLista
import com.example.blankspace.viewModels.IgraSamViewModel
import com.example.blankspace.viewModels.UiStateI
import com.example.blankspace.viewModels.UiStateIgraOffline
import kotlinx.coroutines.delay

@Composable
fun Igra_offline(navController: NavController, selectedZanrovi: String, selectedNivo: String,runda:Int,poeni:Int,databaseViewModel: DatabaseViewModel) {
    Box(modifier = Modifier.fillMaxSize().padding(top = 52.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))

        val selectedNivoList = selectedNivo.split(",").map { it.trim() }
        val selectedZanroviList = selectedZanrovi.split(",").map { it.trim() }

        Igra_offline_mainCard(navController, selectedZanroviList, selectedNivoList,selectedZanrovi,selectedNivo,runda,poeni,databaseViewModel)
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Igra_offline_mainCard(navController: NavController, selectedZanrovi: List<String>, selectedNivo: List<String>,sZ:String,sN:String,runda:Int,poeni:Int,databaseViewModel: DatabaseViewModel) {
    val context = LocalContext.current
    val uiState by databaseViewModel.uiState.collectAsState()
    val igraOfflineLista by databaseViewModel.IgraOfflineLista.collectAsState()

    val count = remember { mutableStateOf(0) }
    val isAudioP = remember { mutableStateOf(false) }
    var crta= remember { mutableStateOf("") }

    TimerEffect(count, navController, sZ, sN, runda, poeni)
    FetchDataEffect(selectedZanrovi, selectedNivo, runda, poeni, databaseViewModel, context, igraOfflineLista)

    LaunchedEffect(databaseViewModel.uiState.value.igraoffline?.crtice) {
        crta.value = databaseViewModel.uiState.value.igraoffline?.crtice ?: ""
    }

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).fillMaxHeight(0.7f),
        shape = RoundedCornerShape(60.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                DisplayContentIgraOffline(uiState, crta)
                Spacer(modifier = Modifier.height(22.dp))

                UserInputSectionIgraOffline(uiState, context, navController, sZ, sN, runda, poeni, isAudioP, count,databaseViewModel)
                Spacer(modifier = Modifier.height(22.dp))

                HelpButtonsSectionIgraOffline(crta, uiState, context)
            }

            // Dodavanje plave trake na dnu
            Box(
                modifier = Modifier.fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(LIGTH_BLUE).height(40.dp)
            ){
                Column {
                    Spacer(modifier=Modifier.padding(top=10.dp))
                    Row{
                        Spacer(modifier = Modifier.padding(start = 30.dp))
                        Text(text = "Runda: ${uiState.igraoffline?.runda} ")
                        Spacer(modifier = Modifier.padding(start = 5.dp))
                        Text(text = "Vreme: ")
                        Text(text=" ${count.value}",color=
                        if(count.value>=50)Color.Red else
                            TEXT_COLOR)
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(end=30.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(text = "\uD834\uDD1E")  // Violinijski ključ
                            Text(text = " ${poeni} ")
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun TimerEffect(
    count: MutableState<Int>,
    navController: NavController,
    sZ: String,
    sN: String,
    runda: Int,
    poeni: Int
) {
    LaunchedEffect(Unit) {
        while (count.value < 60) {
            delay(1000)  // pauza -1 sekunda
            count.value += 1  // count+1
        }
        navController.navigate(Destinacije.Igra_offline.ruta+"/"+sZ+"/"+sN+"/"+ (runda+1).toString()+"/"+(poeni).toString())
    }
}

@Composable
fun FetchDataEffect(
    selectedZanrovi: List<String>,
    selectedNivo: List<String>,
    runda: Int,
    poeni: Int,
    viewModel: DatabaseViewModel,
    context: Context,
    igraOfflineLista: IgraOfflineLista
) {
    LaunchedEffect(key1 = selectedZanrovi, key2 = selectedNivo) {
        if (!selectedZanrovi.isNullOrEmpty()) {
           // val url = BASE_URL+"get_audio/"
            //viewModel.downloadAudio(url, context)
            igraOfflineLista.igraOfflineLista?.let {
                viewModel.fetchIgraOfflineData(selectedZanrovi, selectedNivo.toString(),runda,poeni,
                    it,context)
            }
        }
    }
}

@Composable
fun DisplayContentIgraOffline(uiState: UiStateIgraOffline, crta: MutableState<String>) {
    uiState.igraoffline?.let { igrasam ->
        var flag by remember { mutableStateOf(false) }
        igrasam.stihpoznat.forEach { stih ->
            flag = stih.length > 36
        }
        igrasam.stihpoznat.forEach { stih ->
            Text(
                text = stih,
                color = TEXT_COLOR,
                fontSize = if (flag) 12.sp else 15.sp
            )

        }
        Text("${crta.value}", color = TEXT_COLOR, fontSize = if (crta.value.length > 36) 12.sp else 15.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInputSectionIgraOffline(
    uiState: UiStateIgraOffline,
    context: Context,
    navController: NavController,
    sZ: String,
    sN: String,
    runda: Int,
    poeni: Int,
    isAudioP: MutableState<Boolean>,
    count: MutableState<Int>,
    viewModel: DatabaseViewModel
) {
    var odgovor by remember { mutableStateOf("") }

    var isListening by remember { mutableStateOf(false) }
    val speechRecognizer = remember {
        SpeechRecognizer.createSpeechRecognizer(context)
    }

    // RecognitionListener koji hvata događaje prepoznavanja
    val recognitionListener = remember {
        object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) { // Možeš staviti neki log ili UI indikator
            }

            override fun onBeginningOfSpeech() { // Korisnik počeo da govori
            }

            override fun onRmsChanged(rmsdB: Float) { // Možeš iskoristiti za vizualni indikator jačine glasa
            }

            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {
                // Korisnik je završio govor
                isListening = false
            }

            override fun onError(error: Int) {
                isListening = false
                // Možeš prikazati grešku, na primer Toast
                Toast.makeText(context, "Greška prilikom prepoznavanja govora: $error", Toast.LENGTH_SHORT).show()
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    odgovor = matches[0] // Prvi rezultat
                }
                isListening = false
            }

            override fun onPartialResults(partialResults: Bundle?) {
                // Opcionalno: delimični rezultati
            }

            override fun onEvent(eventType: Int, params: Bundle?) {}
        }
    }

    // Postavljanje listenera jednom
    LaunchedEffect(Unit) {
        speechRecognizer.setRecognitionListener(recognitionListener)
    }

    OutlinedTextField(
        value = odgovor,
        onValueChange = { odgovor = it },
        label = { Text("odgovor", color = TEXT_COLOR) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFFFF69B4),
            unfocusedBorderColor = Color.Gray
        ),
        singleLine = true,
        shape = RoundedCornerShape(20.dp)
    )

    Spacer(modifier = Modifier.height(22.dp))
    // Launcher za traženje dozvole
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            Toast.makeText(context, "Dozvola za mikrofon je potrebna.", Toast.LENGTH_SHORT).show()
        }
    }

    Row {
        Button(
            onClick = {
                // Provera dozvole
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
                    isListening = true

                } else {
                    speechRecognizer.stopListening()
                    isListening = false

                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isListening) Color.Red else Color(0xFFFF69B4),
                contentColor = Color.White
            )
        ) {
            Text(if (isListening) "Slusam" else "Govori", style = MaterialTheme.typography.bodyMedium)
        }

        Button(
            onClick = {
                if (!isAudioP.value) {
                    uiState.igraoffline?.zvuk?.let { zvukUrl ->
                       // viewModel.downloadAudio(zvukUrl, context)
                    }
                    isAudioP.value=true
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF69B4),
                contentColor = Color.White
            )
        ) {
            Text(text = "Pusti", style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center, color = Color.White)
        }
        Spacer(modifier = Modifier.height(1.dp))

        Button(
            onClick = {
                if (uiState.igraoffline != null) {
                    val cleanedAnswer = odgovor.toLowerCase()
                        .replace("ć", "c")
                        .replace("đ", "dj")
                        .replace("ž", "z")
                        .replace("č", "c")
                        .replace("š", "s")
                    if (cleanedAnswer == uiState.igraoffline!!.tacno.toLowerCase()) {
                        Toast.makeText(context, "Tacan odgovor!", Toast.LENGTH_SHORT).show()
                        //viewModel.stopAudio()
                        if (uiState.igraoffline?.runda!! < 7) {
                            navController.navigate(Destinacije.Igra_offline.ruta + "/$sZ/$sN/${uiState.igraoffline?.runda!!}/${poeni + 10}")
                        } else {
                            navController.navigate(Destinacije.Kraj_igre_offline.ruta + "/$poeni")
                        }
                    } else {
                        Toast.makeText(context, "Netacan odgovor", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF69B4),
                contentColor = Color.White
            )
        ) {
            Text(text = "Proveri", style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center, color = Color.White)
        }

        Spacer(modifier = Modifier.height(1.dp))
    }
    Row{

        Button(
            onClick = {
                //viewModel.stopAudio()
                if (uiState.igraoffline?.runda!! < 7) {
                    navController.navigate(Destinacije.Igra_offline.ruta + "/$sZ/$sN/${uiState.igraoffline?.runda!!}/$poeni")
                } else {
                    navController.navigate(Destinacije.Kraj_igre_offline.ruta + "/$poeni")
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF69B4),
                contentColor = Color.White
            )
        ) {
            Text(text = "Dalje", style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center, color = Color.White)
        }

    }
}

@Composable
fun HelpButtonsSectionIgraOffline(crta: MutableState<String>, uiState: UiStateIgraOffline, context: Context) {
    Row {
        Text(
            text = "Pomoć ",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = TEXT_COLOR,
            modifier = Modifier.padding(top = 15.dp)
        )
        val configuration = LocalConfiguration.current
        val isDarkMode = configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK == android.content.res.Configuration.UI_MODE_NIGHT_YES
        val cn2= LocalContext.current
        OutlinedButton(
            onClick = {
                val i=uiState.igraoffline?.izvodjac

                Toast.makeText(cn2, "" +i, Toast.LENGTH_SHORT).show()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Izvođač",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = TEXT_COLOR
            )
        }

        OutlinedButton(
            onClick = {
                Toast.makeText(context, uiState.igraoffline?.pesma, Toast.LENGTH_LONG).show()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Pesma",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = TEXT_COLOR
            )
        }

        OutlinedButton(
            onClick = {
                if (crta.value[0].toString()=="_"){
                    val words = uiState.igraoffline?.tacno?.split(" ")


                    val firstLetters = words?.joinToString(" ") {
                        val firstLetter = it.first()  // Uzmi prvo slovo reči
                        val underscores = "_ ".repeat(it.length - 1)  // Ponovi crticama ostatak dužine reči
                        "$firstLetter$underscores"  // Spoj prvo slovo sa crtama
                    }
                    crta.value= firstLetters?.toString()!!

                }

            },
        ) {
            Text(
                text = "Slova",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = TEXT_COLOR
            )
        }
    }
}