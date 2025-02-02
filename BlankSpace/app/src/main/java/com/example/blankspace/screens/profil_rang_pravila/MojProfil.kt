package com.example.blankspace.screens.profil_rang_pravila


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.components.ListItemRow
import com.example.blankspace.ui.theme.TEXT_COLOR
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.MojProfilViewModel

@Composable
fun MojProfil(navController: NavController,viewModelLogin:LoginViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2()
        MojProfil_mainCard(navController,viewModelLogin)
    }
}

@Composable
fun MojProfil_mainCard(navController: NavController, viewModelLogin: LoginViewModel) {
    val viewModel: MojProfilViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val uiStateLogin by viewModelLogin.uiState.collectAsState()

    LaunchedEffect(Unit) {
        uiStateLogin.login?.korisnicko_ime?.let { viewModel.fetchMojProfil(it) }
    }

    val profile = uiState.mojprofil
    val profileItems = if (profile != null) {
        listOf(
            "Ime" to profile.ime,
            "Prezime" to profile.prezime,
            "Korisnicko ime" to profile.korisnicko_ime,
            "Licni poeni" to profile.licni_poeni.toString(),
            "Rang poeni" to profile.rang_poeni.toString(),
            "Rank" to profile.rank
        )
    } else {
        emptyList()
    }

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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(22.dp))
            HeadlineText("Moj profil")
            Spacer(modifier = Modifier.height(22.dp))

            MojProfilCard(profileItems)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun MojProfilCard(profileItems: List<Pair<String, String>>){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .border(3.dp, TEXT_COLOR, RoundedCornerShape(3.dp))
                .background(Color(0xFFF0DAE7))
        ) {
            LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
                itemsIndexed(profileItems) { index, item ->
                    ListItemRow(item, index, listOf(item))
                }
            }
        }
    }
}