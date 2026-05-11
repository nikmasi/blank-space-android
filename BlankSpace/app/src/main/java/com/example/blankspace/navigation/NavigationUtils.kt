package com.example.blankspace.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.blankspace.screens.Destinacije

@Composable
fun rememberCurrentRoute(navController: NavController): String {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    return currentDestination?.route ?: Destinacije.Pocetna.ruta
}