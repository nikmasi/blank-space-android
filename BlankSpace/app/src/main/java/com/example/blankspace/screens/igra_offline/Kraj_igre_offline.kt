package com.example.blankspace.screens.igra_offline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.ui.modifiers.buttonStyle
import com.example.blankspace.ui.modifiers.columnMainStyle
import com.example.blankspace.ui.modifiers.mainCardStyle
import com.example.blankspace.viewModels.DatabaseViewModel
import  com.example.blankspace.ui.theme.*
@Composable
fun Kraj_igre_offline(navController: NavController,poeni:Int,databaseViewModel: DatabaseViewModel){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        val poeni2=poeni/10
        Kraj_igre_offline_mainCard(navController,poeni2, modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun Kraj_igre_offline_mainCard(navController: NavController, poeni: Int, modifier: Modifier = Modifier) {

    Surface(
        color = CardContainerColor,
        modifier = modifier.mainCardStyle(heightFraction = 0.55f),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.columnMainStyle(),
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
            modifier = Modifier.buttonStyle()
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
        modifier = Modifier.buttonStyle()
    ) {
        Text(text = "Kraj", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
    }
}