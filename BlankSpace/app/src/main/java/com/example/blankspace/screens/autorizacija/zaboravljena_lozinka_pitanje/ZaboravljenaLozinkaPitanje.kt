package com.example.blankspace.screens.autorizacija.zaboravljena_lozinka_pitanje

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.UiStateZLP
import com.example.blankspace.viewModels.ZaboravljenaLozinkaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.blankspace.ui.theme.*

@Composable
fun ZaboravljenaLozinkaPitanje(viewModel: ZaboravljenaLozinkaViewModel, onNavigateToReset: () -> Unit) {

    val uiStateP by viewModel.uiStateP.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()

        ZaboravljenaLozinkaPitanje_mainCard(
            modifier = Modifier.align(Alignment.Center),
            question = uiState.zaboravljenaLozinka?.pitanje_lozinka ?: "Nema pitanja",
            korisnickoIme = uiState.zaboravljenaLozinka?.korisnicko_ime ?: "",
            onAnswerSubmit = { ime, odgovor ->
                viewModel.fetchZaboravljenaLozinkaPitanje(ime, odgovor)
            }
        )
    }

    HandlePasswordQuestionResponse(uiStateP = uiStateP, onSuccess = onNavigateToReset)
}

@Composable
fun ZaboravljenaLozinkaPitanje_mainCard(modifier: Modifier,
                    korisnickoIme:String,question: String,onAnswerSubmit: (String, String) -> Unit) {

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.85f)
            .fillMaxHeight(0.5f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            PasswordQuestionHeader(question = question)

            var odgovor by remember { mutableStateOf("") }

            PasswordQuestionField(odgovor, onValueChange = { odgovor = it })

            PasswordQuestionButton(odgovor,
                onConfirm = { uneseniOdgovor ->
                    onAnswerSubmit(korisnickoIme, uneseniOdgovor)
                })
        }
    }
}

@Composable
fun HandlePasswordQuestionResponse(uiStateP: UiStateZLP, onSuccess: () -> Unit) {
    val context = LocalContext.current

    LaunchedEffect(uiStateP.zaboravljenaLozinkaPitanje?.odgovor) {
        val odgovor = uiStateP.zaboravljenaLozinkaPitanje?.odgovor
        if (!odgovor.isNullOrEmpty()) {
            when {
                odgovor.contains("Netačan odgovor!") -> {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, odgovor, Toast.LENGTH_SHORT).show()
                    }
                }
                odgovor.contains("Tačan odgovor!") -> {
                    onSuccess()
                }
            }
        }
    }
}