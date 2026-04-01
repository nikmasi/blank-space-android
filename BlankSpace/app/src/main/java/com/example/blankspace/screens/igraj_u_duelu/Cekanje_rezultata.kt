package com.example.blankspace.screens.igraj_u_duelu

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blankspace.screens.igraj_u_duelu.components.BodyTextDuel
import com.example.blankspace.screens.igraj_u_duelu.components.HeadlineTextDuel
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.DuelViewModel
import kotlinx.coroutines.delay
import com.example.blankspace.ui.theme.*

@Composable
fun Cekanje_rezultata(viewModelDuel:DuelViewModel,poeni: Int,sifra:Int, onKrajDuela: (Int) -> Unit){
    val uiStateD by viewModelDuel.uiState.collectAsState()
    val uiStateCekanjeRezultata by viewModelDuel.uiStateCekanjeRezultata.collectAsState()
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        Cekanje_rezultata_mainCard(
            sifraSobe = viewModelDuel.sifraSobe.value.sifra,
            odgovor = uiStateCekanjeRezultata.cekanjeRezultata?.odgovor,
            modifier = Modifier.align(Alignment.Center),
            onTick = {
                viewModelDuel.fetchCekanjeRezultata(
                    poeni/10,
                    viewModelDuel.sifraSobe.value.sifra,
                    uiStateD.duel?.rundePoeni ?: emptyList(),
                    viewModelDuel.redniBroj.value.redniBroj
                )
            },
            onShowToast = { poruka -> Toast.makeText(context, poruka, Toast.LENGTH_SHORT).show() },
            onNavigateToKraj = {onKrajDuela(sifra)}
        )
    }
}

@Composable
fun Cekanje_rezultata_mainCard(
    sifraSobe: Int, odgovor: String?, modifier: Modifier = Modifier,
    onTick: () -> Unit, onShowToast: (String) -> Unit, onNavigateToKraj: () -> Unit
) {
    HandleCekanjeRezultataResponse(sifraSobe , onClick = onTick)

    HandleCekanjeRezultataOdgovorResponse(
        odgovor = odgovor, onShowToast = onShowToast, onNavigateToKraj = onNavigateToKraj
    )

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.4f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HeadlineTextDuel("Igra je završena.", 20.sp,FontWeight.SemiBold, true)
            HeadlineTextDuel("Rezultat se obrađuje...", 20.sp, FontWeight.SemiBold, true)

            Spacer(modifier = Modifier.height(34.dp))

            CircularProgressIndicator(
                color = AccentPink,
                modifier = Modifier.height(48.dp).width(48.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            BodyTextDuel("Čeka se da drugi igrač završi rundu...", true)
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HandleCekanjeRezultataResponse(sifraSobe: Int, onClick: () -> Unit){
    LaunchedEffect(sifraSobe) {
        delay(1000)

        while (true) {
            onClick()
            delay(3000)
        }
    }
}

@Composable
fun HandleCekanjeRezultataOdgovorResponse(
    odgovor: String?, onShowToast: (String) -> Unit, onNavigateToKraj: () -> Unit
){
    LaunchedEffect(odgovor) {
        if (!odgovor.isNullOrEmpty()) {
            onShowToast(odgovor)

            if (odgovor.contains("Čeka se rezultat duela")) {
                return@LaunchedEffect
            }
            onNavigateToKraj()
        }
    }
}