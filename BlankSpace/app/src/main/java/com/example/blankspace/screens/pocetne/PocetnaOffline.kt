package com.example.blankspace.screens.pocetne


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.pocetne.cards.PocetnaMainCard
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.ui.components.MyImage
import com.example.blankspace.ui.components.SmallButton
import com.example.blankspace.viewModels.LoginViewModel

@Composable
fun PocetnaOffline(modifier: Modifier = Modifier, navController: NavController,viewModelLogin:LoginViewModel) {
    Box(modifier = modifier.fillMaxSize()) {
        BgCard2()

        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .fillMaxHeight(0.85f),
            shape = RoundedCornerShape(60.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                MyImage(ContentScale.Crop, 5)

                HeadlineText("Igra dopunjavanja tekstova")
                HeadlineText("pesama")


                Spacer(modifier = Modifier.height(22.dp))

                SmallButton(onClick = {
                    navController.navigate(Destinacije.Nivo_igra_offline.ruta)
                }, text = "Offline igra", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
