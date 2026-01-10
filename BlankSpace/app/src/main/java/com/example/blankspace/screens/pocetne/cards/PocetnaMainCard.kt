package com.example.blankspace.screens.pocetne.cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blankspace.screens.pocetne.components.PocetnaFooterText
import com.example.blankspace.screens.pocetne.components.PocetnaHeaderText
import com.example.blankspace.ui.components.HeadlineTextWhite
import com.example.blankspace.ui.modifiers.horizontalVerticalPadding
import kotlinx.coroutines.delay

@Composable
fun PocetnaMainCard(
    onGameSoloClick: () -> Unit,
    onGameDuelClick: () -> Unit,
    onSuggestArtistClick :(()->Unit)? =null,
    onSuggestSongClick :(()->Unit)? =null,
    onSearchAndSuggestClick :(()->Unit)? =null,
    onGameSing :(()->Unit)? =null,
    onGameChallenge :(()->Unit)? =null
) {

    Column(
        modifier = Modifier.horizontalVerticalPadding(verticalP = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        PocetnaHeaderText()

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(12.dp, RoundedCornerShape(36.dp)),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0DAE7)),
            shape = RoundedCornerShape(36.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeadlineTextWhite("Izaberi režim igre")

                Spacer(modifier = Modifier.height(16.dp))

                GameButton(
                    text = "Igraj sam",
                    onClick = onGameSoloClick
                )

                Spacer(modifier = Modifier.height(8.dp))

                GameButton(
                    text = "Igraj u duelu",
                    onClick = onGameDuelClick
                )

                onGameSing?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    GameButton(
                        text = "Pogodi i pevaj",
                        onClick = onGameSing
                    )
                }

                onGameChallenge?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    GameButton(
                        text = "Challenge",
                        onClick = onGameChallenge
                    )
                }

                onSuggestSongClick?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    GameButton(
                        text = "Predloži pesmu",
                        onClick = onSuggestSongClick
                    )
                }

                onSuggestArtistClick?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    GameButton(
                        text = "Predloži izvođača",
                        onClick = onSuggestArtistClick
                    )
                }

                onSearchAndSuggestClick?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    GameButton(
                        text = "Pretraži i predloži",
                        onClick = onSearchAndSuggestClick
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        PocetnaFooterText()
    }
}

@Composable
fun GameButton(
    text: String,
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }

    Button(
        onClick = {
            pressed = true
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFEC8FB7),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .graphicsLayer {
                scaleX = if (pressed) 0.97f else 1f
                scaleY = if (pressed) 0.97f else 1f
            }
            .shadow(10.dp, RoundedCornerShape(50))
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }

    LaunchedEffect(pressed) {
        if (pressed) {
            delay(100)
            pressed = false
        }
    }
}