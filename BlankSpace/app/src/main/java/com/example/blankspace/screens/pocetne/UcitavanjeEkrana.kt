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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blankspace.R
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.LoginViewModel
import kotlinx.coroutines.delay
import android.media.MediaPlayer
import android.widget.Toast
import com.example.blankspace.hasDownloadedData
import com.example.blankspace.isInternetAvailable
import com.example.blankspace.viewModels.DatabaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.graphicsLayer
import com.example.blankspace.ui.modifiers.mainCardStyle
import com.example.blankspace.ui.theme.*

@Composable
fun UcitavanjeEkrana(modifier: Modifier = Modifier, navController: NavController, loginViewModel: LoginViewModel, databaseViewModel: DatabaseViewModel) {
    Box(modifier = modifier.fillMaxSize()) {
        BgCard2()

        UcitavanjeEkrana_main(
            navController = navController,
            loginViewModel = loginViewModel,
            databaseViewModel = databaseViewModel,
        )
    }
}

@Composable
fun UcitavanjeEkrana_main(navController: NavController, loginViewModel: LoginViewModel, databaseViewModel: DatabaseViewModel) {
    val context = LocalContext.current
    val isLoadingData = remember { mutableStateOf(true) }
    val uiStateLogin by loginViewModel.uiState.collectAsState()

    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.taylorswiftblankspace2) }
    val animationFinished = remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        mediaPlayer.start()
        delay(5400)
        mediaPlayer.stop()
        mediaPlayer.release()

        animationFinished.value = true
        delay(1500)

        isLoadingData.value = false

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

    UcitavanjeEkranaContent(isLoading = isLoadingData.value)
}

@Composable
fun UcitavanjeEkranaContent(isLoading: Boolean) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()

        val infiniteTransition = rememberInfiniteTransition(label = "pulse")
        val iconScale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.08f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ), label = "scale"
        )

        Surface(
            color = CardContainerColor,
            modifier = Modifier
                .align(Alignment.Center)
                .mainCardStyle(heightFraction = 0.5f, elevation = 20.dp, cornerRadius = 32.dp),
            shape = RoundedCornerShape(32.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(150.dp)
                        .graphicsLayer {
                            scaleX = iconScale
                            scaleY = iconScale
                        }
                )

                Spacer(modifier = Modifier.height(40.dp))

                if (isLoading) {
                    Spacer(modifier = Modifier.height(32.dp))

                    LinearProgressIndicator(
                        modifier = Modifier
                            .width(150.dp)
                            .height(4.dp),
                        color = PrimaryDark,
                        trackColor = PrimaryDark.copy(alpha = 0.2f)
                    )
                }
            }
        }
    }
}