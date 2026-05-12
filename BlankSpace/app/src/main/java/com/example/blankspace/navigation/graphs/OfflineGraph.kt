package com.example.blankspace.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.blankspace.navigation.utils.sharedHiltViewModel
import com.example.blankspace.ui.screens.Destinacije
import com.example.blankspace.ui.screens.igra_offline.Igra_offline
import com.example.blankspace.ui.screens.igra_offline.Kraj_igre_offline
import com.example.blankspace.ui.screens.igra_offline.Zanr_igra_offline
import com.example.blankspace.viewModels.DatabaseViewModel

fun NavGraphBuilder.offlineGraph(
    navController: NavController
) {
    navigation(startDestination = Destinacije.Nivo_igra_sam.ruta, route = "offline_graph") {
        composable(
            route = "${Destinacije.Zanr_igra_offline.ruta}/{selectedNivo}",
            arguments = listOf(navArgument("selectedNivo") { type = NavType.StringType }),
        ) { navBackStackEntry ->
            val selectedNivo = navBackStackEntry.arguments?.getString("selectedNivo") ?: ""
            val databaseViewModel: DatabaseViewModel =
                navBackStackEntry.sharedHiltViewModel<DatabaseViewModel>(navController)

            Zanr_igra_offline(navController,selectedNivo,databaseViewModel)
        }
        composable(
            route = "${Destinacije.Igra_offline.ruta}/{selectedZanrovi}/{selectedNivo}/{runda}/{poeni}",
            arguments = listOf(
                navArgument("selectedZanrovi") { type = NavType.StringType },
                navArgument("selectedNivo") { type = NavType.StringType },
                navArgument("runda") { type = NavType.IntType },
                navArgument("poeni") { type = NavType.IntType }// Pretpostavljamo da je `selectedNivo` String
            )
        ) { navBackStackEntry ->
            val selectedZanrovi = navBackStackEntry.arguments?.getString("selectedZanrovi") ?: ""
            val selectedNivo = navBackStackEntry.arguments?.getString("selectedNivo") ?: ""
            val runda=navBackStackEntry.arguments?.getInt("runda")?:0
            val poeni=navBackStackEntry.arguments?.getInt("poeni")?:0

            val databaseViewModel: DatabaseViewModel =
                navBackStackEntry.sharedHiltViewModel<DatabaseViewModel>(navController)

            Igra_offline(navController, selectedZanrovi, selectedNivo,runda,poeni,databaseViewModel)
        }
        composable(
            route = "${Destinacije.Kraj_igre_offline.ruta}/{poeni}",
            arguments = listOf(navArgument("poeni") { type = NavType.IntType }
            )
        ) { navBackStackEntry ->
            val poeni = navBackStackEntry.arguments?.getInt("poeni") ?: 0
            Kraj_igre_offline(poeni,
                onClickPonovo = { navController.navigate(Destinacije.Nivo_igra_offline.ruta) },
                onClickKraj = { navController.navigate(Destinacije.PocetnaOffline.ruta) }
            )
        }
    }
}
