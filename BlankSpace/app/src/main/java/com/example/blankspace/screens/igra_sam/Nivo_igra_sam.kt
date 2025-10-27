package com.example.blankspace.screens.igra_sam

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.navigation.NavController
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.ui.components.SmallButton
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.ui.components.HeadlineTextWhite
import kotlinx.coroutines.delay

// Boje za usklađivanje
private val PrimaryDark = Color(0xFF49006B)       // Tamno ljubičasta/Magenta
private val CardContainerColor = Color(0xFFF0DAE7) // Svetlo roze (za karticu)
private val AccentPink = Color(0xFFEC8FB7)       // Roze za Normal dugme

@Composable
fun Nivo_igra_sam(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            // Malo smanjen top padding radi lepše centriranosti
            .padding(top = 30.dp)
    ) {
        BgCard2() // Pozadinska karta (Gradijent)

        // Centrirana kartica sa izborom težine
        Surface(
            // KARTICA JE SVETLO ROZE (Kao što ste tražili)
            color = CardContainerColor,
            modifier = Modifier
                .align(Alignment.Center) // Centriramo karticu
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .fillMaxHeight(0.55f)
                // Koristimo jaču senku
                .shadow(16.dp, RoundedCornerShape(28.dp)),
            shape = RoundedCornerShape(28.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround // Raspored oko dugmadi
            ) {
                // Naslov - koristi tamnu PrimaryDark boju za dobar kontrast na svetlo roze
                Text(
                    text = "Izaberite težinu",
                    color = PrimaryDark,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(top = 8.dp)
                )

                // Dugmići - grupisani
                Column(
                    verticalArrangement = Arrangement.spacedBy(18.dp), // Veći razmak
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // EASY - Svetlija, kontrastna boja
                    LevelButton(text = "Easy", color = Color(0xFF5AB1BB)) {
                        navController.navigate(Destinacije.Zanr_igra_sam.ruta + "/easy")
                    }
                    // NORMAL - Naglašena roze boja
                    LevelButton(text = "Normal", color = AccentPink) {
                        navController.navigate(Destinacije.Zanr_igra_sam.ruta + "/normal")
                    }
                    // HARD - Tamna PrimaryDark boja
                    LevelButton(text = "Hard", color = PrimaryDark) {
                        navController.navigate(Destinacije.Zanr_igra_sam.ruta + "/hard")
                    }
                }
            }
        }
    }
    // Napomena: Stari, suvišni kodovi (Nivo_igra_sam_mainCard, NivoIgreButtons, itd.) su uklonjeni.
}

/**
 * Poboljšana i usklađena komponenta dugmeta za nivo.
 */
@Composable
fun LevelButton(text: String, color: Color, onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    // Visina senke se menja sa pritiskom (moderniji bounce efekat)
    val elevation = if (pressed) 2.dp else 8.dp

    Button(
        onClick = {
            pressed = true
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = color, // Boja definisana za nivo
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp), // Moderniji ugao
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .shadow(elevation, RoundedCornerShape(16.dp))
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }

    // Kratak bounce efekat
    LaunchedEffect(pressed) {
        if (pressed) {
            delay(100)
            pressed = false
        }
    }
}