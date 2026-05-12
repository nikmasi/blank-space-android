package com.example.blankspace.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.blankspace.navigation.utils.sharedHiltViewModel
import com.example.blankspace.ui.screens.Destinacije
import com.example.blankspace.ui.screens.sadrzaj.SadrzajIzvodjaci
import com.example.blankspace.ui.screens.sadrzaj.SadrzajKorisnici
import com.example.blankspace.ui.screens.sadrzaj.SadrzajPesme
import com.example.blankspace.ui.screens.sadrzaj.SadrzajSoba
import com.example.blankspace.ui.screens.sadrzaj.SadrzajStihovi
import com.example.blankspace.ui.screens.sadrzaj.SadrzajZanrova
import com.example.blankspace.ui.screens.statistika.AdminStatistika
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