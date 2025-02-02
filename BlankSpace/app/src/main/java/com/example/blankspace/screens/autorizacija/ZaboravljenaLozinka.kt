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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blankspace.ui.components.MyButton
import com.example.blankspace.ui.components.OutlinedTextFieldInput
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.ui.theme.TEXT_COLOR
import com.example.blankspace.viewModels.UiStateZL
import com.example.blankspace.viewModels.ZaboravljenaLozinkaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun ZaboravljenaLozinka(navController: NavController,viewModel: ZaboravljenaLozinkaViewModel){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))
        ZaboravljenaLozinka_mainCard(navController, viewModel)
    }
}

@Composable
fun ZaboravljenaLozinka_mainCard(navController: NavController,viewModel: ZaboravljenaLozinkaViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val context= LocalContext.current

    HandleForgotPasswordResponse(uiState, context, navController)

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
            HeadlineText("Zaboravljena lozinka")
            Spacer(modifier = Modifier.height(42.dp))

            var username by remember { mutableStateOf("") }

            ForgotPasswordField(username = username, onValueChange = { username = it })
            Spacer(modifier = Modifier.height(22.dp))

            ResetPasswordButton(username = username, context = context, viewModel = viewModel)
        }
    }
}

@Composable
fun HandleForgotPasswordResponse(
    uiState: UiStateZL,
    context: android.content.Context,
    navController: NavController
) {
    LaunchedEffect(uiState.zaboravljenaLozinka?.odgovor) {
        val odgovor = uiState.zaboravljenaLozinka?.odgovor
        if (!odgovor.isNullOrEmpty()) {
            if (odgovor.contains("Pogrešno korisničko ime")) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, odgovor, Toast.LENGTH_SHORT).show()
                }
                return@LaunchedEffect
            }
            delay(100)
            navController.navigate(Destinacije.ZaboravljenaLozinkaPitanje.ruta)
        }
    }
}

@Composable
fun ForgotPasswordField(username: String, onValueChange: (String) -> Unit) {
    OutlinedTextFieldInput(
        value = username,
        onValueChange = onValueChange,
        label = "Korisničko ime"
    )
}

@Composable
fun ResetPasswordButton(username: String, context: android.content.Context, viewModel: ZaboravljenaLozinkaViewModel) {
    MyButton(
        onClick = {
            if (username.isEmpty()) {
                Toast.makeText(context, "Niste uneli podatak!", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.fetchZaboravljenaLozinka(username)
            }
        },
        text = "Postavi pitanje"
    )
}