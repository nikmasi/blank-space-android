package com.example.blankspace.screens.predlaganje

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.cards.BgCard2
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.PredlaganjeIzvodjacaViewModel
import com.example.blankspace.viewModels.UiStateL

private val PrimaryDark = Color(0xFF49006B)
private val AccentPink = Color(0xFFEC8FB7)
private val CardContainerColor = Color(0xFFF0DAE7)
private val InputBorderColor = PrimaryDark.copy(alpha = 0.5f)
private val ListItemBackground = Color(0xFFFFFFFF)

data class SelectedSong(
    val izvodjac: String,
    val naslov: String
)

@Composable
fun PretragaPredlaganje(navController: NavController, viewModelLogin: LoginViewModel){
    Box(modifier = Modifier.fillMaxSize().padding(top= 52.dp +16.dp)) {
        BgCard2()
        PretragaPredlaganje_mainCardStyled(navController,viewModelLogin, Modifier.align(Alignment.Center))
    }
}

@Composable
fun PretragaPredlaganje_mainCardStyled(navController: NavController, viewModelLogin: LoginViewModel, modifier: Modifier) {
    val viewModelPredlaganje: PredlaganjeIzvodjacaViewModel = hiltViewModel()

    val uiStatePesme by viewModelPredlaganje.uiStateWebScrapper.collectAsState()
    val uiStateLogin by viewModelLogin.uiState.collectAsState()

    val context = LocalContext.current
    var reci by remember { mutableStateOf("") }
    val izvodjac by remember { mutableStateOf("izvodjac") }

    var showPredlogDialog by remember { mutableStateOf(false) }
    var selectedSongForDialog by remember { mutableStateOf<SelectedSong?>(null) }


    Surface(
        color = CardContainerColor,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.75f)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            HeadlineTextStyled("Pretraga i predlaganje")
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PretragaPredlaganjeInputStyled(
                    value = reci,
                    onValueChange = { reci = it },
                    modifier = Modifier.weight(1f)
                )
                PretragaPredlaganjeButtonStyled(
                    reci = reci,
                    izvodjac = izvodjac,
                    uiStateLogin = uiStateLogin,
                    viewModelPredlaganje = viewModelPredlaganje,
                    context = context
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Divider(color = InputBorderColor, thickness = 1.dp)
            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (uiStatePesme.isRefreshing) {
                    CircularProgressIndicator(color = Color.Red, modifier = Modifier.align(Alignment.Center))
                } else if (uiStatePesme.pesme.isEmpty() && reci.isNotEmpty()) {
                    Text(
                        text = "Nema pronađenih pesama.",
                        color = PrimaryDark.copy(alpha = 0.7f),
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiStatePesme.pesme) { pesme ->
                            PredlogListItem(
                                izvodjac = pesme.izvodjac,
                                naslov = pesme.naslov,
                                onPredloziClick = {
                                    // Postavi izabranu pesmu i prikaži dijalog
                                    selectedSongForDialog = SelectedSong(pesme.izvodjac, pesme.naslov)
                                    showPredlogDialog = true
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showPredlogDialog && selectedSongForDialog != null) {
        PredlogSelectionDialog(
            selectedSong = selectedSongForDialog!!,
            onDismiss = { showPredlogDialog = false },
            onPredloziPesmu = { izvodjacArg, naslovArg ->
                showPredlogDialog = false
                navController.navigate(Destinacije.PredlaganjePesme.ruta)
            },
            onPredloziIzvodjaca = { izvodjacArg, naslovArg ->
                showPredlogDialog = false
                navController.navigate(Destinacije.PredlaganjeIzvodjaca.ruta)
            }
        )
    }
}

@Composable
fun HeadlineTextStyled(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge.copy(
            color = PrimaryDark,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp
        )
    )
}

@Composable
fun PretragaPredlaganjeInputStyled(value: String, onValueChange: (String) -> Unit, modifier: Modifier) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Pretraži izvođača/pesmu...") },
        modifier = modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AccentPink,
            unfocusedBorderColor = InputBorderColor,
            focusedLabelColor = AccentPink,
            cursorColor = AccentPink,
            unfocusedLabelColor = InputBorderColor
        ),
        shape = RoundedCornerShape(12.dp),
        singleLine = true
    )
}

@Composable
fun PretragaPredlaganjeButtonStyled(
    reci: String,
    izvodjac: String,
    uiStateLogin: UiStateL,
    viewModelPredlaganje: PredlaganjeIzvodjacaViewModel,
    context: Context
) {
    Button(
        onClick = {
            if (reci.isBlank()) {
                Toast.makeText(context, "Unesite tekst za pretragu.", Toast.LENGTH_SHORT).show()
                return@Button
            }
            uiStateLogin.login?.korisnicko_ime?.let {
                viewModelPredlaganje.fetchPretragaPredlaganje(it, izvodjac, reci)
            } ?: Toast.makeText(context, "Korisnik nije prijavljen.", Toast.LENGTH_SHORT).show()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = AccentPink,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.height(56.dp)
    ) {
        Text("Pretraga", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun PredlogListItem(izvodjac: String, naslov: String, onPredloziClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(12.dp))
            .background(ListItemBackground, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
            Text(
                text = naslov,
                color = PrimaryDark,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                maxLines = 1
            )
            Text(
                text = izvodjac,
                color = PrimaryDark.copy(alpha = 0.7f),
                fontSize = 13.sp,
                maxLines = 1
            )
        }

        Button(
            onClick = onPredloziClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryDark,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
            modifier = Modifier.height(36.dp)
        ) {
            Text("Predloži", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun PredlogSelectionDialog(
    selectedSong: SelectedSong,
    onDismiss: () -> Unit,
    onPredloziPesmu: (String, String) -> Unit,
    onPredloziIzvodjaca: (String, String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Izaberite tip predloga",
                fontWeight = FontWeight.Bold,
                color = PrimaryDark
            )
        },
        text = {
            Column {
                Text(
                    text = "Šta želite da predložite za:",
                    color = PrimaryDark.copy(alpha = 0.7f)
                )
                Text(
                    text = "${selectedSong.izvodjac} - ${selectedSong.naslov}",
                    fontWeight = FontWeight.Bold,
                    color = AccentPink,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        },
        containerColor = CardContainerColor,
        confirmButton = {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { onPredloziPesmu(selectedSong.izvodjac, selectedSong.naslov) },
                    colors = ButtonDefaults.buttonColors(containerColor = AccentPink, contentColor = Color.White),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                ) {
                    Text("Predloži Pesmu")
                }

                Button(
                    onClick = { onPredloziIzvodjaca(selectedSong.izvodjac, selectedSong.naslov) },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryDark, contentColor = Color.White),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Predloži Izvođača")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Odustani", color = PrimaryDark.copy(alpha = 0.7f))
            }
        }
    )
}