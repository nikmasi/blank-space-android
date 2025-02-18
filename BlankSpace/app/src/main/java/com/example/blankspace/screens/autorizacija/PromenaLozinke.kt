package com.example.blankspace.screens.autorizacija

import android.widget.Toast
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blankspace.ui.components.MyButton
import com.example.blankspace.ui.components.OutlinedTextFieldInput
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.UiStateNL
import com.example.blankspace.viewModels.UiStateZL
import com.example.blankspace.viewModels.ZaboravljenaLozinkaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PromenaLozinke(navController: NavController,viewModel: ZaboravljenaLozinkaViewModel,loginViewModel:LoginViewModel){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))
        PromenaLozinke_mainCard(navController, viewModel,loginViewModel)
    }
}

@Composable
fun PromenaLozinke_mainCard(navController: NavController,viewModel: ZaboravljenaLozinkaViewModel,loginViewModel: LoginViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val uiStateNL by viewModel.uiStateNL.collectAsState()
    val context= LocalContext.current

    HandlePasswordChangeResponse(uiStateNL, context, loginViewModel, navController, uiState)

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
            HeadlineText("Promena lozinke")
            Spacer(modifier = Modifier.height(42.dp))

            var novaLozinka by remember { mutableStateOf("") }
            var potvrdaLozinke by remember { mutableStateOf("") }

            PasswordField(label = "Nova lozinka", value = novaLozinka, onValueChange = { novaLozinka = it })
            PasswordField(label = "Potvrdite lozinku", value = potvrdaLozinke, onValueChange = { potvrdaLozinke = it })

            Spacer(modifier = Modifier.height(22.dp))
            ChangePasswordButton(novaLozinka, potvrdaLozinke, uiState, viewModel, context)
        }
    }
}

@Composable
fun HandlePasswordChangeResponse(
    uiStateNL: UiStateNL,
    context: android.content.Context,
    loginViewModel: LoginViewModel,
    navController: NavController,
    uiState: UiStateZL
) {
    LaunchedEffect(uiStateNL.novaLozinka?.odgovor) {
        val odgovor = uiStateNL.novaLozinka?.odgovor
        if (!odgovor.isNullOrEmpty()) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, odgovor, Toast.LENGTH_SHORT).show()
            }
            if (odgovor.contains("Lozink")) {
                return@LaunchedEffect
            } else if (odgovor.contains("Uspeh")) {
                loginViewModel.setKorisnikZL(uiState)
                navController.navigate(Destinacije.Login.ruta)
            }
        }
    }
}

@Composable
fun PasswordField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextFieldInput(
        value = value,
        onValueChange = onValueChange,
        label = label,
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
fun ChangePasswordButton(
    novaLozinka: String,
    potvrdaLozinke: String,
    uiState: UiStateZL,
    viewModel: ZaboravljenaLozinkaViewModel,
    context: android.content.Context
) {
    MyButton(
        onClick = {
            if (novaLozinka.isEmpty() || potvrdaLozinke.isEmpty()) {
                Toast.makeText(context, "Niste uneli sve podatke!", Toast.LENGTH_SHORT).show()
            } else {
                uiState.zaboravljenaLozinka?.korisnicko_ime?.let {
                    viewModel.fetchNovaLozinka(it, novaLozinka, potvrdaLozinke)
                }
            }
        },
        text = "Promenite lozinku"
    )
}
