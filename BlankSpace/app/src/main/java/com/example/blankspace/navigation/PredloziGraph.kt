package com.example.blankspace.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.dodavanje.PesmaPodaci
import com.example.blankspace.screens.dodavanje.PesmaPodaci2
import com.example.blankspace.screens.uklanjanje.PredloziIzvodjaca
import com.example.blankspace.screens.uklanjanje.PredloziPesama
import com.example.blankspace.viewModels.PredloziViewModel

fun NavGraphBuilder.predloziGraph(
    navController: NavController
) {
    navigation(startDestination = Destinacije.PredloziIzvodjaca.ruta, route = "predlozi_graph") {
        composable(route = Destinacije.PredloziIzvodjaca.ruta){
            val viewModelPredlozi: PredloziViewModel =
                it.sharedHiltViewModel<PredloziViewModel>(navController)

            PredloziIzvodjaca(navController,viewModelPredlozi)
        }
        composable(route = Destinacije.PredloziPesme.ruta){
            val viewModelPredlozi: PredloziViewModel =
                it.sharedHiltViewModel<PredloziViewModel>(navController)

            PredloziPesama(navController,viewModelPredlozi)
        }
        composable(route = "${Destinacije.PesmaPodaci.ruta}/{zanr}/{izvodjac}",
            arguments = listOf(
                navArgument("zanr") { type = NavType.StringType },
                navArgument("izvodjac") { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val zanr=navBackStackEntry.arguments?.getString("zanr")?:""
            val izvodjac=navBackStackEntry.arguments?.getString("izvodjac")?:""

            val viewModelPredlozi: PredloziViewModel =
                navBackStackEntry.sharedHiltViewModel<PredloziViewModel>(navController)

            PesmaPodaci(navController,viewModelPredlozi,zanr,izvodjac)
        }

        composable(route = "${Destinacije.PesmaPodaci2.ruta}/{zanr}/{izvodjac}/{pesma}",
            arguments = listOf(
                navArgument("zanr") { type = NavType.StringType },
                navArgument("izvodjac") { type = NavType.StringType },
                navArgument("pesma") { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val zanr=navBackStackEntry.arguments?.getString("zanr")?:""
            val izvodjac=navBackStackEntry.arguments?.getString("izvodjac")?:""
            val pesma=navBackStackEntry.arguments?.getString("pesma")?:""

            val viewModelPredlozi: PredloziViewModel =
                navBackStackEntry.sharedHiltViewModel<PredloziViewModel>(navController)

            PesmaPodaci2(navController,viewModelPredlozi,zanr,izvodjac,pesma)
        }
    }
}