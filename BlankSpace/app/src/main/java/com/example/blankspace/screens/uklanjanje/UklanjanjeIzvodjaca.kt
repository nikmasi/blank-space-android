package com.example.blankspace.screens.uklanjanje

import android.content.Context
import android.widget.Toast
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
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.theme.TEXT_COLOR
import com.example.blankspace.viewModels.IzvodjaciZanraViewModel
import com.example.blankspace.viewModels.UiStateUklanjanjeIzvodjaca
import com.example.blankspace.viewModels.UklanjanjeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun UklanjanjeIzvodjaca(navController: NavController, zanr:Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        UklanjanjeIzvodjaca_mainCard(navController,zanr)
    }
}

@Composable
fun UklanjanjeIzvodjaca_mainCard(navController:NavController,zanr: Int) {
    val viewModel:IzvodjaciZanraViewModel = hiltViewModel()
    val viewModelUklanjanje: UklanjanjeViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsState()
    val uiStateUklanjanje by viewModelUklanjanje.uiStateIzvodjac.collectAsState()
    val context= LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchIzvodjaciZanra(zanr)
    }

    HandleUklanjanjeIzvodjacaResponse(context,viewModel,uiStateUklanjanje,zanr)

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
            Spacer(modifier = Modifier.height(22.dp))
            HeadlineText("Uklanjanje izvođača")
            Spacer(modifier = Modifier.height(22.dp))

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
                            text = "Žanr",
                            style = MaterialTheme.typography.bodyMedium,
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
                        itemsIndexed(uiState.izvodjaci) { index, item->
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
                                    text = item.ime.toString(),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Black
                                )
                                Button(
                                    onClick = {
                                        viewModelUklanjanje.fetchUklanjanjeIzvodjac(item.id)
                                    }
                                ) {
                                    Text("Ukloni")
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

@Composable
fun HandleUklanjanjeIzvodjacaResponse(
    context: Context,
    viewModel: IzvodjaciZanraViewModel,
    uiStateUklanjanje: UiStateUklanjanjeIzvodjaca,
    zanr: Int
){
    LaunchedEffect(uiStateUklanjanje.uklanjanjeIzvodjaca?.odgovor) {
        val odgovor = uiStateUklanjanje.uklanjanjeIzvodjaca?.odgovor
        if (!odgovor.isNullOrEmpty()) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, odgovor, Toast.LENGTH_SHORT).show()
            }
            viewModel.fetchIzvodjaciZanra(zanr)
            return@LaunchedEffect
        }
    }
}