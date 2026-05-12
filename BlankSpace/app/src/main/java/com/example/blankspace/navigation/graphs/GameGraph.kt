package com.example.blankspace.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.blankspace.navigation.utils.sharedHiltViewModel
import com.example.blankspace.ui.screens.Destinacije
import com.example.blankspace.ui.screens.igra_challenge.Igra_challenge
import com.example.blankspace.ui.screens.igra_offline.Kraj_igre_offline
import com.example.blankspace.ui.screens.igra_pogodi_i_pevaj.Igra_pogodiPevaj
import com.example.blankspace.ui.screens.igra_sam.Igra_sam
import com.example.blankspace.ui.screens.igra_sam.Kraj_igre
import com.example.blankspace.ui.screens.igra_sam.Nivo_igra
import com.example.blankspace.ui.screens.igra_sam.Zanr_igra
import com.example.blankspace.viewModels.IgraSamViewModel
import com.example.blankspace.viewModels.LoginViewModel

fun NavGraphBuilder.gameGraph(
    navController: NavController,
    viewModelLogin: LoginViewModel
) {
    navigation(startDestination = Destinacije.Nivo_igra_sam.ruta, route = "game_graph") {

        //nivo
        composable(route = Destinacije.Nivo_igra_sam.ruta) {
            Nivo_igra(
                onNavigateToGenre = { tezina ->
                    navController.navigate("${Destinacije.Zanr_igra_sam.ruta}/$tezina")
                },
                text = "Igraj sam"
            )
        }
        composable(route = Destinacije.Nivo_igra_offline.ruta) {
            Nivo_igra(onNavigateToGenre = { nivo ->
                navController.navigate("${Destinacije.Zanr_igra_offline.ruta}/$nivo")
            }, text ="Offline igra")
        }
        composable(route = Destinacije.Nivo_pogodiPevaj.ruta) {
            Nivo_igra(
                onNavigateToGenre = { tezina ->
                    navController.navigate("${Destinacije.Zanr_pogodiPevaj.ruta}/$tezina")
                },
                text ="Pogodi i Pevaj"
            )
        }
        composable(route = Destinacije.Nivo_challenge.ruta) {
            Nivo_igra(onNavigateToGenre = { tezina ->
                navController.navigate("${Destinacije.Zanr_challenge.ruta}/$tezina")
            }, text ="Challenge mode")
        }

        composable(
            route = "${Destinacije.Zanr_igra_sam.ruta}/{selectedNivo}",
            arguments = listOf(navArgument("selectedNivo") { type = NavType.StringType }),
        ) { navBackStackEntry ->
            val selectedNivo = navBackStackEntry.arguments?.getString("selectedNivo") ?: ""
            val viewModelIgraSam: IgraSamViewModel =
                navBackStackEntry.sharedHiltViewModel<IgraSamViewModel>(navController)

            Zanr_igra(
                selectedNivo = selectedNivo,
                onNavigateNext = { zanroviIds, nivo ->
                    viewModelIgraSam.postaviListu(emptyList())
                    navController.navigate(Destinacije.Igra_sam.ruta + "/$zanroviIds/$nivo/0/0")
                }
            )
        }

        composable(
            route = "${Destinacije.Zanr_pogodiPevaj.ruta}/{selectedNivo}",
            arguments = listOf(navArgument("selectedNivo") { type = NavType.StringType }),
        ) { navBackStackEntry ->
            val selectedNivo = navBackStackEntry.arguments?.getString("selectedNivo") ?: ""
            val viewModelIgraSam: IgraSamViewModel =
                navBackStackEntry.sharedHiltViewModel<IgraSamViewModel>(navController)

            Zanr_igra(selectedNivo = selectedNivo,
                onNavigateNext = { zanroviIds, selectedNivoValue ->
                    viewModelIgraSam.postaviListu(emptyList())
                    navController.navigate(Destinacije.Igra_pogodiPevaj.ruta + "/$zanroviIds/$selectedNivoValue/0/0")
                }
            )
        }
        composable(
            route = "${Destinacije.Zanr_challenge.ruta}/{selectedNivo}",
            arguments = listOf(navArgument("selectedNivo") { type = NavType.StringType }),
        ) { navBackStackEntry ->
            val selectedNivo = navBackStackEntry.arguments?.getString("selectedNivo") ?: ""
            val viewModelIgraSam: IgraSamViewModel =
                navBackStackEntry.sharedHiltViewModel<IgraSamViewModel>(navController)

            Zanr_igra(selectedNivo = selectedNivo,
                onNavigateNext = { zanroviIds, selectedNivoValue ->
                    viewModelIgraSam.postaviListu(emptyList())
                    navController.navigate(Destinacije.Igra_challenge.ruta + "/$zanroviIds/$selectedNivoValue/80/0")
                }
            )
        }

        //igra

        composable(
            route = "${Destinacije.Igra_sam.ruta}/{selectedZanrovi}/{selectedNivo}/{runda}/{poeni}",
            arguments = listOf(
                navArgument("selectedZanrovi") { type = NavType.StringType },
                navArgument("selectedNivo") { type = NavType.StringType },
                navArgument("runda") { type = NavType.IntType },
                navArgument("poeni") { type = NavType.IntType }
            )
        ) { navBackStackEntry ->
            val selectedZanrovi = navBackStackEntry.arguments?.getString("selectedZanrovi") ?: ""
            val selectedNivo = navBackStackEntry.arguments?.getString("selectedNivo") ?: ""
            val runda=navBackStackEntry.arguments?.getInt("runda")?:0
            val poeni=navBackStackEntry.arguments?.getInt("poeni")?:0

            val viewModelIgraSam: IgraSamViewModel =
                navBackStackEntry.sharedHiltViewModel<IgraSamViewModel>(navController)

            Igra_sam(navController, selectedZanrovi, selectedNivo,runda,poeni,viewModelIgraSam)
        }

        composable(
            route = "${Destinacije.Igra_pogodiPevaj.ruta}/{selectedZanrovi}/{selectedNivo}/{runda}/{poeni}",
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

            val viewModelIgraSam: IgraSamViewModel =
                navBackStackEntry.sharedHiltViewModel<IgraSamViewModel>(navController)

            Igra_pogodiPevaj(navController, selectedZanrovi, selectedNivo,runda,poeni,viewModelIgraSam)
        }
        composable(
            route = "${Destinacije.Igra_challenge.ruta}/{selectedZanrovi}/{selectedNivo}/{runda}/{poeni}",
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

            val viewModelIgraSam: IgraSamViewModel =
                navBackStackEntry.sharedHiltViewModel<IgraSamViewModel>(navController)

            Igra_challenge(navController, selectedZanrovi, selectedNivo,runda,poeni,viewModelIgraSam)
        }

        // kraj

        composable(
            route = "${Destinacije.Kraj_igre_igre_sam.ruta}/{poeni}",
            arguments = listOf(navArgument("poeni") { type = NavType.IntType }
            )
        ) { navBackStackEntry ->
            val poeni = navBackStackEntry.arguments?.getInt("poeni") ?: 0

            val viewModelIgraSam: IgraSamViewModel =
                navBackStackEntry.sharedHiltViewModel<IgraSamViewModel>(navController)

            Kraj_igre(poeni,viewModelLogin,viewModelIgraSam,
                onClickPonovo = { navController.navigate(Destinacije.Nivo_igra_sam.ruta) },
                onClickKraj = { navController.navigate(Destinacije.Login.ruta) }
            )
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
        composable(
            route = "${Destinacije.Kraj_pogodiPevaj.ruta}/{poeni}",
            arguments = listOf(navArgument("poeni") { type = NavType.IntType }
            )
        ) { navBackStackEntry ->
            val poeni = navBackStackEntry.arguments?.getInt("poeni") ?: 0

            val viewModelIgraSam: IgraSamViewModel =
                navBackStackEntry.sharedHiltViewModel<IgraSamViewModel>(navController)

            Kraj_igre(poeni, viewModelLogin, viewModelIgraSam,
                onClickPonovo = { navController.navigate(Destinacije.Nivo_pogodiPevaj.ruta) },
                onClickKraj = { navController.navigate(Destinacije.Login.ruta) }
            )
        }
        composable(
            route = "${Destinacije.Kraj_challenge.ruta}/{poeni}",
            arguments = listOf(navArgument("poeni") { type = NavType.IntType }
            )
        ) { navBackStackEntry ->
            val poeni = navBackStackEntry.arguments?.getInt("poeni") ?: 0

            val viewModelIgraSam: IgraSamViewModel =
                navBackStackEntry.sharedHiltViewModel<IgraSamViewModel>(navController)

            Kraj_igre(poeni, viewModelLogin, viewModelIgraSam,
                onClickPonovo = { navController.navigate(Destinacije.Nivo_challenge.ruta) },
                onClickKraj = { navController.navigate(Destinacije.Login.ruta) }
            )
        }
    }
}