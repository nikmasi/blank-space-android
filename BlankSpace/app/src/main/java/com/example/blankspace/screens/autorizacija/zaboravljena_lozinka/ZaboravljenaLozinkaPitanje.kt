package com.example.blankspace.screens.autorizacija.zaboravljena_lozinka

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.blankspace.R
import com.example.blankspace.screens.autorizacija.auth_components.AuthButton
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
            question = uiState.zaboravljenaLozinka?.pitanje_lozinka ?: stringResource(id = R.string.forgot_password_no_questions),
            korisnickoIme = uiState.zaboravljenaLozinka?.korisnicko_ime ?: "",
            onAnswerSubmit = { ime, odgovor ->
                viewModel.fetchZaboravljenaLozinkaPitanje(ime, odgovor)
            }
        )
    }

    HandlePasswordQuestionResponse(uiStateP = uiStateP, onSuccess = onNavigateToReset)
}

@Composable
fun ZaboravljenaLozinkaPitanje_mainCard(
    modifier: Modifier, korisnickoIme:String,question: String,onAnswerSubmit: (String, String) -> Unit
) {
    val context = LocalContext.current
    var odgovor by remember { mutableStateOf("") }

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.85f)
            .fillMaxHeight(0.5f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            ForgotPasswordHeader(title1 = stringResource(id = R.string.forgot_password), title2 = question)

            PasswordQuestionField(odgovor, onValueChange = { odgovor = it })

            val inf = stringResource(id = R.string.forgot_password_message_information)
            AuthButton(
                text = stringResource(id = R.string.forgot_password_conf_answer),
                validation = {
                    if (odgovor.isBlank()) {
                        Toast.makeText(context, inf, Toast.LENGTH_SHORT).show()
                        false
                    } else true
                },
                onClickAction = { onAnswerSubmit(korisnickoIme, odgovor) },
                modifier = Modifier.padding(top=12.dp, bottom = 24.dp)
            )
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
                odgovor.contains("Tačan odgovor!") -> { onSuccess() }
            }
        }
    }
}