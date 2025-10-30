package com.example.blankspace.ui.bars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.ui.theme.TEXT_COLOR
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import com.example.blankspace.ui.theme.TEXT_COLOR
import com.example.blankspace.viewModels.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlankSpaceTopAppBar(navController: NavController, currentRoute: String, viewModelLogin: LoginViewModel) {
    val uiStateLogin by viewModelLogin.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxWidth().height(100.dp).background(Color.Transparent)) {
        Box(modifier = Modifier.fillMaxSize().background(brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFADD8E6), Color(0xFFADD8E6)))).blur(8.dp))

        TopAppBar(
            title = { Text(text = "Blank Space", color = TEXT_COLOR, style = MaterialTheme.typography.titleLarge) },
            actions = {
                when {
                    Destinacije.Pocetna.ruta == currentRoute -> {
                        IconButton(onClick = { navController.navigate(Destinacije.Pocetna.ruta) }) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "Početna", tint = TEXT_COLOR)
                        }
                        IconButton(onClick = { navController.navigate(Destinacije.Login.ruta) }) {
                            Icon(Icons.Default.Person, contentDescription = "Uloguj se", tint = TEXT_COLOR)
                        }
                    }
                    Destinacije.PravilaIgre.ruta == currentRoute -> {
                        IconButton(onClick = {
                            if (uiStateLogin.login?.access != null) {
                                uiStateLogin.login?.tip?.let {
                                    when (it) {
                                        "S" -> navController.navigate(Destinacije.PocetnaStudent.ruta)
                                        "B" -> navController.navigate(Destinacije.PocetnaBrucos.ruta)
                                        "M" -> navController.navigate(Destinacije.PocetnaMaster.ruta)
                                        "A" -> navController.navigate(Destinacije.PocetnaAdmin.ruta)
                                    }
                                }
                            } else {
                                navController.navigate(Destinacije.Pocetna.ruta)
                            }
                        }) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "Početna", tint = TEXT_COLOR)
                        }
                    }
                    else -> {
                        IconButton(onClick = {
                            navController.navigate(Destinacije.Login.ruta)
                            viewModelLogin.izloguj_se()
                        }) { Icon(Icons.Default.PlayArrow, contentDescription = "Izloguj se", tint = TEXT_COLOR) }
                    }
                }

                IconButton(onClick = { navController.navigate(Destinacije.PravilaIgre.ruta) }) {
                    Icon(Icons.Default.Info, contentDescription = "Pravila igre", tint = TEXT_COLOR)
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
            modifier = Modifier.fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x66ADD8E6),
                            Color.Transparent
                        )
                    )
                )
        )
    }
}