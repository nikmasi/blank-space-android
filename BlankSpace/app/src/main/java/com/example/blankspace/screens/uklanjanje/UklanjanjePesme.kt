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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.blankspace.data.retrofit.models.PesmeIzvodjaca
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.theme.TEXT_COLOR
import com.example.blankspace.viewModels.UiStateUklanjanjePesme
import com.example.blankspace.viewModels.UklanjanjeViewModel
import com.example.blankspace.viewModels.ZanrViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun UklanjanjePesme(navController: NavController,viewModelUklanjanje: UklanjanjeViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        UklanjanjePesme_mainCard(navController,viewModelUklanjanje)
    }
}

@Composable
fun UklanjanjePesme_mainCard(navController:NavController,viewModelUklanjanje: UklanjanjeViewModel) {
    val viewModel:ZanrViewModel = hiltViewModel()
    val uiStateUklanjanje by viewModelUklanjanje.uiStatePesma.collectAsState()
    val uiStatePesmeIzv by viewModelUklanjanje.uiStatePesmeIzv.collectAsState()
    val uiState by viewModelUklanjanje.uiStatePesmeIzvodjaca.collectAsState()

    val context= LocalContext.current
    var izv by remember { mutableStateOf(-1) }
    var ime by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        izv= uiStatePesmeIzv.izvodjac_id!!
        ime= uiStatePesmeIzv.ime!!
        uiStatePesmeIzv.ime?.let {
            uiStatePesmeIzv.izvodjac_id?.let { it1 ->
                viewModelUklanjanje.dohvatiPesmeIzvodjaca(
                    it1,
                    it,-1)
            }
        }
    }

    HandleUklanjanjePesmeResponse(viewModelUklanjanje, context, uiStateUklanjanje, izv, ime)

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
                .padding(16.dp), // Padding unutar card-a
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Centriranje sadržaja unutar card-a
        ) {
            Spacer(modifier = Modifier.height(22.dp))
            HeadlineText("Uklanjanje pesama")
            Spacer(modifier = Modifier.height(22.dp))

            SongRemovalCard(
                pesmeIzvodjaca = uiState.pesmeIzvodjaca,
                onRemove = { id -> viewModelUklanjanje.fetchUklanjanjePesme(id) }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun HandleUklanjanjePesmeResponse(
    viewModelUklanjanje: UklanjanjeViewModel,
    context: Context,
    uiStateUklanjanje:UiStateUklanjanjePesme,
    izv:Int,
    ime:String
){
    LaunchedEffect(uiStateUklanjanje.uklanjanjePesme?.odgovor) {
        val odgovor = uiStateUklanjanje.uklanjanjePesme?.odgovor
        if (!odgovor.isNullOrEmpty()) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, odgovor, Toast.LENGTH_SHORT).show()
            }
            viewModelUklanjanje.dohvatiPesmeIzvodjaca(izv, ime,-1)
            return@LaunchedEffect
        }
    }
}
@Composable
fun SongRemovalCard(pesmeIzvodjaca: List<PesmeIzvodjaca>, onRemove: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .border(3.dp, TEXT_COLOR, RoundedCornerShape(3.dp))
                .background(Color(0xFFF0DAE7))
        ) {
            SongRemovalHeader()

            LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
                itemsIndexed(pesmeIzvodjaca) { index, item ->
                    val backgroundColor = if (index % 2 == 1) Color(0xFFF0DAE7) else Color(0xFFADD8E6)
                    SongRemovalRow(pesma = item, backgroundColor = backgroundColor, onRemove = onRemove)
                }
            }
        }
    }
}

@Composable
fun SongRemovalHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0DAE7))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Pesma", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
        Text(text = "Ukloni?", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
    }
}

@Composable
fun SongRemovalRow(pesma: PesmeIzvodjaca, backgroundColor: Color, onRemove: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = pesma.naziv ?: "Nepoznato", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
        Button(onClick = { onRemove(pesma.id) }) {
            Text("Ukloni")
        }
    }
}