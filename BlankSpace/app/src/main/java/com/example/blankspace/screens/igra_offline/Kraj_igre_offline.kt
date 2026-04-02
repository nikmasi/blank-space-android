package com.example.blankspace.screens.igra_offline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.igra_sam.KrajIgreBodyStyled
import com.example.blankspace.screens.igra_sam.KrajIgreButtonsStyled
import com.example.blankspace.screens.igra_sam.KrajIgreHeaderStyled
import com.example.blankspace.ui.modifiers.columnMainStyle
import com.example.blankspace.ui.modifiers.mainCardStyle
import  com.example.blankspace.ui.theme.*

@Composable
fun Kraj_igre_offline(poeni:Int, onClickPonovo: () -> Unit, onClickKraj: () -> Unit){
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        val poeni2=poeni/10
        Kraj_igre_offline_mainCard(poeni2, modifier = Modifier.align(Alignment.Center),
            onClickPonovo = onClickPonovo, onClickKraj = onClickKraj
        )
    }
}

@Composable
fun Kraj_igre_offline_mainCard(
    poeni: Int, modifier: Modifier = Modifier, onClickPonovo: () -> Unit, onClickKraj: () -> Unit
) {
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

            KrajIgreButtonsStyled(onClickPonovo = onClickPonovo, onClickKraj = onClickKraj)
        }
    }
}