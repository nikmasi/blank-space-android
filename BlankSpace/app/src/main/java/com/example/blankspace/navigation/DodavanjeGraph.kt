package com.example.blankspace.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.dodavanje.IzborZanra2
import com.example.blankspace.screens.dodavanje.PesmaPodaciD
import com.example.blankspace.screens.predlaganje.ImeIzvodjaca
import com.example.blankspace.screens.predlaganje.IzborIzvodjaca
import com.example.blankspace.screens.predlaganje.IzborZanra
import com.example.blankspace.screens.predlaganje.NazivZanra
import com.example.blankspace.viewModels.DodavanjeViewModel

fun NavGraphBuilder.dodavanjeGraph(
    navController: NavController
) {

    navigation(startDestination = Destinacije.NazivZanra.ruta, route = "dodavanje_graph") {
        composable(route = Destinacije.NazivZanra.ruta){
            val viewModelDodavanje: DodavanjeViewModel =
                it.sharedHiltViewModel<DodavanjeViewModel>(navController)

            NazivZanra(navController, viewModelDodavanje)
        }
        composable(
            route = "${Destinacije.ImeIzvodjaca.ruta}/{zanr}",
            arguments = listOf(
                navArgument("zanr") { type = NavType.StringType }
            )

        ) { navBackStackEntry ->
            val zanr=navBackStackEntry.arguments?.getString("zanr")?:""

            val viewModelDodavanje: DodavanjeViewModel =
                navBackStackEntry.sharedHiltViewModel<DodavanjeViewModel>(navController)

            ImeIzvodjaca(viewModelDodavanje,
                onProvera  = { izvodjac ->
                    navController.navigate("${Destinacije.PesmaPodaciD.ruta}/$zanr/$izvodjac")
                })
        }
        composable(
            route = "${Destinacije.PesmaPodaciD.ruta}/{zanr}/{izvodjac}",
            arguments = listOf(
                navArgument("zanr") { type = NavType.StringType },
                navArgument("izvodjac") { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val zanr=navBackStackEntry.arguments?.getString("zanr")?:""
            val izvodjac=navBackStackEntry.arguments?.getString("izvodjac")?:""

            val viewModelDodavanje: DodavanjeViewModel =
                navBackStackEntry.sharedHiltViewModel<DodavanjeViewModel>(navController)

            PesmaPodaciD(navController, viewModelDodavanje,zanr,izvodjac)
        }
        composable(route = Destinacije.IzborZanra.ruta){
            val viewModelDodavanje: DodavanjeViewModel =
                it.sharedHiltViewModel<DodavanjeViewModel>(navController)

            IzborZanra(navController, viewModelDodavanje)
        }
        composable(route = Destinacije.IzborZanra2.ruta){
            val viewModelDodavanje: DodavanjeViewModel =
                it.sharedHiltViewModel<DodavanjeViewModel>(navController)

            IzborZanra2(navController, viewModelDodavanje)
        }
        composable(route = "${Destinacije.IzborIzvodjaca.ruta}/{zanr}",
            arguments = listOf(
                navArgument("zanr") { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val zanr=navBackStackEntry.arguments?.getString("zanr")?:""

            val viewModelDodavanje: DodavanjeViewModel =
                navBackStackEntry.sharedHiltViewModel<DodavanjeViewModel>(navController)

            IzborIzvodjaca(navController, viewModelDodavanje,zanr)
        }
    }
}