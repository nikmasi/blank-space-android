package com.example.blankspace.screens.pocetne.cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.ui.components.HeadlineTextWhite
import com.example.blankspace.ui.components.MyImage
import com.example.blankspace.ui.components.SmallButton
import kotlinx.coroutines.delay

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

        // 🎮 Glavni deo
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
                Spacer(modifier = Modifier.height(24.dp))

                // 🕹️ Dugme za solo mod
                GameButton(
                    text = "Igraj sam",
                    icon = Icons.Default.Edit,
                    onClick = onGameSoloClick
                )

                Spacer(modifier = Modifier.height(16.dp))

                // ⚔️ Dugme za duel mod
                GameButton(
                    text = "Igraj u duelu",
                    icon = Icons.Default.MoreVert,
                    onClick = onGameDuelClick
                )


                onGameSing?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    GameButton(
                        text = "Pogodi i pevaj",
                        icon = Icons.Default.MoreVert,
                        onClick = onGameSing
                    )
                }


                onGameChallenge?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    GameButton(
                        text = "Challenge",
                        icon = Icons.Default.MoreVert,
                        onClick = onGameChallenge
                    )
                }
                onSuggestSongClick?.let {
                    Spacer(modifier = Modifier.height(16.dp))

                    GameButton(
                        text = "Prezlozi pesmu",
                        icon = Icons.Default.MoreVert,
                        onClick = onSuggestSongClick
                    )
                }
                onSuggestArtistClick?.let {
                    Spacer(modifier = Modifier.height(16.dp))

                    GameButton(
                        text = "Prezlozi izvodjaca",
                        icon = Icons.Default.MoreVert,
                        onClick = onSuggestArtistClick
                    )
                }
                onSearchAndSuggestClick?.let {
                    Spacer(modifier = Modifier.height(16.dp))

                    GameButton(
                        text = "Pretrazi i predlozi",
                        icon = Icons.Default.MoreVert,
                        onClick = onSearchAndSuggestClick
                    )
                }
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
    icon: ImageVector,
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }

    Button(
        onClick = {
            pressed = true
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFEC8FB7), // roza dugme
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .graphicsLayer {
                scaleX = if (pressed) 0.97f else 1f
                scaleY = if (pressed) 0.97f else 1f
            }
            .shadow(10.dp, RoundedCornerShape(50))
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
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