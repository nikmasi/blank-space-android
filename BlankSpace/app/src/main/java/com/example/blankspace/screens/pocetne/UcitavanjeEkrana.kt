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
import androidx.compose.ui.platform.LocalContext
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
import android.media.AudioAttributes
import android.media.SoundPool
import android.media.MediaPlayer
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.blankspace.hasDownloadedData
import com.example.blankspace.isInternetAvailable
import com.example.blankspace.viewModels.DatabaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blankspace.hasDownloadedData
import com.example.blankspace.isInternetAvailable
import com.example.blankspace.screens.pocetne.cards.BgCard2
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

// Boje za usklađivanje
private val PrimaryDark = Color(0xFF49006B)       // Tamno ljubičasta/Magenta
private val CardContainerColor = Color(0xFFF0DAE7) // Svetlo roze (za karticu)
private val DarkOverlay = Color(0xFF330044) // Još tamnija boja za efekat zavese

@Composable
fun UcitavanjeEkrana(modifier: Modifier = Modifier, navController: NavController, loginViewModel: LoginViewModel, databaseViewModel: DatabaseViewModel) {
    Box(modifier = modifier.fillMaxSize()) {
        BgCard2() // Pozadina sa gradijentom

        // Glavni sadržaj ekrana za učitavanje, centriran unutar Box-a
        UcitavanjeEkrana_main(
            navController = navController,
            loginViewModel = loginViewModel,
            databaseViewModel = databaseViewModel,
            modifier = Modifier.align(Alignment.Center) // Centriranje!
        )
    }
}

@Composable
fun UcitavanjeEkrana_main(navController: NavController, loginViewModel: LoginViewModel, databaseViewModel: DatabaseViewModel, modifier: Modifier) {
    val context = LocalContext.current
    val isLoadingData = remember { mutableStateOf(true) }
    val uiStateLogin by loginViewModel.uiState.collectAsState()

    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.taylorswiftblankspace2) }
    val animationFinished = remember { mutableStateOf(false) }

    // Efekat za pokretanje muzike i navigaciju
    LaunchedEffect(true) {
        mediaPlayer.start()
        delay(5400)
        mediaPlayer.stop()
        mediaPlayer.release()

        // Sačekaj da se animacija završi (dajemo joj ~1.5s)
        animationFinished.value = true
        delay(1500)

        isLoadingData.value = false

        // Logika za internet i navigaciju (ista kao prethodno)
        if (isInternetAvailable(context)) {
            if (!hasDownloadedData(context)) {
                withContext(Dispatchers.IO) {
                    databaseViewModel.loadZanrovi()
                    databaseViewModel.loadIzvodjaci()
                    databaseViewModel.loadPesme()
                    databaseViewModel.loadStihovi()
                    context.getSharedPreferences("app_prefs", 0).edit().putBoolean("has_downloaded_data", true).apply()
                }
            }
            withContext(Dispatchers.Main) {
                if (uiStateLogin.login?.access != null) {
                    when (uiStateLogin.login?.tip) {
                        "S" -> navController.navigate(Destinacije.PocetnaStudent.ruta)
                        "B" -> navController.navigate(Destinacije.PocetnaBrucos.ruta)
                        "M" -> navController.navigate(Destinacije.PocetnaMaster.ruta)
                        "A" -> navController.navigate(Destinacije.PocetnaAdmin.ruta)
                        else -> navController.navigate(Destinacije.Login.ruta)
                    }
                } else {
                    navController.navigate(Destinacije.Login.ruta)
                }
            }
        } else {
            if (hasDownloadedData(context)) {
                Log.d("AppInit", "Offline mode active, using local data")
                withContext(Dispatchers.Main) {
                    navController.navigate(Destinacije.PocetnaOffline.ruta)
                }
            } else {
                Log.e("AppInit", "No internet and no local data")
                withContext(Dispatchers.Main) {
                    navController.navigate(Destinacije.PocetnaOffline.ruta)
                }
                Toast.makeText(context, "Nema interneta. Aplikacija se može pokrenuti u offline rezimu.", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Animacije za UI elemente
    val infiniteTransition = rememberInfiniteTransition(label = "loading_screen_animations")

    val iconScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "icon_scale_animation"
    )

    // Animacija 'Zavese' (wipe effect)
    val wipeProgress by animateFloatAsState(
        targetValue = if (animationFinished.value) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1200,
            easing = FastOutSlowInEasing
        )
    )

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.85f) // Veća širina kartice
            .fillMaxHeight(0.65f) // Veća visina
            .shadow(20.dp, RoundedCornerShape(32.dp)), // Još jača senka
        shape = RoundedCornerShape(32.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp, vertical = 40.dp), // Veći padding
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // 1. Ikonica (iznad teksta)
                Image(
                    painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                    contentDescription = "BlankSpace Logo",
                    modifier = Modifier
                        .size(100.dp)
                        .graphicsLayer {
                            scaleX = iconScale
                            scaleY = iconScale
                        },
                    contentScale = ContentScale.Fit
                )

                // 2. Citat (veći font i bolji raspored)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LoadingQuoteText(text = "Cause I’ve got a ", color = PrimaryDark)
                    Spacer(modifier = Modifier.height(8.dp))
                    LoadingQuoteText(text = "blank space,", color = PrimaryDark, fontWeight = FontWeight.ExtraBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    LoadingQuoteText(text = "baby", color = PrimaryDark)
                    Spacer(modifier = Modifier.height(8.dp))
                    LoadingQuoteText(text = "and I’ll ", color = PrimaryDark)
                    Spacer(modifier = Modifier.height(8.dp))
                    LoadingQuoteText(text = "write your name", color = PrimaryDark)
                }

                // 3. Progres Bar
                if (isLoadingData.value) {
                    CircularProgressIndicator(color = PrimaryDark)
                } else {
                    Text(
                        text = "Dobrodošli!",
                        color = PrimaryDark.copy(alpha = 0.8f),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            /*
            // EFEKAT ZAVESE/WIPE EFFECT (Simulacija Motion Blur-a)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        // Pomeranje u Y osi bazirano na progresu wipe-a
                        translationY = size.height * (1 - wipeProgress)
                    }
                    .background(DarkOverlay) // Tamna preklopna boja
            ) {
                // Opšta poruka dok je zavesa spuštena
                if (wipeProgress < 0.2f) {
                    Text(
                        text = "Loading...",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }*/
        }
    }
}

// Pomoćna funkcija za stilizaciju teksta
@Composable
fun LoadingQuoteText(text: String, color: Color, fontWeight: FontWeight = FontWeight.Bold) {
    Text(
        text = text,
        color = color,
        fontSize = 25.sp, // Veći font
        fontWeight = fontWeight,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}