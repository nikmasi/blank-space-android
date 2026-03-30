package com.example.blankspace.screens.igra_pogodi_i_pevaj

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.igra_sam.LevelButton
import com.example.blankspace.ui.theme.*

@Composable
fun Nivo_pogodiPevaj(onNavigateToGenre: (String) -> Unit){
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        Nivo_mainCardStyled(
            onDifficultySelected = onNavigateToGenre,
            modifier = Modifier.align(Alignment.Center),
            text="Pogodi i Pevaj"
        )
    }
}

@Composable
fun Nivo_mainCardStyled(onDifficultySelected: (String) -> Unit, modifier: Modifier = Modifier, text:String) {
    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.65f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            NivoIgreHeader(text)
            Spacer(modifier = Modifier.height(42.dp))
            NivoButtonsStyled(onDifficultySelected = onDifficultySelected)
        }
    }
}

@Composable
fun NivoIgreHeader(text:String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Izaberi težinu",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = PrimaryDark
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = AccentPink
        )
    }
}

@Composable
fun NivoButtonsStyled(onDifficultySelected: (String) -> Unit) {
    LevelButton(text = "Easy", color = Color(0xFF5AB1BB)) { onDifficultySelected("easy")}
    Spacer(modifier = Modifier.height(20.dp))
    LevelButton(text = "Normal", color = AccentPink) { onDifficultySelected("normal") }
    Spacer(modifier = Modifier.height(20.dp))
    LevelButton(text = "Hard", color = PrimaryDark) { onDifficultySelected("hard") }
}