package com.example.blankspace.screens.igra_offline

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.ui.components.BodyText
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.viewModels.DatabaseViewModel
import com.example.blankspace.viewModels.IgraSamViewModel
import com.example.blankspace.viewModels.LoginViewModel

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)

@Composable
fun Kraj_igre_offline(navController: NavController,poeni:Int,databaseViewModel: DatabaseViewModel){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        val poeni2=poeni/10
        Kraj_igre_offline_mainCard(navController,poeni2,databaseViewModel, modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun Kraj_igre_offline_mainCard(navController: NavController,poeni: Int,databaseViewModel: DatabaseViewModel, modifier: Modifier = Modifier) {
    val uiState by databaseViewModel.uiState.collectAsState()

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.85f)
            .fillMaxHeight(0.55f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                KrajIgreHeaderStyled()
                Spacer(modifier = Modifier.height(32.dp))
                KrajIgreBodyStyled(poeni)
            }

            KrajIgreButtons(navController)
        }
    }
}

@Composable
fun KrajIgreHeaderStyled() {
    Text(
        text = "Igra završena!",
        fontSize = 32.sp,
        fontWeight = FontWeight.ExtraBold,
        color = PrimaryDark
    )
}

@Composable
fun KrajIgreBodyStyled(poeni: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Osvojeni poeni:",
            fontSize = 20.sp,
            color = PrimaryDark.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$poeni",
            fontSize = 48.sp,
            fontWeight = FontWeight.Black,
            color = AccentPink
        )
    }
}

@Composable
fun KrajIgreButtons(navController: NavController) {
    @Composable
    fun ActionButton(text: String, onClick: () -> Unit) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = AccentPink,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(0.9f).height(52.dp)
        ) {
            Text(text = text, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
    }

    ActionButton(
        text = "Igraj ponovo",
        onClick = {
            navController.navigate(Destinacije.Nivo_igra_offline.ruta)
        }
    )

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = {
            navController.navigate(Destinacije.PocetnaOffline.ruta)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = PrimaryDark.copy(alpha = 0.7f)
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(52.dp)
    ) {
        Text(text = "Kraj", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
    }
}