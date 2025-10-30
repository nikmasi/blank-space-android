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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.viewModels.IgraSamViewModel
import com.example.blankspace.viewModels.LoginViewModel

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)

@Composable
fun Kraj_pogodiPevaj(navController: NavController,poeni:Int,viewModelLogin: LoginViewModel,igraSamViewModel: IgraSamViewModel){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        val poeni2 = poeni/10
        Kraj_pogodiPevaj_mainCardStyled(navController,poeni2,viewModelLogin,igraSamViewModel, modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun Kraj_pogodiPevaj_mainCardStyled(
    navController: NavController,
    poeni: Int,
    viewModelLogin: LoginViewModel,
    igraSamViewModel: IgraSamViewModel,
    modifier: Modifier = Modifier
) {
    val uiStateLogin by viewModelLogin.uiState.collectAsState()

    LaunchedEffect(Unit) {
        uiStateLogin.login?.korisnicko_ime?.let { igraSamViewModel.fetchKrajIgre(it,poeni) }
    }

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.6f)
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
            KrajIgreHeaderPogodiPevaj(title = "Kraj Igre")
            Spacer(modifier = Modifier.height(42.dp))
            KrajIgreBodyPogodiPevaj(poeni)
            Spacer(modifier = Modifier.height(32.dp))
            KrajIgreButtonsPogodiPevaj(navController)
        }
    }
}

@Composable
fun KrajIgreHeaderPogodiPevaj(title: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = PrimaryDark
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Pogodi i Pevaj mod završen!",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = AccentPink
        )
    }
}

@Composable
fun KrajIgreBodyPogodiPevaj(poeni: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Osvojeni poeni:",
            fontSize = 24.sp,
            color = PrimaryDark.copy(alpha = 0.8f),
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$poeni ",
            fontSize = 30.sp,
            color = AccentPink,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
fun KrajIgreButtonsPogodiPevaj(navController: NavController) {

    @Composable
    fun EndGameButton(text: String, destination: String, containerColor: Color) {
        Button(
            onClick = { navController.navigate(destination) },
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(56.dp)
                .shadow(8.dp, RoundedCornerShape(16.dp), spotColor = containerColor.copy(alpha = 0.5f))
        ) {
            Text(text, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }

    EndGameButton(
        text = "Igraj ponovo",
        destination = Destinacije.Nivo_pogodiPevaj.ruta,
        containerColor = AccentPink
    )

    Spacer(modifier = Modifier.height(20.dp))

    EndGameButton(
        text = "Kraj",
        destination = Destinacije.Login.ruta,
        containerColor = PrimaryDark.copy(alpha = 0.7f)
    )
}