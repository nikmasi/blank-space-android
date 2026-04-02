package com.example.blankspace.screens.igra_pogodi_i_pevaj

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.igra_sam.Kraj_igre_igre_sam_mainCard
import com.example.blankspace.viewModels.IgraSamViewModel
import com.example.blankspace.viewModels.LoginViewModel

@Composable
fun Kraj_pogodiPevaj(poeni:Int,viewModelLogin: LoginViewModel,igraSamViewModel: IgraSamViewModel,
     onClickPonovo: () ->Unit, onClickKraj: () ->Unit
){
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        val poeni2 = poeni/10

        Kraj_igre_igre_sam_mainCard(
            poeni = poeni2,
            viewModelLogin = viewModelLogin,
            igraSamViewModel = igraSamViewModel,
            modifier = Modifier.align(Alignment.Center),
            onClickPonovo = onClickPonovo,
            onClickKraj = onClickKraj
        )
    }
}