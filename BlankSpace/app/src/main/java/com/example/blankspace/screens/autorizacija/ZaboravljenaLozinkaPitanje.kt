package com.example.blankspace.screens.autorizacija

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.viewModels.UiStateZL
import com.example.blankspace.viewModels.UiStateZLP
import com.example.blankspace.viewModels.ZaboravljenaLozinkaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)

@Composable
fun ZaboravljenaLozinkaPitanje(navController: NavController, viewModel: ZaboravljenaLozinkaViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()

        ZaboravljenaLozinkaPitanje_mainCard(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ZaboravljenaLozinkaPitanje_mainCard(navController: NavController, viewModel: ZaboravljenaLozinkaViewModel, modifier: Modifier) {
    val uiState by viewModel.uiState.collectAsState()
    val uiStateP by viewModel.uiStateP.collectAsState()
    val context = LocalContext.current

    HandlePasswordQuestionResponse(uiStateP, context, navController)

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
            PasswordQuestionHeader(uiState)

            var odgovor by remember { mutableStateOf("") }

            PasswordQuestionField(odgovor, onValueChange = { odgovor = it })


            PasswordQuestionButton(odgovor, context, uiState, viewModel)
        }
    }
}

@Composable
fun PasswordQuestionHeader(uiState: UiStateZL) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Zaboravljena lozinka",
            color = PrimaryDark,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "${uiState.zaboravljenaLozinka?.pitanje_lozinka}",
            style = MaterialTheme.typography.titleMedium,
            color = PrimaryDark,
            fontWeight = FontWeight.SemiBold,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Composable
fun HandlePasswordQuestionResponse(
    uiStateP: UiStateZLP,
    context: android.content.Context,
    navController: NavController
) {
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
                    navController.navigate(Destinacije.PromenaLozinke.ruta)
                }
            }
        }
    }
}

// **MODIFIKOVANO**: Stilizovano polje
@Composable
fun PasswordQuestionField(odgovor: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = odgovor,
        onValueChange = onValueChange,
        label = { Text("Odgovor", color = PrimaryDark) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AccentPink,
            unfocusedBorderColor = PrimaryDark.copy(alpha = 0.5f),
            cursorColor = AccentPink,
            focusedTextColor = PrimaryDark,
            unfocusedTextColor = PrimaryDark
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PasswordQuestionButton(odgovor: String, context: android.content.Context, uiState: UiStateZL, viewModel: ZaboravljenaLozinkaViewModel) {
    var pressed by remember { mutableStateOf(false) }
    val elevation = if (pressed) 2.dp else 8.dp

    Button(
        onClick = {
            if (odgovor.isBlank()) {
                Toast.makeText(context, "Niste uneli odgovor!", Toast.LENGTH_SHORT).show()
            } else {
                pressed = true
                uiState.zaboravljenaLozinka?.korisnicko_ime?.let {
                    viewModel.fetchZaboravljenaLozinkaPitanje(it, odgovor)
                }
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = AccentPink,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(elevation, RoundedCornerShape(16.dp))
    ) {
        Text(
            text = "Potvrdi odgovor",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
    LaunchedEffect(pressed) {
        if (pressed) {
            delay(100)
            pressed = false
        }
    }
}