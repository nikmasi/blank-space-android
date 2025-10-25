package com.example.blankspace.ui.bars

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.ui.theme.TEXT_COLOR
import com.example.blankspace.viewModels.LoginViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlankSpaceTopAppBar(navController: NavController, currentRoute: String, viewModelLogin: LoginViewModel) {
    val uiStateLogin by viewModelLogin.uiState.collectAsState()
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
                createButton("Početna") { navController.navigate(Destinacije.Pocetna.ruta) }
                createButton("Uloguj se") { navController.navigate(Destinacije.Login.ruta) }
            }
            else if(Destinacije.Login.ruta==currentRoute){ }
            else if(Destinacije.PravilaIgre.ruta==currentRoute){
                createButton("Početna") {
                    if(uiStateLogin.login?.access!=null){
                        uiStateLogin.login?.tip?.let {
                            when (it) {
                                "S" -> navController.navigate(Destinacije.PocetnaStudent.ruta)
                                "B" -> navController.navigate(Destinacije.PocetnaBrucos.ruta)
                                "M" -> navController.navigate(Destinacije.PocetnaMaster.ruta)
                                "A" -> navController.navigate(Destinacije.PocetnaAdmin.ruta)
                            }
                        }
                    }else{ navController.navigate(Destinacije.Pocetna.ruta) }
                }
            }
            else if (Destinacije.Login.ruta != currentRoute || Destinacije.Registracija.ruta != currentRoute ||
                Destinacije.ZaboravljenaLozinka.ruta !=currentRoute ||
                Destinacije.ZaboravljenaLozinkaPitanje.ruta !=currentRoute ||
                Destinacije.Pocetna.ruta !=currentRoute) {
                createButton("Izloguj se") {
                    navController.navigate(Destinacije.Login.ruta)
                    viewModelLogin.izloguj_se()
                    //navController.navigate(Destinacije.Login.ruta)
                }
            }
            createButton("Pravila igre") { navController.navigate(Destinacije.PravilaIgre.ruta) }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFADD8E6))
    )
}

