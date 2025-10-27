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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.blankspace.ui.components.HeadlineText
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.ui.components.ListItemRow
import com.example.blankspace.ui.theme.TEXT_COLOR
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.MojProfilViewModel

// Definicija nove palete za profil
val ProfileCardColor = Color.White // Čisto bela je najčistija
val ProfileAccentColor = Color(0xFF49006B) // Tamno ljubičasta za akcent

@Composable
fun MojProfil(navController: NavController, viewModelLogin: LoginViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        BgCard2() // Zadržana je BgCard2 pozadina
        MojProfil_mainCard(navController, viewModelLogin)
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
            "Korisničko ime" to profile.korisnicko_ime,
            "Lični poeni" to profile.licni_poeni.toString(),
            "Rang poeni" to profile.rang_poeni.toString(),
            "Rank" to profile.rank
        )
    } else {
        emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 64.dp), // Veliki vertikalni padding
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top // Postavljeno na vrh
    ) {

        Spacer(modifier = Modifier.height(28.dp))
        // 🔝 Naslov van kartice (na pozadini)
        /*Text(
            text = "Moj Profil",
            color = ProfileCardColor, // Beli tekst za naslov
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.align(Alignment.Start) // Pomereno ulevo
        )*/
        Spacer(modifier = Modifier.height(44.dp))

        // 💳 Glavna Kartica Profita - Cisto Bela
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(16.dp, RoundedCornerShape(36.dp)), // Ekstremna senka
            colors = CardDefaults.cardColors(containerColor = ProfileCardColor), // Čisto bela
            shape = RoundedCornerShape(36.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp), // Veći padding unutar kartice
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Ime korisnika (naglašeno)
                Text(
                    text="Moj Profil",
                    //text = profile?.korisnicko_ime ?: "Korisnik",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = ProfileAccentColor
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Lista podacima
                MojProfilDataList(profileItems)
            }
        }
    }
}

@Composable
fun MojProfilDataList(profileItems: List<Pair<String, String>>) {
    // Ovde koristimo čisti Column/LazyColumn, bez dodatnih kartica ili bordera,
    // za ultra-čist izgled.
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp) // Razmak između stavki
    ) {
        itemsIndexed(profileItems) { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Naslov
                Text(
                    text = item.first,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
                // Vrednost
                Text(
                    text = item.second,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = ProfileAccentColor // Akcent boja
                )
            }
            // Horizontalna linija za razdvajanje (tanja)
            if (index < profileItems.size - 1) {
                Divider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp)
            }
        }
    }
}