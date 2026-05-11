package com.example.blankspace.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.sadrzaj.SadrzajIzvodjaci
import com.example.blankspace.screens.sadrzaj.SadrzajKorisnici
import com.example.blankspace.screens.sadrzaj.SadrzajPesme
import com.example.blankspace.screens.sadrzaj.SadrzajSoba
import com.example.blankspace.screens.sadrzaj.SadrzajStihovi
import com.example.blankspace.screens.sadrzaj.SadrzajZanrova
import com.example.blankspace.screens.statistika.AdminStatistika
import com.example.blankspace.viewModels.AdminStatistikaViewModel

fun NavGraphBuilder.adminGraph(
    navController: NavController
) {
    navigation(startDestination = Destinacije.AdminStatistika.ruta, route = "admin_graph") {
        composable(route = Destinacije.AdminStatistika.ruta){
            val viewModelAdminStatistika: AdminStatistikaViewModel =
                it.sharedHiltViewModel<AdminStatistikaViewModel>(navController)

            AdminStatistika(viewModelAdminStatistika)
        }

        composable(route = Destinacije.SadrzajZanrova.ruta) {
            SadrzajZanrova()
        }
        composable(route = Destinacije.SadrzajKorisnici.ruta) {
            SadrzajKorisnici()
        }
        composable(route = Destinacije.SadrzajIzvodjaci.ruta) {
            SadrzajIzvodjaci()
        }
        composable(route = Destinacije.SadrzajPesme.ruta) {
            SadrzajPesme()
        }
        composable(route = Destinacije.SadrzajStihovi.ruta) {
            SadrzajStihovi()
        }
        composable(route = Destinacije.SadrzajSoba.ruta) {
            SadrzajSoba()
        }
    }
}