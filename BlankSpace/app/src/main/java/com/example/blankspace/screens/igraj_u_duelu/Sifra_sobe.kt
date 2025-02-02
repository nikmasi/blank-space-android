package com.example.blankspace.screens.igraj_u_duelu

import android.content.Context
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.ui.components.OutlinedTextFieldInput
import com.example.blankspace.ui.components.SmallButton
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.DuelViewModel
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.UiStateL
import com.example.blankspace.viewModels.UiStateProveriSifru

@Composable
fun Sifra_sobe_duel(navController: NavController,viewModelDuel:DuelViewModel,loginViewModel: LoginViewModel){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))
        Sifra_sobe_duel_mainCard(navController,viewModelDuel,loginViewModel)
    }
}

@Composable
fun Sifra_sobe_duel_mainCard(navController: NavController,viewModelDuel:DuelViewModel,loginViewModel: LoginViewModel) {
    val context = LocalContext.current
    val uiStateProveriSifru by viewModelDuel.uiStateProveriSifru.collectAsState()
    var sifra by remember { mutableStateOf("") }

    HandleProveriSifruResponse(navController,context,uiStateProveriSifru,viewModelDuel,sifra)

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
            val uiStateLogin by loginViewModel.uiState.collectAsState()

            SifraSobeGenerisiNovuSifru(navController,viewModelDuel,uiStateLogin)
            HeadlineText("ILI")
            SifraSobeUnesiPostojecuSifru(viewModelDuel,uiStateLogin,sifra, onValueChange = {sifra=it})

        }
    }
}

@Composable
fun HandleProveriSifruResponse(
    navController: NavController,
    context: Context,
    uiStateProveriSifru: UiStateProveriSifru,
    viewModelDuel: DuelViewModel,
    sifra:String
){
    LaunchedEffect(uiStateProveriSifru.proveriSifru?.error) {
        val odgovor = uiStateProveriSifru.proveriSifru?.error
        if (!odgovor.isNullOrEmpty()) {
            if(odgovor=="NE"){
                uiStateProveriSifru.proveriSifru?.stihovi?.let {
                    viewModelDuel.dodeli(uiStateProveriSifru)
                    viewModelDuel.fetchDuel(1,0,
                        it, emptyList(), context)
                }
                viewModelDuel.upisiSifruSobe(sifra.toInt())
                viewModelDuel.upisiRedniBroj(redniBroj = 2)
                navController.navigate(Destinacije.Duel.ruta + "/" + 0 + "/" + 0)
            }
            return@LaunchedEffect
        }
    }
}

@Composable
fun SifraSobeGenerisiNovuSifru(navController: NavController,viewModelDuel: DuelViewModel,uiStateLogin: UiStateL){
    HeadlineText("Generiši novu šifru sobe")
    Spacer(modifier = Modifier.height(22.dp))

    SmallButton(onClick = {
        uiStateLogin.login?.let { viewModelDuel.generisiSifru(it.korisnicko_ime) }
        navController.navigate(Destinacije.Generisi_sifru_sobe.ruta)
    }, text = "Generiši", style = MaterialTheme.typography.bodyMedium )

    Spacer(modifier = Modifier.height(22.dp))

}

@Composable
fun SifraSobeUnesiPostojecuSifru(viewModelDuel: DuelViewModel,uiStateLogin: UiStateL,sifra: String,onValueChange: (String) -> Unit){
    Spacer(modifier = Modifier.height(22.dp))

    HeadlineText("Unesi postojeću šifru sobe")
    Spacer(modifier = Modifier.height(42.dp))

    OutlinedTextFieldInput(
        value = sifra,
        onValueChange = onValueChange,
        label = "šifra")

    SmallButton(onClick = {
        uiStateLogin.login?.korisnicko_ime?.let { viewModelDuel.proveriSifru(it,sifra.toInt()) }
    },
        text = "Unesi", style = MaterialTheme.typography.bodyMedium)
}