package com.example.blankspace.screens.sadrzaj


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.blankspace.data.retrofit.models.Zanr
import com.example.blankspace.data.room.SobaEntity
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.DatabaseViewModel
import com.example.blankspace.viewModels.UiStateZ
import com.example.blankspace.viewModels.ZanrViewModel

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)
private val LightBackground = Color(0xFFF7F7F7)

@Composable
fun SadrzajSoba(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        SadrzajSoba_mainCard(
            navController = navController,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun SadrzajSoba_mainCard(navController: NavController, modifier: Modifier) {
    val viewModel: ZanrViewModel = hiltViewModel()
    val databaseViewModel:DatabaseViewModel = hiltViewModel()

    val uiState by databaseViewModel.allSobe.collectAsState(initial = emptyList())
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchCategories()
    }

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.7f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            SadrzajSobaHeader()
            Spacer(modifier = Modifier.height(16.dp))

            SobeListaStyled(uiState = uiState)
        }
    }
}


@Composable
fun SobeListaStyled(
    uiState: List<SobaEntity>
) {
    when {

        uiState.isEmpty() -> {
            Text(
                text = "Nema soba za prikaz ili greska.",
                color = PrimaryDark.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp) // Razmak između kartica
            ) {
                items(uiState) { item ->
                    SadrzajSobaCard(item = item)
                }
            }
        }
    }
}

@Composable
fun SadrzajSobaCard(item: SobaEntity) {
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(
                color = LightBackground,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = PrimaryDark.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = item.id.toString()+ " "+item.stihovi.toString(),
            color = PrimaryDark,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
fun SadrzajSobaHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Pregled soba",
            color = PrimaryDark,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Spisak svih soba u sistemu.",
            style = MaterialTheme.typography.bodyMedium,
            color = PrimaryDark.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}