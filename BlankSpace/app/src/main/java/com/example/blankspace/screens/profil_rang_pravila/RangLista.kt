package com.example.blankspace.screens.pocetne

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.ui.theme.TEXT_COLOR
import com.example.blankspace.viewModels.RangListaModel
import com.example.blankspace.viewModels.UiStateRL

@Composable
fun RangLista(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        RangLista_mainCard(navController)
    }
}

@Composable
fun RangLista_mainCard(navController:NavController) {
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight(0.7f),
        shape = RoundedCornerShape(60.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
    ) {
        val viewModel:RangListaModel = hiltViewModel()

        LaunchedEffect(Unit) {
            viewModel.fetchRangLista()
        }
        val uiState by viewModel.uiState.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Padding unutar card-a
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Centriranje sadržaja unutar card-a
        ) {
            Spacer(modifier = Modifier.height(22.dp))
            HeadlineText("Rang Lista")
            Spacer(modifier = Modifier.height(22.dp))

            RangListaCard(uiState)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun RangListaCard(uiState: UiStateRL) {
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
                    text = "Redni broj",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                Text(
                    text = "Igrac",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                Text(
                    text = "Poeni",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {

                itemsIndexed(uiState.rangLista) { index, item->
                    val backgroundColor =
                        if (index % 2 == 1) {
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
                            text = item.index.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                        Text(
                            text = item.korisnicko_ime,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                        Text(
                            text = item.rang_poeni,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}