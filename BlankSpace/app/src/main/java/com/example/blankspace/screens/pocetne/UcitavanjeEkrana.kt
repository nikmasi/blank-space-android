package com.example.blankspace.screens.pocetne

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blankspace.R
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.components.HeadlineText

@Composable
fun UcitavanjeEkrana(modifier: Modifier = Modifier,navController: NavController) {
    Box(modifier = modifier.fillMaxSize()) {
        BgCard2()
        UcitavanjeEkrana_main(navController)
    }
}

@Composable
fun UcitavanjeEkrana_main(navController:NavController) {
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(true) {
        delay(2000)
        isLoading.value = false
        navController.navigate(Destinacije.Login.ruta)
    }
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp) // Dodajte padding sa strane
            .fillMaxHeight(0.99f), // Ova linija čini visinu fleksibilnom (70% ekrana)
        shape = RoundedCornerShape(60.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HeadlineText("BLANK SPACE")

            val infiniteTransition = rememberInfiniteTransition()
            val scale by infiniteTransition.animateFloat(
                initialValue = 2.6f,
                targetValue = 3.1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
            Image(
                painter = painterResource(id = R.mipmap.ic_launcher_foreground), // Zamenite sa ID vaše slike
                contentDescription = "Učitavanje...",
                modifier = Modifier
                    .size(100.dp * scale)  // Animirajte veličinu slike
                    .padding(16.dp),  // Dodajte padding oko slike
                contentScale = ContentScale.Fit // Možete menjati contentScale (Fit, Crop, Inside...)
            )
        }
    }
}