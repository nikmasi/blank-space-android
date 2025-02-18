package com.example.blankspace.screens.uklanjanje

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
import com.example.blankspace.data.retrofit.models.PredloziPesamaResponse
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.ui.components.SmallButton
import com.example.blankspace.ui.theme.TEXT_COLOR
import com.example.blankspace.ui.theme.TopAppBarHeight
import com.example.blankspace.viewModels.PredloziViewModel


@Composable
fun PredloziPesama(navController: NavController,viewModelPredlozi:PredloziViewModel) {
    Box(modifier = Modifier.fillMaxSize().padding(top= TopAppBarHeight +30.dp)) {
        BgCard2()
        PredloziPesama_mainCard(navController,viewModelPredlozi)
    }
}

@Composable
fun PredloziPesama_mainCard(navController:NavController,viewModelPredlozi:PredloziViewModel) {
    val context= LocalContext.current
    val viewModel:PredloziViewModel = hiltViewModel()
    val uiState by viewModel.uiStateP.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPredloziPesama()
    }

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight(0.95f),
        shape = RoundedCornerShape(60.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HeadlineText("Predlozi pesama")
            Spacer(modifier = Modifier.height(12.dp))

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
                    HeaderPredloziPesama()

                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {

                        itemsIndexed(uiState.predloziPesama) { index, item->
                            val backgroundColor =
                                if (index % 2 == 1) {
                                    Color(0xFFF0DAE7)
                                } else {
                                    Color(0xFFADD8E6)
                                }

                            ItemPredloziPesama(item,backgroundColor,navController,viewModel)

                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun HeaderPredloziPesama(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0DAE7))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Pesma",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
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
}

@Composable
fun ItemPredloziPesama(
    item:PredloziPesamaResponse,
    backgroundColor:Color,
    navController: NavController,
    viewModel: PredloziViewModel
){
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.naziv_pesme,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Text(
                text = item.izv_ime,
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
            val buttonStyle = MaterialTheme.typography.bodyMedium

            SmallButton(onClick = {
                navController.navigate(Destinacije.PesmaPodaci2.ruta)
            }, text = "Prihvati", style = buttonStyle)

            SmallButton(onClick = {
                viewModel.odbijPredlogPesme(item.id)
            }, text = "Odbij", style = buttonStyle)
        }
    }
}
