package com.example.blankspace.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.blankspace.ui.screens.Destinacije
import com.example.blankspace.screens.pocetne.RangLista
import com.example.blankspace.ui.screens.profil_rang_pravila.pravila_igre.PravilaIgre

fun NavGraphBuilder.profileGraph(
    navController: NavController
) {
    navigation(startDestination = Destinacije.PravilaIgre.ruta, route = "profile_graph") {
        composable(route = Destinacije.PravilaIgre.ruta) {
            PravilaIgre(onClick = { navController.navigate(Destinacije.Login.ruta) })
        }
        composable(route = Destinacije.RangLista.ruta) {
            RangLista()
        }
    }
}