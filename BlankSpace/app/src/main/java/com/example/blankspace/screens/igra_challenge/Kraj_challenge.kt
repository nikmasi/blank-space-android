package com.example.blankspace.screens.igra_challenge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.igra_sam.KrajIgreBodyStyled
import com.example.blankspace.screens.igra_sam.KrajIgreButtonsStyled
import com.example.blankspace.screens.igra_sam.KrajIgreHeaderStyled
import com.example.blankspace.viewModels.IgraSamViewModel
import com.example.blankspace.viewModels.LoginViewModel

private val CardContainerColor = Color(0xFFF0DAE7)

@Composable
fun Kraj_challenge(poeni:Int,viewModelLogin: LoginViewModel,igraSamViewModel: IgraSamViewModel,onClickPonovo: () ->Unit, onClickKraj: () ->Unit){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        val poeni2 = poeni/10
        Kraj_challenge_mainCardStyled(poeni2,viewModelLogin,igraSamViewModel, modifier = Modifier.align(Alignment.Center),
            onClickPonovo = onClickPonovo,
            onClickKraj = onClickKraj
        )
    }
}

@Composable
fun Kraj_challenge_mainCardStyled(
    poeni: Int,
    viewModelLogin: LoginViewModel,
    igraSamViewModel: IgraSamViewModel,
    modifier: Modifier = Modifier,
    onClickPonovo: () ->Unit, onClickKraj: () ->Unit
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
            KrajIgreHeaderStyled()
            KrajIgreBodyStyled(poeni)
            KrajIgreButtonsStyled(onClickPonovo = onClickPonovo, onClickKraj = onClickKraj)
        }
    }
}