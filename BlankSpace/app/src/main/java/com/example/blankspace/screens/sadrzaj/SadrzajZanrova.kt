package com.example.blankspace.screens.sadrzaj

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.blankspace.R
import com.example.blankspace.data.retrofit.models.Zanr
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.UiStateZ
import com.example.blankspace.viewModels.ZanrViewModel
import com.example.blankspace.ui.theme.*

@Composable
fun SadrzajZanrova() {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        SadrzajZanrova_mainCard(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun SadrzajZanrova_mainCard(modifier: Modifier) {
    val viewModel: ZanrViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) { viewModel.fetchCategories() }

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.7f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 32.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            SadrzajHeader(
                text1 = stringResource(id = R.string.genres_overview),
                text2 = stringResource(id = R.string.list_of_all_genres)
            )
            Spacer(modifier = Modifier.height(16.dp))

            ZanroviListaStyled(uiState = uiState)
        }
    }
}


@Composable
fun ZanroviListaStyled(uiState: UiStateZ) {
    when {
        uiState.isRefreshing -> { CircularProgressIndicator(color = AccentPink) }
        uiState.error != null -> {
            Text(text = "Greška: ${uiState.error}", color = Color.Red, modifier = Modifier.padding(16.dp))
        }
        uiState.zanrovi.isEmpty() -> {
            Text(
                text = "Nema žanrova za prikaz.",
                color = PrimaryDark.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
        else -> {
            LazyColumn(
                modifier = Modifier.fillMaxHeight(0.8f).padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(uiState.zanrovi) { item -> SadrzajZanrCard(item = item) }
            }
        }
    }
}

@Composable
fun SadrzajZanrCard(item: Zanr) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(color = LightBackground, shape = RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = PrimaryDark.copy(alpha = 0.3f), shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = item.naziv.toString(),
            color = PrimaryDark,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}