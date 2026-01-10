package com.example.blankspace.screens.pocetne

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.pocetne.components.PocetnaFooterText
import com.example.blankspace.screens.pocetne.components.PocetnaHeaderText
import com.example.blankspace.ui.components.HeadlineTextWhite
import com.example.blankspace.ui.components.PrimaryGameButton
import com.example.blankspace.ui.modifiers.horizontalVerticalPadding

@Composable
fun PocetnaOffline(modifier: Modifier = Modifier, onNavigateToOffline: () -> Unit) {
    Box(modifier = modifier.fillMaxSize()) {
        BgCard2()

        Column(
            modifier = Modifier.horizontalVerticalPadding(verticalP = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            PocetnaHeaderText()

            PocetnaOfflineCard(
                onNavigateToOffline = onNavigateToOffline
            )

            PocetnaFooterText()
        }
    }
}

@Composable
private fun PocetnaOfflineCard(onNavigateToOffline: () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(36.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0DAE7)),
        shape = RoundedCornerShape(36.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeadlineTextWhite("Izaberi režim igre")

            PrimaryGameButton(text = "Offline igra", onClick = onNavigateToOffline)
        }
    }
}