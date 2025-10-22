package com.example.blankspace.screens.predlaganje

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.ui.components.OutlinedTextFieldInput
import com.example.blankspace.ui.components.SmallButton
import com.example.blankspace.ui.theme.TEXT_COLOR
import com.example.blankspace.ui.theme.TopAppBarHeight
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.PredlaganjeIzvodjacaViewModel
import com.example.blankspace.viewModels.UiStateL
import com.example.blankspace.viewModels.UiStatePI
import com.example.blankspace.viewModels.ZanrViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun PretragaPredlaganje(navController: NavController, viewModelLogin: LoginViewModel){
    Box(modifier = Modifier.fillMaxSize().padding(top= TopAppBarHeight +16.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))
        PretragaPredlaganje_mainCard(navController,viewModelLogin)
    }
}

@Composable
fun PretragaPredlaganje_mainCard(navController: NavController, viewModelLogin: LoginViewModel) {
    val viewModel: ZanrViewModel = hiltViewModel()
    val viewModelPredlaganje: PredlaganjeIzvodjacaViewModel = hiltViewModel()

    val uiStatePesme by viewModelPredlaganje.uiStateWebScrapper.collectAsState()

    val uiStateLogin by viewModelLogin.uiState.collectAsState()

    val context = LocalContext.current
    var selectedZanr by remember { mutableStateOf("") }
    var reci by remember { mutableStateOf("") }


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
                .padding(start = 16.dp,end=16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HeadlineText("Pretraga i predlaganje")

            PretragaPredlaganjeInput(value = reci, onValueChange = {reci=it})
            PretragaPredlaganjeButton(reci,"izvodjac",uiStateLogin,viewModelPredlaganje,context)

            Spacer(modifier = Modifier.height(22.dp))

            Box(modifier = Modifier.height(150.dp)) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp), // prilagođen padding
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(3.dp) // malo razmaka
                ) {
                    items(uiStatePesme.pesme) { pesme ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp) // bolja vidljivost
                        ) {
                            Text(
                                text = "${pesme.izvodjac} - ${pesme.naslov}",
                                color = TEXT_COLOR,
                                modifier = Modifier
                                    .weight(1f) // zauzima sav prostor osim za dugme
                            )
                            SmallButton(onClick = {

                            }, text = "Predloži", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }

        }
    }
}


@Composable
fun PretragaPredlaganjeInput(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextFieldInput(
        value = value,
        onValueChange = onValueChange,
        label = "search:"
    )
}

@Composable
fun PretragaPredlaganjeButton(
    reci: String,
    izvodjac: String,
    uiStateLogin: UiStateL,
    viewModelPredlaganje: PredlaganjeIzvodjacaViewModel,
    context: Context
) {
    SmallButton(onClick = {
        uiStateLogin.login?.korisnicko_ime?.let { viewModelPredlaganje.fetchPretragaPredlaganje(it,izvodjac,reci) }
    }, text = "Pretraga", style = MaterialTheme.typography.bodyMedium)
}