package com.example.blankspace.screens.igraj_u_duelu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blankspace.screens.igraj_u_duelu.components.ActionButtonDuel
import com.example.blankspace.screens.igraj_u_duelu.components.BodyTextDuel
import com.example.blankspace.screens.igraj_u_duelu.components.HeadlineTextDuel
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.DuelViewModel
import kotlinx.coroutines.delay
import com.example.blankspace.ui.theme.*

private val TimeWarningColor = Color(0xFFD32F2F) // crvena za upozorenje

@Composable
fun Generisi_sifru_sobe(viewModelDuel:DuelViewModel, onClickDuel: (Int) -> Unit, onClickLogin: () -> Unit){
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        Generisi_sifru_sobe_mainCard(
            viewModelDuel = viewModelDuel, modifier = Modifier.align(Alignment.Center),
            onClickDuel = onClickDuel, onClickLogin = onClickLogin
        )
    }
}

@Composable
fun Generisi_sifru_sobe_mainCard(
    viewModelDuel: DuelViewModel, modifier: Modifier = Modifier,
    onClickDuel: (Int) -> Unit, onClickLogin: () -> Unit
) {
    val uiStateStigaoIgrac by viewModelDuel.uiStateStigaoIgrac.collectAsState()
    val uiStateSifra by viewModelDuel.uiStateSifSobe.collectAsState()

    HandleSifraResponse( viewModelDuel = viewModelDuel, onClickDuel = onClickDuel)

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.5f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HeadlineTextDuel("Generisana šifra sobe:",24.sp, FontWeight.ExtraBold)

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "${uiStateSifra.sifraResponse?.sifra}",
                fontSize = 48.sp,
                fontWeight = FontWeight.Black,
                color = AccentPink
            )

            Spacer(modifier = Modifier.height(22.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (uiStateStigaoIgrac.isRefreshing) {
                    CircularProgressIndicator(
                        modifier = Modifier.height(20.dp),
                        color = AccentPink
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                BodyTextDuel("Kada drugi igrač unese šifru, počeće igra...")
            }

            Spacer(modifier = Modifier.height(42.dp))

            ActionButtonDuel(
                onClick = onClickLogin,
                text = "Odustani",
                containerColor = TimeWarningColor,
                modifier = Modifier.fillMaxWidth(0.7f)
            )
        }
    }
}

@Composable
fun HandleSifraResponse(viewModelDuel: DuelViewModel, onClickDuel: (Int) -> Unit){

    val context = LocalContext.current
    val sifraSobe by viewModelDuel.sifraSobe.collectAsState()
    val uiStateStigaoIgrac by viewModelDuel.uiStateStigaoIgrac.collectAsState()
    val uiStateSifra by viewModelDuel.uiStateSifSobe.collectAsState()

    LaunchedEffect(sifraSobe.sifra) {
        val sifra = sifraSobe.sifra
        if (sifra == -1) return@LaunchedEffect

        while (true) {
            viewModelDuel.stigaoIgrac(sifra)

            delay(3000)

            val stigaoIgrac = viewModelDuel.uiStateStigaoIgrac.value.stigaoIgrac?.stigao

            if (stigaoIgrac == "true") {
                viewModelDuel.stopAudio()

                val stihovi = uiStateSifra.sifraResponse?.stihovi
                if (stihovi != null) {
                    viewModelDuel.fetchDuel(0, 0, stihovi, rundaPoeni = emptyList(), context)
                }

                viewModelDuel.upisiRedniBroj(1)
                viewModelDuel.upisiSifruSobe(sifra)

                onClickDuel(sifra)
                break
            }
        }
    }
}