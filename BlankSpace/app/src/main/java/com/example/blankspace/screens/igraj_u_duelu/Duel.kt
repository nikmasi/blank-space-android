package com.example.blankspace.screens.igra_sam

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.ui.components.SmallButton
import com.example.blankspace.data.retrofit.BASE_URL
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.ui.theme.LIGTH_BLUE
import com.example.blankspace.ui.theme.TEXT_COLOR
import com.example.blankspace.viewModels.DuelViewModel
import kotlinx.coroutines.delay


@Composable
fun Duel(navController: NavController,runda:Int,poeni:Int,viewModel: DuelViewModel,sifra:Int) {
    Box(modifier = Modifier.fillMaxSize().padding(top = 52.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))
        Duel_mainCard(navController,runda,poeni,viewModel,sifra)
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Duel_mainCard(navController: NavController,runda:Int,poeni:Int,viewModel: DuelViewModel,sifra: Int) {
    val uiState by viewModel.uiState.collectAsState()
    val uiStateSifra by viewModel.uiStateSifSobe.collectAsState()
    val uiStateProveriSifru by viewModel.uiStateProveriSifru.collectAsState()
    val context = LocalContext.current

    val count = remember { mutableStateOf(0) }
    val isAudioP = remember { mutableStateOf(false) }
    var crta= remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (count.value < 60) {
            delay(1000)  // Pauza od 1 sekunde
            count.value += 1  // Povećaj brojač za 1
        }
        val url = BASE_URL+"get_audio/"

        uiStateSifra.sifraResponse?.stihovi?.let {
            viewModel.fetchDuel(uiState.duel?.runda!!,poeni,
                it, uiState.duel!!.rundePoeni,context)
        }

        navController.navigate(Destinacije.Duel.ruta+"/"+ uiState.duel?.runda!!+"/"+poeni+"/"+sifra)
    }

    LaunchedEffect(viewModel.uiState.value.duel?.crtice) {
        crta.value = viewModel.uiState.value.duel?.crtice ?: ""
    }

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight(0.6f),
        shape = RoundedCornerShape(60.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (uiState.isRefreshing) {
                    CircularProgressIndicator()
                } else {
                    uiState.error?.let {
                        Text(text = "Greška: $it", color = Color.Red)
                    }

                    uiState.duel?.let { igrasam ->
                        var flag by remember { mutableStateOf(false) }

                        igrasam.stihpoznat.forEach { stih->
                            if (stih.length>36) flag=true else flag=false
                        }
                        igrasam.stihpoznat.forEach { stih->
                            Text(text = stih,color= TEXT_COLOR,
                                fontSize = if (flag==true) 12.sp else 15.sp)
                        }

                        Text("${crta.value}", color = TEXT_COLOR,
                            fontSize = if (crta.value.length>36)12.sp else 15.sp)
                    }
                }
                Spacer(modifier = Modifier.height(22.dp))
                var odgovor by remember { mutableStateOf("") }

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
                Row {
                    SmallButton( onClick = {
                        if(!isAudioP.value){
                            uiState.duel?.zvuk?.let { zvukUrl ->
                                viewModel.downloadAudio(uiState.duel?.zvuk!!, context)
                            }
                        }
                    },text = "Pusti", style = MaterialTheme.typography.bodyMedium)

                    Spacer(modifier = Modifier.height(4.dp))

                    SmallButton(onClick = {
                        if (uiState.duel != null) {
                            odgovor=odgovor.toLowerCase()
                            odgovor.replace("ć", "c")
                                .replace("đ", "dj")
                                .replace("ž", "z")
                                .replace("č", "c")
                                .replace("š", "s")
                                .replace("ć", "c")
                            if (odgovor.toLowerCase() == uiState.duel!!.tacno.toLowerCase()) {
                                Toast.makeText(context, "Tacan odgovor!", Toast.LENGTH_SHORT).show()
                                viewModel.stopAudio()
                                viewModel.dodaj(1)
                                if(uiState.duel?.runda!! < 7){
                                    uiStateSifra.sifraResponse?.stihovi?.let {
                                        viewModel.fetchDuel(uiState.duel?.runda!!,poeni,
                                            it, uiState.duel!!.rundePoeni,context)
                                    }
                                    navController.navigate(Destinacije.Duel.ruta+"/"+ uiState.duel?.runda!!+"/"+(poeni+10).toString()+"/"+sifra)
                                }else {
                                    viewModel.fetchCekanjeRezultata(poeni,viewModel.sifraSobe.value.sifra,
                                        uiState.duel!!.rundePoeni,viewModel.redniBroj.value.redniBroj)
                                    navController.navigate("${Destinacije.Cekanje_rezultata.ruta}/${poeni}/${sifra}")

                                }
                            } else {
                                viewModel.dodaj(0)
                                viewModel.fetchCekanjeRezultata(poeni,viewModel.sifraSobe.value.sifra,
                                    uiState.duel!!.rundePoeni,viewModel.redniBroj.value.redniBroj)
                                Toast.makeText(context, "Netacan odgovor", Toast.LENGTH_SHORT).show()
                            }
                        }

                    },text = "Proveri", style = MaterialTheme.typography.bodyMedium)

                    Spacer(modifier = Modifier.height(4.dp))

                    SmallButton(onClick = {
                        viewModel.stopAudio()
                        viewModel.dodaj(0)
                        Log.d("DUEL LIS",uiState.toString())
                        if(uiState.duel?.runda!! < 7){
                            uiStateSifra.sifraResponse?.stihovi?.let {
                                viewModel.fetchDuel(uiState.duel?.runda!!,poeni,
                                    it, uiState.duel!!.rundePoeni,context)
                            }
                            navController.navigate(Destinacije.Duel.ruta+"/"+ uiState.duel?.runda!!+"/"+poeni+"/"+sifra)
                        }else {
                            navController.navigate("${Destinacije.Cekanje_rezultata.ruta}/${poeni}/${sifra}")

                        }
                    },text = "Dalje", style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(modifier = Modifier.height(22.dp))
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(LIGTH_BLUE)
                    .height(40.dp)
            ){
                Column {
                    Spacer(modifier=Modifier.padding(top=10.dp))
                    Row{
                        Spacer(modifier = Modifier.padding(start = 30.dp))
                        Text(text = "Runda: ${uiState.duel?.runda} ")
                        Spacer(modifier = Modifier.padding(start = 5.dp))
                        Text(text = "Vreme: ")
                        Text(text=" ${count.value}",color=
                        if(count.value>=50)Color.Red else
                            TEXT_COLOR)
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(end=30.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(text = "\uD834\uDD1E")
                            Text(text = " ${poeni} ")
                        }
                    }
                }
            }
        }
    }
}