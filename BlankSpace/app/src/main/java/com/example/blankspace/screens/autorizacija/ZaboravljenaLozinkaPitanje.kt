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
import com.example.blankspace.viewModels.UiStateZLP
import com.example.blankspace.viewModels.ZaboravljenaLozinkaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ZaboravljenaLozinkaPitanje(navController: NavController,viewModel: ZaboravljenaLozinkaViewModel){
    Box(modifier = Modifier.fillMaxSize().padding(top=52.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))
        ZaboravljenaLozinkaPitanje_mainCard(navController,viewModel)
    }
}

@Composable
fun ZaboravljenaLozinkaPitanje_mainCard(navController: NavController,viewModel: ZaboravljenaLozinkaViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val uiStateP by viewModel.uiStateP.collectAsState()
    val context= LocalContext.current

    HandlePasswordQuestionResponse(uiStateP, context, navController)

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
            PasswordQuestionHeader(uiState)
            Spacer(modifier = Modifier.height(12.dp))

            var odgovor by remember { mutableStateOf("") }

            PasswordQuestionField(odgovor, onValueChange = { odgovor = it })
            Spacer(modifier = Modifier.height(22.dp))

            PasswordQuestionButton(odgovor,context,uiState,viewModel)
        }
    }
}

@Composable
fun PasswordQuestionHeader(uiState: UiStateZL){
    HeadlineText("Zaboravljena lozinka")
    Spacer(modifier = Modifier.height(22.dp))

    Text(
        text = "${uiState.zaboravljenaLozinka?.pitanje_lozinka}",
        style = MaterialTheme.typography.titleSmall,
        color = TEXT_COLOR
    )
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

@Composable
fun PasswordQuestionField(odgovor: String, onValueChange: (String) -> Unit) {
    OutlinedTextFieldInput(
        value = odgovor,
        onValueChange = onValueChange,
        label = "Odgovor"
    )
}

@Composable
fun PasswordQuestionButton(odgovor: String, context: android.content.Context, uiState: UiStateZL,viewModel: ZaboravljenaLozinkaViewModel){
    MyButton(onClick = {
        if(odgovor==""){
            Toast.makeText(context, "Niste uneli podatak!", Toast.LENGTH_SHORT).show()
        }
        else{
            uiState.zaboravljenaLozinka?.korisnicko_ime?.let {
                viewModel.fetchZaboravljenaLozinkaPitanje(
                    it,odgovor)
            }
        }
    },text="Odgovor na pitanje")
}