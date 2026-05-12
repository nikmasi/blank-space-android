package com.example.blankspace.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.blankspace.navigation.utils.sharedHiltViewModel
import com.example.blankspace.ui.screens.Destinacije
import com.example.blankspace.ui.screens.autorizacija.login.Login
import com.example.blankspace.ui.screens.autorizacija.registracija.Registracija
import com.example.blankspace.ui.screens.autorizacija.zaboravljena_lozinka.PromenaLozinke
import com.example.blankspace.ui.screens.autorizacija.zaboravljena_lozinka.ZaboravljenaLozinka
import com.example.blankspace.ui.screens.autorizacija.zaboravljena_lozinka.ZaboravljenaLozinkaPitanje
import com.example.blankspace.ui.screens.predlaganje.PredlaganjeIzvodjaca
import com.example.blankspace.ui.screens.predlaganje.PredlaganjePesme
import com.example.blankspace.ui.screens.predlaganje.PretragaPredlaganje
import com.example.blankspace.ui.screens.profil_rang_pravila.moj_profil.MojProfil
import com.example.blankspace.ui.screens.takmicenje.KorisnikPregled
import com.example.blankspace.viewModels.KorisniciViewModel
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.ZaboravljenaLozinkaViewModel

fun NavGraphBuilder.authGraph(
    navController: NavController,
    viewModelLogin: LoginViewModel,
    setUserType: () -> Unit
){
    navigation(startDestination = Destinacije.Login.ruta,route ="auth_graph"){
        composable(route = Destinacije.Login.ruta) {
            setUserType()
            Login(
                viewModelLogin,
                onSignUpClick = { navController.navigate(Destinacije.Registracija.ruta) },
                onGuestClick = { navController.navigate(Destinacije.Pocetna.ruta) },
                onForgotClick = { navController.navigate(Destinacije.ZaboravljenaLozinka.ruta) },
                onNavigate = { ruta ->
                    navController.navigate(ruta) {
                        popUpTo(Destinacije.Login.ruta) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Destinacije.Registracija.ruta) {
            Registracija(
                onBackToLogin ={ navController.navigate(Destinacije.Login.ruta) },
                onClickPocetna = { navController.navigate(Destinacije.PocetnaBrucos.ruta) }
            )
        }
        composable(route = Destinacije.ZaboravljenaLozinka.ruta) {
            val viewModelZaboravljenaLozinka: ZaboravljenaLozinkaViewModel =
                it.sharedHiltViewModel<ZaboravljenaLozinkaViewModel>(navController)

            ZaboravljenaLozinka(viewModel=viewModelZaboravljenaLozinka,
                onNavigateToQuestion = {
                    navController.navigate(Destinacije.ZaboravljenaLozinkaPitanje.ruta)
                },
                onResetClick ={ username ->
                    viewModelZaboravljenaLozinka.fetchZaboravljenaLozinka(username)
                }
            )
        }
        composable(route = Destinacije.ZaboravljenaLozinkaPitanje.ruta) {
            val viewModelZaboravljenaLozinka: ZaboravljenaLozinkaViewModel =
                it.sharedHiltViewModel<ZaboravljenaLozinkaViewModel>(navController)

            ZaboravljenaLozinkaPitanje(viewModelZaboravljenaLozinka, {
                navController.navigate(Destinacije.PromenaLozinke.ruta)
            })
        }
        composable(route = Destinacije.PromenaLozinke.ruta) {
            val viewModelZaboravljenaLozinka: ZaboravljenaLozinkaViewModel =
                it.sharedHiltViewModel<ZaboravljenaLozinkaViewModel>(navController)

            PromenaLozinke(viewModelZaboravljenaLozinka,viewModelLogin, onClick = {
                navController.navigate(Destinacije.Login.ruta) {
                    popUpTo(Destinacije.PromenaLozinke.ruta) { inclusive = true }
                }
            })
        }
        composable(route = Destinacije.KorisnikPregled.ruta){
            val viewModelKorisnici: KorisniciViewModel =
                it.sharedHiltViewModel<KorisniciViewModel>(navController)

            KorisnikPregled(viewModelKorisnici,viewModelLogin)
        }

        composable(route = Destinacije.PredlaganjeIzvodjaca.ruta) {
            PredlaganjeIzvodjaca(navController,viewModelLogin)
        }
        composable(route = Destinacije.PredlaganjePesme.ruta){
            PredlaganjePesme(navController,viewModelLogin)
        }
        composable(route = Destinacije.PretragaPredlaganje.ruta) {
            PretragaPredlaganje(navController,viewModelLogin)
        }
        composable(route = Destinacije.MojProfil.ruta) {
            MojProfil(navController, viewModelLogin)
        }
    }
}