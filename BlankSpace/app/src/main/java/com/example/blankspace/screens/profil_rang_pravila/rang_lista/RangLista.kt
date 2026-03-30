package com.example.blankspace.screens.pocetne

import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.blankspace.R
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.profil_rang_pravila.rang_lista.RangListaContent
import com.example.blankspace.viewModels.RangListaModel
import com.example.blankspace.ui.theme.*

@Composable
fun RangLista() {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        RangLista_mainCard()
    }
}

@Composable
fun RangLista_mainCard() {
    val viewModel: RangListaModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.fetchRangLista()
    }
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(28.dp))

        Spacer(modifier = Modifier.height(44.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(16.dp, RoundedCornerShape(36.dp)),
            colors = CardDefaults.cardColors(containerColor = RLCardColor), // Čisto bela
            shape = RoundedCornerShape(36.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.rank_leaderboard),
                    color = RLTertiaryColor,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = Color.LightGray.copy(alpha = 0.5f)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text=stringResource(id = R.string.rank_rank), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = RLTertiaryColor)
                    Text(text=stringResource(id = R.string.rank_player), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = RLTertiaryColor)
                    Text(text=stringResource(id = R.string.rank_points), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = RLTertiaryColor)
                }

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = Color.LightGray.copy(alpha = 0.5f)
                )
                
                RangListaContent(uiState)
            }
        }
    }
}