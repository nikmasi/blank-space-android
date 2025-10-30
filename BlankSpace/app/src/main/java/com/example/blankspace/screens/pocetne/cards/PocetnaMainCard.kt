package com.example.blankspace.screens.pocetne.cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.ui.components.HeadlineTextWhite
import kotlinx.coroutines.delay

// Uklonili smo MaxHeightForScroll jer više ne skrolujemo.

@Composable
fun PocetnaMainCard(
    navController: NavController,
    imgSize:Int,
    userName: String?,
    isLoggedIn: Boolean,
    onGameSoloClick: () -> Unit,
    onGameDuelClick: () -> Unit,
    onSuggestArtistClick :(()->Unit)? =null,
    onSuggestSongClick :(()->Unit)? =null,
    onSearchAndSuggestClick :(()->Unit)? =null,
    onGameSing :(()->Unit)? =null,
    onGameChallenge :(()->Unit)? =null
) {
    // Uklonili smo rememberScrollState() jer više ne skrolujemo.

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // 🔝 Naslov
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "BlankSpace 🎵",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Igra dopunjavanja tekstova pesama",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 16.sp
            )
        }

        // 🎮 Glavni deo - Kartica
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
                    .padding(24.dp), // Zadržavamo padding za celu karticu
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeadlineTextWhite("Izaberi režim igre")

                // Smanjen Spacer
                Spacer(modifier = Modifier.height(16.dp))

                // 🕹️ Dugme za solo mod
                GameButton(
                    text = "Igraj sam",
                    onClick = onGameSoloClick
                )

                // Smanjen Spacer
                Spacer(modifier = Modifier.height(8.dp))

                // ⚔️ Dugme za duel mod
                GameButton(
                    text = "Igraj u duelu",
                    onClick = onGameDuelClick
                )

                // --- Dodatne opcije sa smanjenim razmacima ---

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

                // Dodatni mali spacer na dnu kartice
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // 🔻 Donji tekst
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "🎧 Zabavi se i testiraj svoje muzičko znanje!",
            color = Color(0xFF49006B).copy(alpha = 0.8f),
            fontSize = 14.sp
        )
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
            fontSize = 16.sp, // SMANJEN FONT (sa 18.sp na 16.sp)
            fontWeight = FontWeight.Bold
        )
    }

    // kratak bounce efekat
    LaunchedEffect(pressed) {
        if (pressed) {
            delay(100)
            pressed = false
        }
    }
}