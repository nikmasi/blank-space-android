package com.example.blankspace.screens.igraj_u_duelu

import android.content.Context
import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.DuelViewModel
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.UiStateL
import com.example.blankspace.viewModels.UiStateProveriSifru

// --- BOJE ---
private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)
private val TextMain = PrimaryDark

// --- POMOĆNE KOMPONENTE (za samostalnost fajla) ---

@Composable
fun HeadlineTextSifraSobe(text: String) {
    Text(text, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = TextMain)
}

@Composable
fun ActionButtonSifraSobe(onClick: () -> Unit, text: String, modifier: Modifier, containerColor: Color = AccentPink) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.height(48.dp)
    ) {
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextFieldInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(0.8f).padding(bottom = 16.dp),
        //keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AccentPink,
            unfocusedBorderColor = PrimaryDark.copy(alpha = 0.5f),
            cursorColor = AccentPink
        ),
        singleLine = true,
        shape = RoundedCornerShape(12.dp)
    )
}

// --- GLAVNE KOMPONENTE ---

@Composable
fun Sifra_sobe_duel(navController: NavController,viewModelDuel:DuelViewModel,loginViewModel: LoginViewModel){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Sifra_sobe_duel_mainCard(
            navController = navController,
            viewModelDuel = viewModelDuel,
            loginViewModel = loginViewModel,
            modifier = Modifier.align(Alignment.Center) // Centriranje kartice
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
        color = CardContainerColor, // Svetlo roza
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.6f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp), // Povećan padding
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly // Ravnomerno raspoređivanje
        ) {
            val uiStateLogin by loginViewModel.uiState.collectAsState()

            SifraSobeGenerisiNovuSifru(navController,viewModelDuel,uiStateLogin)

            // Estetski separator
            Text("— ILI —", color = TextMain.copy(alpha = 0.6f), fontWeight = FontWeight.Bold)

            SifraSobeUnesiPostojecuSifru(viewModelDuel,uiStateLogin,sifra, onValueChange = { sifra = it })
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

        // Proveravamo da li je došlo do odgovora i da li je soba slobodna ("NE" znači da je soba postojala i da si drugi igrač)
        if (odgovor == "NE") {
            uiStateProveriSifru.proveriSifru?.stihovi?.let {
                // Pozovi dodeli za sinhronizaciju stanja
                viewModelDuel.dodeli(uiStateProveriSifru,sifra.toInt())

                // Fetch Duel podaci
                viewModelDuel.fetchDuel(1,0,
                    it, emptyList(), context)
            }

            // Ažuriraj lokalno stanje
            viewModelDuel.upisiSifruSobe(sifra.toInt())
            viewModelDuel.upisiRedniBroj(redniBroj = 2) // Ti si drugi igrač!

            // Navigacija
            navController.navigate(Destinacije.Duel.ruta + "/" + 1 + "/" + 0+"/${sifra.toInt()}")
        }
        // Možda treba dodati i else blok za Toast("Neispravna šifra") ako je odgovor DA (soba ne postoji)
        else if (odgovor == "DA") {
            // Toast.makeText(context, "Šifra ne postoji ili je soba puna.", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun SifraSobeGenerisiNovuSifru(navController: NavController,viewModelDuel: DuelViewModel,uiStateLogin: UiStateL){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HeadlineTextSifraSobe("Generiši novu šifru sobe")
        Spacer(modifier = Modifier.height(22.dp))

        ActionButtonSifraSobe(
            onClick = {
                uiStateLogin.login?.let { viewModelDuel.generisiSifru(it.korisnicko_ime) }
                navController.navigate(Destinacije.Generisi_sifru_sobe.ruta)
            },
            text = "Generiši",
            modifier = Modifier.fillMaxWidth(0.7f)
        )
    }
}

@Composable
fun SifraSobeUnesiPostojecuSifru(viewModelDuel: DuelViewModel,uiStateLogin: UiStateL,sifra: String,onValueChange: (String) -> Unit){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HeadlineTextSifraSobe("Unesi postojeću šifru sobe")
        Spacer(modifier = Modifier.height(22.dp))

        // Koristi stilizovani OutlinedTextFieldInput
        OutlinedTextFieldInput(
            value = sifra,
            onValueChange = onValueChange,
            label = "Šifra",
            keyboardType = KeyboardType.Number // Šifra je obično numerička
        )

        ActionButtonSifraSobe(
            onClick = {
                if (sifra.isNotEmpty()) {
                    uiStateLogin.login?.korisnicko_ime?.let {
                        viewModelDuel.proveriSifru(it, sifra.toInt())
                    }
                } else {
                    // Opciono: Toast upozorenje
                }
            },
            text = "Unesi",
            modifier = Modifier.fillMaxWidth(0.7f)
        )
    }
}