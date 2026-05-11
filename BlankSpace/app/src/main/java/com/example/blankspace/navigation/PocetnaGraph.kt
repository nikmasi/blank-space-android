package com.example.blankspace.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.pocetne.Pocetna
import com.example.blankspace.screens.pocetne.PocetnaAdmin
import com.example.blankspace.screens.pocetne.PocetnaOffline
import com.example.blankspace.screens.pocetne.PocetnaRegistrovan
import com.example.blankspace.viewModels.LoginViewModel

fun NavGraphBuilder.pocetnaGraph(
    navController: NavController,
    viewModelLogin: LoginViewModel,
    setUserType: (String) -> Unit
) {
    navigation(startDestination = Destinacije.Pocetna.ruta, route = "pocetna_graph") {
        composable(route = Destinacije.Pocetna.ruta) {
            Pocetna(modifier = Modifier,
                onGameSoloClick = { navController.navigate(Destinacije.Nivo_igra_sam.ruta) },
                onGameDuelClick = { navController.navigate(Destinacije.Sifra_sobe_duel.ruta) }
            )
        }
        composable(route = Destinacije.PocetnaOffline.ruta) {
            PocetnaOffline(
                onNavigateToOffline = { navController.navigate(Destinacije.Nivo_igra_offline.ruta) }
            )
        }

        composable(route = Destinacije.PocetnaBrucos.ruta) {
            setUserType("brucos")
            PocetnaRegistrovan(
                modifier = Modifier, viewModelLogin = viewModelLogin,
                onGameSoloClick = { navController.navigate(Destinacije.Nivo_igra_sam.ruta) },
                onGameDuelClick = { navController.navigate(Destinacije.Sifra_sobe_duel.ruta) },
                onLogOut = {navController.navigate(Destinacije.Login.ruta)},
                onCheckLoginStates = {
                    navController.navigate(Destinacije.Login.ruta) {
                        popUpTo(Destinacije.Pocetna.ruta) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Destinacije.PocetnaStudent.ruta) {
            setUserType("student")
            PocetnaRegistrovan(modifier = Modifier,viewModelLogin= viewModelLogin,
                onGameSoloClick = { navController.navigate(Destinacije.Nivo_igra_sam.ruta) },
                onGameDuelClick = { navController.navigate(Destinacije.Sifra_sobe_duel.ruta) },
                onSuggestArtistClick = { navController.navigate(Destinacije.PredlaganjeIzvodjaca.ruta) },
                onGameSing = {navController.navigate(Destinacije.Nivo_pogodiPevaj.ruta)},
                onLogOut = {navController.navigate(Destinacije.Login.ruta)},
                onCheckLoginStates = {
                    navController.navigate(Destinacije.Login.ruta) {
                        popUpTo(Destinacije.Pocetna.ruta) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Destinacije.PocetnaMaster.ruta) {
            setUserType("master")
            PocetnaRegistrovan(modifier = Modifier,viewModelLogin,
                onGameSoloClick = { navController.navigate(Destinacije.Nivo_igra_sam.ruta) },
                onGameDuelClick = { navController.navigate(Destinacije.Sifra_sobe_duel.ruta) },
                onSuggestArtistClick = { navController.navigate(Destinacije.PredlaganjeIzvodjaca.ruta) },
                onSuggestSongClick = { navController.navigate(Destinacije.PredlaganjePesme.ruta) },
                onSearchAndSuggestClick = { navController.navigate(Destinacije.PretragaPredlaganje.ruta) },
                onGameSing = {navController.navigate(Destinacije.Nivo_pogodiPevaj.ruta)},
                onGameChallenge = {navController.navigate(Destinacije.Nivo_challenge.ruta)},
                onLogOut = {navController.navigate(Destinacije.Login.ruta)},
                onCheckLoginStates = {
                    navController.navigate(Destinacije.Login.ruta) {
                        popUpTo(Destinacije.Pocetna.ruta) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Destinacije.PocetnaAdmin.ruta) {
            setUserType("admin")
            PocetnaAdmin(modifier = Modifier,viewModelLogin,
                onLogout = {
                    navController.navigate(Destinacije.Login.ruta) { popUpTo(0) }
                    viewModelLogin.izloguj_se()
                },
                onNavigate = { ruta -> navController.navigate(ruta) }
            )
        }
    }
}