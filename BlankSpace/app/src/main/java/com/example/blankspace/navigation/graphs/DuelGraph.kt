package com.example.blankspace.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.blankspace.navigation.utils.sharedHiltViewModel
import com.example.blankspace.screens.igra_sam.Duel
import com.example.blankspace.ui.screens.Destinacije
import com.example.blankspace.ui.screens.igraj_u_duelu.Cekanje_rezultata
import com.example.blankspace.ui.screens.igraj_u_duelu.Generisi_sifru_sobe
import com.example.blankspace.ui.screens.igraj_u_duelu.Kraj_duela
import com.example.blankspace.ui.screens.igraj_u_duelu.Sifra_sobe_duel
import com.example.blankspace.viewModels.DuelViewModel
import com.example.blankspace.viewModels.LoginViewModel

fun NavGraphBuilder.duelGraph(
    navController: NavController,
    viewModelLogin: LoginViewModel
) {
    navigation(startDestination = Destinacije.AdminStatistika.ruta, route = "duel_graph") {
        composable(route = Destinacije.Sifra_sobe_duel.ruta) {
            val viewModelDuel: DuelViewModel = it.sharedHiltViewModel<DuelViewModel>(navController)

            Sifra_sobe_duel(navController,viewModelDuel,viewModelLogin)
        }
        composable(route = Destinacije.Generisi_sifru_sobe.ruta){
            val viewModelDuel: DuelViewModel = it.sharedHiltViewModel<DuelViewModel>(navController)

            Generisi_sifru_sobe(viewModelDuel,
                onClickDuel = { sifra ->
                    navController.navigate(Destinacije.Duel.ruta + "/" + 0 + "/" + 0 + "/${sifra}")
                },
                onClickLogin = { navController.navigate(Destinacije.Login.ruta) }
            )
        }
        composable(
            route = "${Destinacije.Duel.ruta}/{runda}/{poeni}/{sifra}",
            arguments = listOf(
                navArgument("runda") { type = NavType.IntType },
                navArgument("poeni") { type = NavType.IntType },
                navArgument("sifra") { type = NavType.IntType }
            )
        ) { navBackStackEntry ->
            val runda=navBackStackEntry.arguments?.getInt("runda")?:0
            val poeni=navBackStackEntry.arguments?.getInt("poeni")?:0
            val sifra=navBackStackEntry.arguments?.getInt("sifra")?:0

            val viewModelDuel: DuelViewModel = navBackStackEntry.
                sharedHiltViewModel<DuelViewModel>(navController)

            Duel(navController,runda,poeni,viewModelDuel,sifra)
        }
        composable(route = "${Destinacije.Cekanje_rezultata.ruta}/{poeni}/{sifra}",
            arguments = listOf(

                navArgument("poeni") { type = NavType.IntType },
                navArgument("sifra") { type = NavType.IntType }
            )){
                navBackStackEntry ->
            val poeni=navBackStackEntry.arguments?.getInt("poeni")?:0
            val sifra=navBackStackEntry.arguments?.getInt("sifra")?:0

            val viewModelDuel: DuelViewModel = navBackStackEntry.
                sharedHiltViewModel<DuelViewModel>(navController)

            Cekanje_rezultata(viewModelDuel,poeni,sifra,
                onKrajDuela ={  sifra ->
                    navController.navigate("${Destinacije.Kraj_duela.ruta}/$sifra")
                }
            )
        }

        composable(
            route = "${Destinacije.Kraj_duela.ruta}/{sifra}",
            arguments = listOf(navArgument("sifra") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val sifra=navBackStackEntry.arguments?.getInt("sifra")?:0

            val viewModelDuel: DuelViewModel = navBackStackEntry.
                sharedHiltViewModel<DuelViewModel>(navController)

            Kraj_duela(navController,viewModelDuel,sifra)
        }
    }
}