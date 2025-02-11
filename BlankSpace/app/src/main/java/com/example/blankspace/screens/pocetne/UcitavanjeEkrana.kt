package com.example.blankspace.screens.pocetne

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.R
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.ui.theme.TEXT_COLOR
import com.example.blankspace.viewModels.LoginViewModel
import kotlinx.coroutines.delay

@Composable
fun UcitavanjeEkrana(modifier: Modifier = Modifier, navController: NavController,loginViewModel: LoginViewModel) {
    Box(modifier = modifier.fillMaxSize()) {
        BgCard2()
        UcitavanjeEkrana_main(navController,loginViewModel)
    }
}

@Composable
fun UcitavanjeEkrana_main(navController: NavController,loginViewModel: LoginViewModel) {
    val isLoading = remember { mutableStateOf(true) }
    val uiStateLogin by loginViewModel.uiState.collectAsState()

    LaunchedEffect(true) {
        delay(3000)
        isLoading.value = false
        //navController.navigate(Destinacije.Login.ruta)
        Log.d("LOGIN ",uiStateLogin.toString())
        if(uiStateLogin.login?.access!=null){
            uiStateLogin.login?.tip?.let {
                when (it) {
                    "S" -> navController.navigate(Destinacije.PocetnaStudent.ruta)
                    "B" -> navController.navigate(Destinacije.PocetnaBrucos.ruta)
                    "M" -> navController.navigate(Destinacije.PocetnaMaster.ruta)
                    "A" -> navController.navigate(Destinacije.PocetnaAdmin.ruta)

                }
            }
        }else{
            navController.navigate(Destinacije.Login.ruta)
        }

    }

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight(0.99f),
        shape = RoundedCornerShape(60.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val infiniteTransition = rememberInfiniteTransition()
            val scale by infiniteTransition.animateFloat(
                initialValue = 2.6f,
                targetValue = 3.1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            // Animated text: "Cause I’ve got a blank space, baby, and I’ll write your name"
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            Text(
                text = "Cause I’ve got a ",
                style = TextStyle(
                    fontSize = 25.sp,
                    color = TEXT_COLOR,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .alpha(alpha) // Apply the alpha animation to the text
            )

            Image(
                painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                contentDescription = "Učitavanje...",
                modifier = Modifier
                    .size(100.dp * scale)
                    .padding(16.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = "baby,",
                style = TextStyle(
                    fontSize = 25.sp,
                    color = TEXT_COLOR,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .alpha(alpha) // Apply the alpha animation to the text
            )
            Text(
                text = "and I’ll write your name",
                style = TextStyle(
                    fontSize = 25.sp,
                    color = TEXT_COLOR,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .alpha(alpha) // Apply the alpha animation to the text
            )
        }
    }
}
