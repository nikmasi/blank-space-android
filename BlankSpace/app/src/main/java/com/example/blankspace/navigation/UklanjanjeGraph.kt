package com.example.blankspace.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.uklanjanje.IzborIzvodjacaUklanjanjePesme
import com.example.blankspace.screens.uklanjanje.IzborZanraUklanjanjeIzvodjaca
import com.example.blankspace.screens.uklanjanje.IzborZanraUklanjanjePesme
import com.example.blankspace.screens.uklanjanje.UklanjanjeIzvodjaca
import com.example.blankspace.screens.uklanjanje.UklanjanjeKorisnika
import com.example.blankspace.screens.uklanjanje.UklanjanjePesme
import com.example.blankspace.screens.uklanjanje.UklanjanjeZanra
import com.example.blankspace.viewModels.UklanjanjeViewModel

fun NavGraphBuilder.uklanjanjeGraph(
    navController: NavController
) {
    navigation(startDestination = Destinacije.IzborIzvodjacaUklanjanjePesme.ruta, route = "uklanjanje_graph") {
        composable(route = Destinacije.IzborZanraUklanjanjeIzvodjaca.ruta){
            IzborZanraUklanjanjeIzvodjaca(navController)
        }
        composable(route = Destinacije.IzborZanraUklanjanjePesme.ruta){
            val viewModelUklanjanje: UklanjanjeViewModel =
                it.sharedHiltViewModel<UklanjanjeViewModel>(navController)

            IzborZanraUklanjanjePesme(navController,viewModelUklanjanje)
        }
        composable(route = Destinacije.IzborIzvodjacaUklanjanjePesme.ruta) {
            val viewModelUklanjanje: UklanjanjeViewModel =
                it.sharedHiltViewModel<UklanjanjeViewModel>(navController)

            IzborIzvodjacaUklanjanjePesme(navController,viewModelUklanjanje)
        }
        composable(route = Destinacije.UklanjanjePesme.ruta) {
            val viewModelUklanjanje: UklanjanjeViewModel =
                it.sharedHiltViewModel<UklanjanjeViewModel>(navController)

            UklanjanjePesme(viewModelUklanjanje)
        }
        composable(
            route = "${Destinacije.UklanjanjeIzvodjaca.ruta}/{zanr}",
            arguments = listOf(navArgument("zanr") { type = NavType.IntType }
            )
        ) { navBackStackEntry ->
            val zanr = navBackStackEntry.arguments?.getInt("zanr") ?: 0
            UklanjanjeIzvodjaca(zanr)
        }
        composable(route = Destinacije.UklanjanjeKorisnika.ruta){
            UklanjanjeKorisnika()
        }
        composable(route = Destinacije.UklanjanjeZanra.ruta) {
            UklanjanjeZanra()
        }
    }
}