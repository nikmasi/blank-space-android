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
import com.example.blankspace.screens.igra_sam.KrajIgreBody
import com.example.blankspace.screens.igra_sam.KrajIgreHeader
import com.example.blankspace.viewModels.IgraSamViewModel
import com.example.blankspace.viewModels.LoginViewModel

@Composable
fun Kraj_pogodiPevaj(navController: NavController,poeni:Int,viewModelLogin: LoginViewModel,igraSamViewModel: IgraSamViewModel){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))
        val poeni2=poeni/10
        Kraj_pogodiPevaj_mainCard(navController,poeni2,viewModelLogin,igraSamViewModel)
    }
}

@Composable
fun Kraj_pogodiPevaj_mainCard(navController: NavController,poeni: Int,viewModelLogin: LoginViewModel,igraSamViewModel: IgraSamViewModel) {
    val uiStateLogin by viewModelLogin.uiState.collectAsState()

    LaunchedEffect(Unit) {
        uiStateLogin.login?.korisnicko_ime?.let { igraSamViewModel.fetchKrajIgre(it,poeni) }
    }

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight(0.6f),
        shape = RoundedCornerShape(60.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            KrajIgreHeader()
            Spacer(modifier = Modifier.height(42.dp))
            KrajIgreBody(poeni)
            Spacer(modifier = Modifier.height(22.dp))
            KrajIgreButtons(navController)
        }
    }
}

@Composable
fun KrajIgreButtons(navController: NavController) {
    SmallButton(
        onClick = {
            navController.navigate(Destinacije.Nivo_pogodiPevaj.ruta)
        },
        text = "Igraj ponovo",
        style = MaterialTheme.typography.bodyMedium
    )

    Spacer(modifier = Modifier.height(22.dp))

    SmallButton(
        onClick = {
            navController.navigate(Destinacije.Login.ruta)
        },
        text = "Kraj",
        style = MaterialTheme.typography.bodyMedium
    )
}
