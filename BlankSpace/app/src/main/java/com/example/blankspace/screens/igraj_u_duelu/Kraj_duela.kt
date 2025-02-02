package com.example.blankspace.screens.igraj_u_duelu

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.ui.components.SmallButton
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.theme.TEXT_COLOR
import com.example.blankspace.viewModels.DuelViewModel

@Composable
fun Kraj_duela(navController: NavController,viewModelDuel:DuelViewModel){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))
        Kraj_duela_mainCard(navController,viewModelDuel)
    }
}

@Composable
fun Kraj_duela_mainCard(navController: NavController,viewModelDuel:DuelViewModel) {
    val context = LocalContext.current
    val uiStateStigaoIgrac by viewModelDuel.uiStateStigaoIgrac.collectAsState()
    val uiStateSifra by viewModelDuel.uiStateSifSobe.collectAsState()
    val uiState by viewModelDuel.uiState.collectAsState()
    val uiStateKraj by viewModelDuel.uiStateKrajDuela.collectAsState()

    LaunchedEffect(Unit) {
        //viewModelDuel.fetchKrajDuela()
    }


    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight(0.8f),
        shape = RoundedCornerShape(60.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val uiStateDuel by viewModelDuel.uiStateSifSobe.collectAsState()
            HeadlineText("Rezultat")

            Spacer(modifier = Modifier.height(34.dp))

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
                            text = "Runda",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                        Text(
                            text = "",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                        Text(
                            text = "",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                    }
                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items((1 .. 7).toList()) { index ->
                            val backgroundColor = if (index % 2 == 1) {
                                Color(0xFFF0DAE7)
                            } else {
                                Color(0xFFADD8E6)
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(backgroundColor)
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "$index", // Using $index to display the number
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Black
                                )
                            }
                        }
                    }

                }
            }

            SmallButton(onClick = {
                navController.navigate(Destinacije.Generisi_sifru_sobe.ruta)
            },text = "Igraj ponovo", style = MaterialTheme.typography.bodyMedium)

            SmallButton(onClick = {
                navController.navigate(Destinacije.Login.ruta)
            },text = "Kraj", style = MaterialTheme.typography.bodyMedium)
        }
    }
}