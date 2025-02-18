package com.example.blankspace.screens.predlaganje

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.blankspace.ui.components.OutlinedTextFieldInput
import com.example.blankspace.ui.components.SmallButton
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.ui.theme.TEXT_COLOR
import com.example.blankspace.ui.theme.TopAppBarHeight
import com.example.blankspace.viewModels.IzvodjacZanrViewModel
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.PredlaganjeIzvodjacaViewModel
import com.example.blankspace.viewModels.PredlaganjePesmeViewModel
import com.example.blankspace.viewModels.UiStateL
import com.example.blankspace.viewModels.UiStatePP
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun PredlaganjePesme(navController: NavController,viewModelLogin:LoginViewModel){
    Box(modifier = Modifier
        .fillMaxSize().padding(top= TopAppBarHeight +16.dp)) {
        BgCard2()
        Spacer(Modifier.padding(top = 22.dp))
        PredlaganjePesme_mainCard(navController,viewModelLogin)
    }
}

@Composable
fun PredlaganjePesme_mainCard(navController: NavController,viewModelLogin:LoginViewModel) {
    val viewModel: IzvodjacZanrViewModel = hiltViewModel()
    val viewModelPredlaganje: PredlaganjePesmeViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsState()
    val uiStateLogin by viewModelLogin.uiState.collectAsState()
    val uiStatePredlaganjePesme by viewModelPredlaganje.uiState.collectAsState()

    val context = LocalContext.current
    var selectedZanr by remember { mutableStateOf("") }
    var selectedIzvodjac by remember { mutableStateOf("") }
    val selectedOption = remember { mutableStateOf("")}

    HandlePredlaganjePesmeResponse(navController,context,uiStatePredlaganjePesme)

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight(0.7f),
        shape = RoundedCornerShape(60.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(22.dp))
            var pesma by remember { mutableStateOf("") }

            OutlinedTextFieldInput(
                value = pesma,
                onValueChange = { pesma = it },
                label = "Naziv pesme"
            )

            Spacer(modifier = Modifier.height(22.dp))

            // Loading indicator or content
            if (uiState.isRefreshing) {
                CircularProgressIndicator()
            } else {
                if (uiState.error != null) {
                    Text(text = "Greška: ${uiState.error}", color = Color.Red)
                } else {
                    var expanded by remember { mutableStateOf(false) }
                    selectedOption.value =uiState.izvodjaci[0].ime

                    Column(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = selectedOption.value,
                            onValueChange = {},
                            label = { Text("Ime izvodjaca") },
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth().padding(start = 25.dp,end=25.dp),
                            trailingIcon = {
                                IconButton(onClick = { expanded = !expanded }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = null
                                    )
                                }
                            }
                        )

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            uiState.izvodjaci.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option.ime) },
                                    onClick = {
                                        selectedIzvodjac=option.ime
                                        selectedOption.value = option.ime
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))
                HeadlineText("Naziv žanra ")

                Box(modifier = Modifier.height(150.dp)) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth().padding(start = 53.dp),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(0.dp)  // Razmak između stavki
                    ) {
                        items(uiState.zanrovi) { zanr ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                RadioButton(
                                    selected = selectedZanr == zanr.id.toString(),
                                    onClick = { selectedZanr = zanr.id.toString() }
                                )
                                Text(text = zanr.naziv, color = TEXT_COLOR)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            SmallButton(onClick = {
                if (selectedZanr!="") {
                    uiStateLogin.login?.korisnicko_ime?.let {
                        viewModelPredlaganje.fetchPredlaganjePesme(
                            it,pesma,selectedIzvodjac,selectedZanr)
                    }
                } else {
                    Toast.makeText(context, "Niste izabrali nijedan žanr", Toast.LENGTH_SHORT).show()
                }
            },text = "Predloži", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun HandlePredlaganjePesmeResponse(
    navController: NavController,
    context: Context,
    uiStatePredlaganjePesme: UiStatePP
){
    LaunchedEffect(uiStatePredlaganjePesme.predlaganjepesme?.odgovor) {
        val odgovor = uiStatePredlaganjePesme.predlaganjepesme?.odgovor
        if (!odgovor.isNullOrEmpty()) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, odgovor, Toast.LENGTH_SHORT).show()
            }
            if (odgovor.contains("U bazi vec postoji pesma sa imenom")) {
                return@LaunchedEffect
            }
            delay(3000)
            navController.popBackStack()
        }
    }
}