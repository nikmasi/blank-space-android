package com.example.blankspace.screens.autorizacija.registracija

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.blankspace.R
import com.example.blankspace.screens.autorizacija.auth_components.AuthHeader
import com.example.blankspace.screens.autorizacija.auth_components.AuthNavigation
import com.example.blankspace.screens.autorizacija.auth_components.DividerWithIconModernAuth
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.modifiers.horizontalVerticalPadding
import com.example.blankspace.ui.modifiers.mainCardStyle
import com.example.blankspace.viewModels.RegistracijaViewModel
import com.example.blankspace.viewModels.UiStateR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.blankspace.ui.theme.*

@Composable
fun Registracija(onBackToLogin: ()->Unit, onClickPocetna: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()

        Registracija_mainCard(
            modifier = Modifier.align(Alignment.Center),
            onBackToLogin = onBackToLogin,
            onClickPocetna = onClickPocetna
        )
    }
}

@Composable
fun Registracija_mainCard(modifier: Modifier,onBackToLogin: ()->Unit, onClickPocetna: () -> Unit) {
    val viewModel: RegistracijaViewModel = hiltViewModel()
    val uiStateRegistracija by viewModel.uiState.collectAsState()

    HandleRegistrationResponse(uiStateRegistracija, onClick = onClickPocetna)

    Spacer(modifier = Modifier.height(19.dp))
    Surface(
        color = CardContainerColor,
        modifier = modifier.mainCardStyle(widthFraction = 0.9f, heightFraction = 0.8f, cornerRadius = 32.dp),
        shape = RoundedCornerShape(32.dp)
    ) {
        Spacer(modifier = Modifier.height(19.dp))
        LazyColumn(
            modifier = Modifier.horizontalVerticalPadding(horizontalP = 32.dp, verticalP = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item { AuthHeader(stringResource(id = R.string.title_signUp)) }

            item {
                var name by remember { mutableStateOf("") }
                var username by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var co_password by remember { mutableStateOf("") }
                var question by remember { mutableStateOf("") }
                var answer by remember { mutableStateOf("") }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    RegistrationFields(
                        name = name,
                        username = username,
                        password = password,
                        co_password = co_password,
                        question = question,
                        answer = answer,
                        onValueChange = { field, value ->
                            when (field) {
                                "name" -> name = value
                                "username" -> username = value
                                "password" -> password = value
                                "co_password" -> co_password = value
                                "question" -> question = value
                                "answer" -> answer = value
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    RegistrationButton(name, username, password, co_password, question, answer, onSignUp = { name, username, password, co_password, question, answer ->
                        viewModel.fetchRegistracija(name, username, password, co_password, question, answer)
                    })

                    Spacer(modifier = Modifier.height(20.dp))
                    DividerWithIconModernAuth()
                    Spacer(modifier = Modifier.height(16.dp))

                    AuthNavigation(title = stringResource(id = R.string.registration_already_have_account),
                        textClick = stringResource(id = R.string.registration_log_in), onBackToLogin)
                }
            }
        }
    }
}

@Composable
fun HandleRegistrationResponse(uiStateRegistracija: UiStateR, onClick: () -> Unit) {
    val context = LocalContext.current

    LaunchedEffect(uiStateRegistracija.registration?.odgovor) {
        val odgovor = uiStateRegistracija.registration?.odgovor
        if (!odgovor.isNullOrEmpty()) {
            if (odgovor.contains("Lozinka") ||
                odgovor.contains("Odgovor je duži od 255 karaktera!") ||
                odgovor.contains("Pitanje je duže od 255 karaktera!") ||
                odgovor.contains("Korisnik sa tim imenom već postoji!")
            ) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, odgovor, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(uiStateRegistracija.registration?.korisnicko_ime) {
        if (uiStateRegistracija.registration != null && uiStateRegistracija.registration?.odgovor.isNullOrEmpty()) {
            onClick()
        }
    }
}