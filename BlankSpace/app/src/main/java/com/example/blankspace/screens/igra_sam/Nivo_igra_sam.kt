package com.example.blankspace.screens.igra_sam

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.ui.components.SmallButton
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.ui.components.HeadlineTextWhite

@Composable
fun Nivo_igra_sam(navController: NavController){
    Box(modifier = Modifier.fillMaxSize().padding(top = 52.dp)) {
        BgCard2() // pozadinska karta
        Spacer(modifier = Modifier.padding(top = 22.dp))

        Surface(
            color = Color(0xFFF0DAE7),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .fillMaxHeight(0.6f)
                .shadow(12.dp, RoundedCornerShape(36.dp)),
            shape = RoundedCornerShape(36.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Naslov
                HeadlineTextWhite("Izaberite težinu")

                Spacer(modifier = Modifier.height(32.dp))

                // Dugmići - grupisani u Column sa spacovima
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    GameButtonLarge("Easy") {
                        navController.navigate(Destinacije.Zanr_igra_sam.ruta + "/easy")
                    }
                    GameButtonLarge("Normal") {
                        navController.navigate(Destinacije.Zanr_igra_sam.ruta + "/normal")
                    }
                    GameButtonLarge("Hard") {
                        navController.navigate(Destinacije.Zanr_igra_sam.ruta + "/hard")
                    }
                }
            }
        }
    }
}

@Composable
fun Nivo_igra_sam_mainCard(navController: NavController) {
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight(0.6f),
        shape = RoundedCornerShape(60.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            NivoIgreHeader()
            Spacer(modifier = Modifier.height(42.dp))
            NivoIgreButtons(navController)
        }
    }
}

@Composable
fun NivoIgreHeader() {
    HeadlineText("Izaberite težinu")
}

@Composable
fun NivoIgreButtons(navController: NavController) {
    SmallButton(
        onClick = {
            navController.navigate(Destinacije.Zanr_igra_sam.ruta + "/easy")
        },
        text = "Easy",
        style = MaterialTheme.typography.bodyMedium
    )

    Spacer(modifier = Modifier.height(22.dp))

    SmallButton(
        onClick = {
            navController.navigate(Destinacije.Zanr_igra_sam.ruta + "/normal")
        },
        text = "Normal",
        style = MaterialTheme.typography.bodyMedium
    )

    Spacer(modifier = Modifier.height(22.dp))

    SmallButton(
        onClick = {
            navController.navigate(Destinacije.Zanr_igra_sam.ruta + "/hard")
        },
        text = "Hard",
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun GameButtonLarge(text: String, onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }

    androidx.compose.material3.Button(
        onClick = {
            pressed = true
            onClick()
        },
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = Color(0xFFEC8FB7),
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
        androidx.compose.material3.Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
    }

    // Kratak bounce efekat
    LaunchedEffect(pressed) {
        if (pressed) {
            kotlinx.coroutines.delay(100)
            pressed = false
        }
    }
}