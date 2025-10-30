package com.example.blankspace.screens.pocetne

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.RangListaModel
import com.example.blankspace.viewModels.UiStateRL

val RLCardColor = Color.White
val RLAccentColor = Color(0xFFEC8FB7)
val RLTertiaryColor = Color(0xFF49006B)

@Composable
fun RangLista(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        RangLista_mainCard(navController)
    }
}

@Composable
fun RangLista_mainCard(navController: NavController) {
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
                    text = "Rang Lista 🏆",
                    color = RLTertiaryColor,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Divider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Rank", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = RLTertiaryColor)
                    Text("Igrač", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = RLTertiaryColor)
                    Text("Poeni", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = RLTertiaryColor)
                }

                Divider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

                RangListaContent(uiState)
            }
        }
    }
}

@Composable
fun RangListaContent(uiState: UiStateRL) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp),
    ) {
        itemsIndexed(uiState.rangLista) { index, item ->
            val rankColor = when (index) {
                0 -> Color(0xFFFFD700)
                1 -> Color(0xFFC0C0C0)
                2 -> Color(0xFFA52A2A)
                else -> Color.Transparent
            }

            val textColor = if (index < 3) RLTertiaryColor else Color.Black

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(rankColor.copy(alpha = if (index < 3) 0.3f else 0.0f))
                    .padding(horizontal = 32.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = (index + 1).toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (index < 3) rankColor else Color.DarkGray
                )
                Text(
                    text = item.korisnicko_ime,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor
                )
                Text(
                    text = item.rang_poeni,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = RLAccentColor
                )
            }
            if (index < uiState.rangLista.size - 1) {
                Divider(color = Color.LightGray.copy(alpha = 0.3f), thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}