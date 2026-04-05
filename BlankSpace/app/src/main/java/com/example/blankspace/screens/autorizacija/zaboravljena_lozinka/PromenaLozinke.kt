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
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.UiStateNL
import com.example.blankspace.viewModels.ZaboravljenaLozinkaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.blankspace.ui.theme.*

@Composable
fun PromenaLozinke(viewModel: ZaboravljenaLozinkaViewModel, loginViewModel: LoginViewModel, onClick: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()

        PromenaLozinke_mainCard(
            viewModel = viewModel,
            modifier = Modifier.align(Alignment.Center),
            onClick = {
                loginViewModel.setKorisnikZL(uiState)
                onClick()
            }
        )
    }
}

@Composable
fun PromenaLozinke_mainCard(viewModel: ZaboravljenaLozinkaViewModel, modifier: Modifier, onClick: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val uiStateNL by viewModel.uiStateNL.collectAsState()
    val context = LocalContext.current

    var novaLozinka by remember { mutableStateOf("") }
    var potvrdaLozinke by remember { mutableStateOf("") }

    HandlePasswordChangeResponse(uiStateNL, onClick = onClick)

    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.85f)
            .fillMaxHeight(0.55f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ForgotPasswordHeader(
                title1 = stringResource(id = R.string.change_password),
                title2 = stringResource(id = R.string.change_password_enter_new_password)
            )

            Spacer(modifier = Modifier.height(24.dp))

            PasswordFieldStyled(
                label = stringResource(id = R.string.change_password_new_password),
                value = novaLozinka,
                onValueChange = { novaLozinka = it }
            )
            Spacer(modifier = Modifier.height(12.dp))
            PasswordFieldStyled(
                label = stringResource(id = R.string.change_password_confirm_password),
                value = potvrdaLozinke,
                onValueChange = { potvrdaLozinke = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            val inf = stringResource(id = R.string.login_missing_information)
            AuthButton(
                text = stringResource(id = R.string.change_password_change_password),
                onClickAction = {
                    uiState.zaboravljenaLozinka?.korisnicko_ime?.let {
                        viewModel.fetchNovaLozinka(it, novaLozinka, potvrdaLozinke)
                    }
                },
                validation = {
                    if (novaLozinka.isBlank() || potvrdaLozinke.isBlank()) {
                        Toast.makeText(context, inf, Toast.LENGTH_SHORT).show()
                        false
                    } else true
                },
                modifier = Modifier
            )
        }
    }
}

@Composable
fun HandlePasswordChangeResponse(uiStateNL: UiStateNL, onClick: () -> Unit) {
    val context = LocalContext.current

    LaunchedEffect(uiStateNL.novaLozinka?.odgovor) {
        val odgovor = uiStateNL.novaLozinka?.odgovor
        if (!odgovor.isNullOrEmpty()) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, odgovor, Toast.LENGTH_SHORT).show()
            }
            if (odgovor.contains("Lozink")) {
                return@LaunchedEffect
            } else if (odgovor.contains("Uspeh")) { onClick() }
        }
    }
}