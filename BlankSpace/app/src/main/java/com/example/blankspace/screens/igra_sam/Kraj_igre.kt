package com.example.blankspace.screens.igra_sam

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blankspace.ui.components.BodyText
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.ui.components.SmallButton
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.viewModels.IgraSamViewModel
import com.example.blankspace.viewModels.LoginViewModel
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.blankspace.screens.pocetne.cards.BgCard2

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)
private val TextHighlight = Color(0xFFD32F2F)

@Composable
fun Kraj_igre_igre_sam(navController: NavController, poeni: Int, viewModelLogin: LoginViewModel, igraSamViewModel: IgraSamViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        val poeni2 = poeni / 10
        Kraj_igre_igre_sam_mainCard(
            navController = navController,
            poeni = poeni2,
            viewModelLogin = viewModelLogin,
            igraSamViewModel = igraSamViewModel,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun Kraj_igre_igre_sam_mainCard(navController: NavController, poeni: Int, viewModelLogin: LoginViewModel, igraSamViewModel: IgraSamViewModel, modifier: Modifier) {
    val uiStateLogin by viewModelLogin.uiState.collectAsState()

    LaunchedEffect(Unit) {
        uiStateLogin.login?.korisnicko_ime?.let { korisnik ->
            igraSamViewModel.fetchKrajIgre(korisnik, poeni)
        }
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
                .padding(horizontal = 32.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            KrajIgreHeaderStyled()
            KrajIgreBodyStyled(poeni)
            KrajIgreButtonsStyled(navController)
        }
    }
}

@Composable
fun KrajIgreHeaderStyled() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Rezultat",
            color = PrimaryDark,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Igra je završena. Vaš rezultat je sačuvan.",
            color = PrimaryDark.copy(alpha = 0.8f),
            fontSize = 16.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

@Composable
fun KrajIgreBodyStyled(poeni: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "KRAJ IGRE!",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = AccentPink,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = "Broj osvojenih poena: ",
                fontSize = 20.sp,
                color = PrimaryDark
            )
            Text(
                text = "$poeni",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = TextHighlight, // Crvena za poene
                modifier = Modifier.padding(horizontal = 0.dp)
            )

        }
    }
}

@Composable
fun KrajIgreButtonsStyled(navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        FullWidthStyledButton(
            onClick = { navController.navigate(Destinacije.Nivo_igra_sam.ruta) },
            text = "Igraj ponovo",
            containerColor = AccentPink
        )

        Spacer(modifier = Modifier.height(16.dp))

        FullWidthStyledButton(
            onClick = { navController.navigate(Destinacije.Login.ruta) },
            text = "Kraj",
            containerColor = PrimaryDark.copy(alpha = 0.8f)
        )
    }
}

@Composable
fun FullWidthStyledButton(
    onClick: () -> Unit,
    text: String,
    containerColor: Color
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp))
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
