package com.example.blankspace.screens.uklanjanje

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.theme.TEXT_COLOR
import com.example.blankspace.viewModels.PredloziViewModel

@Composable
fun PredloziIzvodjaca(navController: NavController,viewModelPredlozi:PredloziViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        PredloziIzvodjaca_mainCard(navController,viewModelPredlozi)
    }
}

@Composable
fun PredloziIzvodjaca_mainCard(navController:NavController,viewModelPredlozi:PredloziViewModel) {
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight(0.98f),
        shape = RoundedCornerShape(60.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
    ) {
        val context= LocalContext.current

        LaunchedEffect(Unit) {
            viewModelPredlozi.fetchPredloziIzvodjaca()
        }

        val uiState by viewModelPredlozi.uiState.collectAsState()
        LaunchedEffect(uiState.predloziIzvodjaca) {
            //val odgovor = uiState.predloziIzvodjaca[0].odgovor
            /*
            if (!odgovor.isNullOrEmpty()) {
                if (odgovor.contains("Nema novih predloga")) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, odgovor, Toast.LENGTH_LONG).show()
                    }
                    return@LaunchedEffect
                }
            }*/
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top=12.dp, bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                text = "Predlozi izvođača",
                style = MaterialTheme.typography.headlineSmall,
                color = TEXT_COLOR
            )
            Spacer(modifier = Modifier.height(22.dp))
            // Profil Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp).border(3.dp, TEXT_COLOR, RoundedCornerShape(3.dp))
                        .background(Color(0xFFF0DAE7))
                ) {
                    // LazyColumn sa naizmeničnim redovima boja
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF0DAE7))
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Izvođač",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                        Text(
                            text = "Žanr"// poslednje aktivnosti"
                            ,style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                        Text(
                            text = "Korisnik"// poslednje aktivnosti"
                            ,style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                        Text(
                            text = "Prihvati?"// poslednje aktivnosti"
                            ,style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                        Text(
                            text = "Ukloni?",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                    }
                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {

                        itemsIndexed(uiState.predloziIzvodjaca) { index, item->
                            val backgroundColor =
                                if (index % 2 == 1) {
                                    Color(0xFFF0DAE7)
                                } else {
                                    Color(0xFFADD8E6)
                                }
                            Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(backgroundColor)
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = item.ime_izvodjaca,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Black
                                )
                                Text(
                                    text = item.zan_naziv,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Black
                                )
                                Text(
                                    text = item.kor_ime,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Black
                                )

                            }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(backgroundColor)
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Button(
                                        onClick = {
                                            viewModelPredlozi.sacuvajIzvodjacPredlozi(item.id,item.ime_izvodjaca,item.zan_naziv,item.kor_ime)
                                            navController.navigate(Destinacije.PesmaPodaci.ruta)
                                        }
                                    ) {
                                        Text("Prihvati")
                                    }

                                    Button(
                                        onClick = {
                                            viewModelPredlozi.odbijPredlogIzvodjaca(item.id)
                                        }
                                    ) {
                                        Text("Odbij")
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}