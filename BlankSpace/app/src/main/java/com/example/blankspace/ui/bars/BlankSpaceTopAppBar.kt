package com.example.blankspace.ui.bars

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.ui.theme.TEXT_COLOR
import com.example.blankspace.viewModels.LoginViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlankSpaceTopAppBar(navController: NavController, currentRoute: String, viewModelLogin: LoginViewModel) {

    @Composable
    fun createButton(label: String, onClick: () -> Unit) {
        TextButton(onClick = onClick) {
            Text(label, color = TEXT_COLOR)
        }
    }

    TopAppBar(
        title = { Text("Blank Space") },
        actions = {
            if (Destinacije.Pocetna.ruta == currentRoute) {
                createButton("Početna") {
                    navController.navigate(Destinacije.Pocetna.ruta)
                }
            }

            createButton("Pravila igre") {
                navController.navigate(Destinacije.PravilaIgre.ruta)
            }

            if (Destinacije.Login.ruta != currentRoute || Destinacije.Registracija.ruta != currentRoute ||
                Destinacije.ZaboravljenaLozinka.ruta !=currentRoute ||
                Destinacije.ZaboravljenaLozinkaPitanje.ruta !=currentRoute ||
                Destinacije.Pocetna.ruta !=currentRoute) {
                createButton("Izloguj se") {
                    viewModelLogin.izloguj_se()
                    navController.navigate(Destinacije.Login.ruta)
                }
            }

            if (Destinacije.Pocetna.ruta == currentRoute) {
                createButton("Uloguj se") {
                    navController.navigate(Destinacije.Login.ruta)
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(0xFFADD8E6)
        )
    )
}