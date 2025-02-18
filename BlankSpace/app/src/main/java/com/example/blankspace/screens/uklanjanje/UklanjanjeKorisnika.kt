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
import com.example.blankspace.data.retrofit.models.KorisniciResponse
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.theme.TEXT_COLOR
import com.example.blankspace.ui.theme.TopAppBarHeight
import com.example.blankspace.viewModels.KorisniciViewModel
import com.example.blankspace.viewModels.UiStateUklanjanjeKorisnika
import com.example.blankspace.viewModels.UklanjanjeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun UklanjanjeKorisnika(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize().padding(top= TopAppBarHeight +16.dp)) {
        BgCard2()
        UklanjanjeKorisnika_mainCard(navController)
    }
}

@Composable
fun UklanjanjeKorisnika_mainCard(navController:NavController) {
    val viewModel:KorisniciViewModel = hiltViewModel()
    val viewModelUklanjanje: UklanjanjeViewModel= hiltViewModel()

    val uiState by viewModel.uiState.collectAsState()
    val uiStateUklanjanje by viewModelUklanjanje.uiState.collectAsState()
    val context= LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchKorisnici()
    }

    HandleUklanjanjeKorisnikaResponse(viewModel, uiStateUklanjanje, context)

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight(0.98f),
        shape = RoundedCornerShape(60.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding( bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(22.dp))
            HeadlineText("Uklanjanje korisnika")
            Spacer(modifier = Modifier.height(22.dp))

            UserRemovalCard(
                korisnici = uiState.korisnici,
                onRemove = { username -> viewModelUklanjanje.fetchUklanjanjeKorisnika(username) }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun HandleUklanjanjeKorisnikaResponse(
    viewModel: KorisniciViewModel,
    uiStateUklanjanje: UiStateUklanjanjeKorisnika,
    context: Context
){
    LaunchedEffect(uiStateUklanjanje.uklanjanjeKorisnika?.odgovor) {
        val odgovor = uiStateUklanjanje.uklanjanjeKorisnika?.odgovor
        if (!odgovor.isNullOrEmpty()) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, odgovor, Toast.LENGTH_SHORT).show()
            }
            viewModel.fetchKorisnici()
            return@LaunchedEffect
        }
    }
}

@Composable
fun UserRemovalCard(korisnici: List<KorisniciResponse>, onRemove: (String) -> Unit) {
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
            UserRemovalHeader()

            LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
                itemsIndexed(korisnici) { index, item ->
                    val backgroundColor = if (index % 2 == 1) Color(0xFFF0DAE7) else Color(0xFFADD8E6)
                    UserRemovalRow(
                        korisnik = item,
                        backgroundColor = backgroundColor,
                        onRemove = onRemove
                    )
                }
            }
        }
    }
}

@Composable
fun UserRemovalHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0DAE7))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Korisničko ime:", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
        Text(text = "Datum", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
        Text(text = "Ukloni?", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
    }
}

@Composable
fun UserRemovalRow(korisnik: KorisniciResponse, backgroundColor: Color, onRemove: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = korisnik.korisnicko_ime, style = MaterialTheme.typography.bodyMedium, color = Color.Black)
        Text(text = korisnik.poslednja_aktivnost.toString(), style = MaterialTheme.typography.bodyMedium, color = Color.Black)
        Button(onClick = { onRemove(korisnik.korisnicko_ime) }) {
            Text("Ukloni")
        }
    }
}
