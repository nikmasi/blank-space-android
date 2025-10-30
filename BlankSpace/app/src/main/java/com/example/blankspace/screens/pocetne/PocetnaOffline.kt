package com.example.blankspace.screens.pocetne

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.components.HeadlineTextWhite
import com.example.blankspace.viewModels.LoginViewModel

private val AccentPink = Color(0xFFEC8FB7)

@Composable
fun PocetnaOffline(modifier: Modifier = Modifier, navController: NavController,viewModelLogin:LoginViewModel) {
    Box(modifier = modifier.fillMaxSize()) {
        BgCard2()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
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
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            navController.navigate(Destinacije.Nivo_igra_offline.ruta)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentPink,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(52.dp)
                    ) {
                        Text(
                            text = "Offline igra",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "🎧 Zabavi se i testiraj svoje muzičko znanje!",
                color = Color(0xFF49006B).copy(alpha = 0.8f),
                fontSize = 14.sp
            )
        }
    }
}