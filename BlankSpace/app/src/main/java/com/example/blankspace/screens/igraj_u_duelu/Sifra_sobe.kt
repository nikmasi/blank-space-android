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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.igraj_u_duelu.components.ActionButtonDuel
import com.example.blankspace.screens.igraj_u_duelu.components.HeadlineTextDuel
import com.example.blankspace.screens.igraj_u_duelu.components.OutlinedTextFieldInput
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.DuelViewModel
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.UiStateProveriSifru
import com.example.blankspace.ui.theme.*

@Composable
fun Sifra_sobe_duel(navController: NavController,viewModelDuel:DuelViewModel,loginViewModel: LoginViewModel){
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        Sifra_sobe_duel_mainCard(
            navController = navController,
            viewModelDuel = viewModelDuel,
            loginViewModel = loginViewModel,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun Sifra_sobe_duel_mainCard(
    navController: NavController,
    viewModelDuel: DuelViewModel,
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiStateProveriSifru by viewModelDuel.uiStateProveriSifru.collectAsState()
    var sifra by remember { mutableStateOf("") }

    HandleProveriSifruResponse(navController,context,uiStateProveriSifru,viewModelDuel,sifra)

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.6f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            val uiStateLogin by loginViewModel.uiState.collectAsState()

            SifraSobeGenerisiNovuSifru(onGenerisiClick = {
                uiStateLogin.login?.let { viewModelDuel.generisiSifru(it.korisnicko_ime) }
                navController.navigate(Destinacije.Generisi_sifru_sobe.ruta)
            })

            Text("— ILI —", color = PrimaryDark.copy(alpha = 0.6f), fontWeight = FontWeight.Bold)

            SifraSobeUnesiPostojecuSifru(sifra, onValueChange = { sifra = it }, onUnesiClick = {
                if (sifra.isNotEmpty()) {
                    uiStateLogin.login?.korisnicko_ime?.let {
                        viewModelDuel.proveriSifru(it, sifra.toInt())
                    }
                }
            })
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

        if (odgovor == "NE") {
            uiStateProveriSifru.proveriSifru?.stihovi?.let {
                viewModelDuel.dodeli(uiStateProveriSifru,sifra.toInt())

                viewModelDuel.fetchDuel(0,0,
                    it, emptyList(), context)
            }

            viewModelDuel.upisiSifruSobe(sifra.toInt())
            viewModelDuel.upisiRedniBroj(redniBroj = 2)

            navController.navigate(Destinacije.Duel.ruta + "/" + 0 + "/" + 0+"/${sifra.toInt()}")
        }
        else if (odgovor == "DA") {
            // Toast.makeText(context, "Šifra ne postoji ili je soba puna.", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun SifraSobeGenerisiNovuSifru(onGenerisiClick: () -> Unit){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HeadlineTextDuel("Generiši novu šifru sobe",fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(22.dp))

        ActionButtonDuel(
            onClick = onGenerisiClick,
            text = "Generiši",
            modifier = Modifier.fillMaxWidth(0.7f)
        )
    }
}

@Composable
fun SifraSobeUnesiPostojecuSifru(sifra: String, onValueChange: (String) -> Unit, onUnesiClick: () -> Unit){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HeadlineTextDuel("Unesi postojeću šifru sobe",fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(22.dp))

        OutlinedTextFieldInput(
            value = sifra,
            onValueChange = onValueChange,
            label = "Šifra",
            keyboardType = KeyboardType.Number
        )

        ActionButtonDuel(
            onClick = onUnesiClick,
            text = "Unesi",
            modifier = Modifier.fillMaxWidth(0.7f)
        )
    }
}