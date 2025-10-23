package com.example.blankspace

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.autorizacija.Login
import com.example.blankspace.screens.autorizacija.PromenaLozinke
import com.example.blankspace.screens.autorizacija.Registracija
import com.example.blankspace.screens.autorizacija.ZaboravljenaLozinka
import com.example.blankspace.screens.autorizacija.ZaboravljenaLozinkaPitanje
import com.example.blankspace.screens.dodavanje.IzborZanra2
import com.example.blankspace.screens.dodavanje.PesmaPodaci
import com.example.blankspace.screens.dodavanje.PesmaPodaci2
import com.example.blankspace.screens.dodavanje.PesmaPodaciD
import com.example.blankspace.screens.igra_offline.Igra_offline
import com.example.blankspace.screens.igra_offline.Kraj_igre_offline
import com.example.blankspace.screens.igra_offline.Nivo_igra_offline
import com.example.blankspace.screens.igra_offline.Zanr_igra_offline
import com.example.blankspace.screens.igra_pogodi_i_pevaj.Igra_pogodiPevaj
import com.example.blankspace.screens.igra_pogodi_i_pevaj.Kraj_pogodiPevaj
import com.example.blankspace.screens.igra_pogodi_i_pevaj.Nivo_pogodiPevaj
import com.example.blankspace.screens.igra_pogodi_i_pevaj.Zanr_PogodiPevaj
import com.example.blankspace.screens.igra_sam.Duel
import com.example.blankspace.screens.igra_sam.Igra_sam
import com.example.blankspace.screens.igra_sam.Kraj_igre_igre_sam
import com.example.blankspace.screens.igra_sam.Nivo_igra_sam
import com.example.blankspace.screens.igra_sam.Zanr_igra_sam
import com.example.blankspace.screens.igraj_u_duelu.Cekanje_rezultata
import com.example.blankspace.screens.igraj_u_duelu.Generisi_sifru_sobe
import com.example.blankspace.screens.igraj_u_duelu.Kraj_duela
import com.example.blankspace.screens.igraj_u_duelu.Sifra_sobe_duel
import com.example.blankspace.screens.pocetne.Pocetna
import com.example.blankspace.screens.pocetne.PocetnaAdmin
import com.example.blankspace.screens.pocetne.PocetnaBrucos
import com.example.blankspace.screens.pocetne.PocetnaMaster
import com.example.blankspace.screens.pocetne.PocetnaOffline
import com.example.blankspace.screens.pocetne.PocetnaStudent
import com.example.blankspace.screens.pocetne.RangLista
import com.example.blankspace.screens.pocetne.UcitavanjeEkrana
import com.example.blankspace.screens.predlaganje.ImeIzvodjaca
import com.example.blankspace.screens.predlaganje.IzborIzvodjaca
import com.example.blankspace.screens.predlaganje.IzborZanra
import com.example.blankspace.screens.predlaganje.NazivZanra
import com.example.blankspace.screens.predlaganje.PredlaganjeIzvodjaca
import com.example.blankspace.screens.predlaganje.PredlaganjePesme
import com.example.blankspace.screens.predlaganje.PretragaPredlaganje
import com.example.blankspace.screens.profil_rang_pravila.MojProfil
import com.example.blankspace.screens.profil_rang_pravila.PravilaIgre
import com.example.blankspace.screens.uklanjanje.IzborIzvodjacaUklanjanjePesme
import com.example.blankspace.screens.uklanjanje.IzborZanraUklanjanjeIzvodjaca
import com.example.blankspace.screens.uklanjanje.IzborZanraUklanjanjePesme
import com.example.blankspace.screens.uklanjanje.PredloziIzvodjaca
import com.example.blankspace.screens.uklanjanje.PredloziPesama
import com.example.blankspace.screens.uklanjanje.UklanjanjeIzvodjaca
import com.example.blankspace.screens.uklanjanje.UklanjanjeKorisnika
import com.example.blankspace.screens.uklanjanje.UklanjanjePesme
import com.example.blankspace.screens.uklanjanje.UklanjanjeZanra
import com.example.blankspace.ui.bars.BlankSpaceBottomBar
import com.example.blankspace.ui.bars.BlankSpaceTopAppBar
import com.example.blankspace.ui.theme.BlankSpaceTheme
import com.example.blankspace.viewModels.DatabaseViewModel
import com.example.blankspace.viewModels.DodavanjeViewModel
import com.example.blankspace.viewModels.DuelViewModel
import com.example.blankspace.viewModels.IgraSamViewModel
import com.example.blankspace.viewModels.LoginViewModel
import com.example.blankspace.viewModels.PredloziViewModel
import com.example.blankspace.viewModels.UklanjanjeViewModel
import com.example.blankspace.viewModels.ZaboravljenaLozinkaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlankSpaceTheme {
                BlankSpaceApp()
            }
        }
    }
}
@Composable
fun rememberCurrentRoute(navController: NavController): String {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    return currentDestination?.route ?: Destinacije.Pocetna.ruta
}


fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

fun hasDownloadedData(context: Context): Boolean {
    val prefs = context.getSharedPreferences("offline_mode", Context.MODE_PRIVATE)
    return prefs.getBoolean("data_downloaded", false)
}

fun setDownloadedFlag(context: Context) {
    val prefs = context.getSharedPreferences("offline_mode", Context.MODE_PRIVATE)
    prefs.edit().putBoolean("data_downloaded", true).apply()
}

@Composable
fun BlankSpaceApp(){
    val viewModelIgraSam: IgraSamViewModel = hiltViewModel()
    val viewModelDodavanje: DodavanjeViewModel= hiltViewModel()
    val viewModelDuel : DuelViewModel = hiltViewModel()
    val viewModelUklanjanje: UklanjanjeViewModel = hiltViewModel()
    val viewModelZaboravljenaLozinka: ZaboravljenaLozinkaViewModel = hiltViewModel()
    val viewModelLogin: LoginViewModel = hiltViewModel()
    val viewModelPredlozi: PredloziViewModel = hiltViewModel()
    val uiStateLogin by viewModelLogin.uiState.collectAsState()

    val databaseViewModel: DatabaseViewModel = viewModel()
    val navController = rememberNavController()
    val currentRoute = rememberCurrentRoute(navController)

    var userType by remember{ mutableStateOf("") }

    Scaffold(
        topBar = {
            if(currentRoute!=Destinacije.UcitavanjeEkrana.ruta)
                BlankSpaceTopAppBar(navController,currentRoute,viewModelLogin) },
        bottomBar = { BlankSpaceBottomBar(navController,currentRoute,userType) }
    ) { innerPadding ->
        val padding = innerPadding
        NavHost(
            navController = navController,
            startDestination = Destinacije.UcitavanjeEkrana.ruta
        ) {
            composable(route = Destinacije.UcitavanjeEkrana.ruta){
                UcitavanjeEkrana(Modifier,navController,viewModelLogin, databaseViewModel)
            }
            composable(route = Destinacije.Pocetna.ruta) {
                userType=""
                Pocetna(modifier = Modifier.padding(padding),navController,viewModelLogin)
            }
            composable(route = Destinacije.PocetnaOffline.ruta) {
                userType=""
                PocetnaOffline(modifier = Modifier.padding(padding),navController,viewModelLogin)
            }
            composable(route = Destinacije.PocetnaBrucos.ruta) {
                userType="brucos"
                PocetnaBrucos(modifier = Modifier.padding(padding),navController,viewModelLogin)
            }
            composable(route = Destinacije.PocetnaStudent.ruta) {
                userType="student"
                PocetnaStudent(modifier = Modifier.padding(padding),navController,viewModelLogin)
            }
            composable(route = Destinacije.PocetnaMaster.ruta) {
                userType="master"
                PocetnaMaster(modifier = Modifier.padding(padding),navController,viewModelLogin)
            }
            composable(route = Destinacije.PocetnaAdmin.ruta) {
                userType="admin"
                PocetnaAdmin(modifier = Modifier.padding(padding),navController,viewModelLogin)
            }
            composable(route = Destinacije.PravilaIgre.ruta) {
                PravilaIgre(navController)
            }
            composable(route = Destinacije.Nivo_igra_sam.ruta) {
                Nivo_igra_sam(navController)
            }
            composable(route = Destinacije.Nivo_igra_offline.ruta) {
                Nivo_igra_offline(navController)
            }
            composable(route = Destinacije.Nivo_pogodiPevaj.ruta) {
                Nivo_pogodiPevaj(navController)
            }
            composable(route = Destinacije.Sifra_sobe_duel.ruta) {
                Sifra_sobe_duel(navController,viewModelDuel,viewModelLogin)
            }
            composable(
                route = "${Destinacije.Zanr_igra_sam.ruta}/{selectedNivo}",
                arguments = listOf(navArgument("selectedNivo") { type = NavType.StringType }),
                ) { navBackStackEntry ->
                val selectedNivo = navBackStackEntry.arguments?.getString("selectedNivo") ?: ""
                Zanr_igra_sam(navController,selectedNivo,viewModelIgraSam)
            }
            composable(
                route = "${Destinacije.Zanr_igra_offline.ruta}/{selectedNivo}",
                arguments = listOf(navArgument("selectedNivo") { type = NavType.StringType }),
            ) { navBackStackEntry ->
                val selectedNivo = navBackStackEntry.arguments?.getString("selectedNivo") ?: ""
                Zanr_igra_offline(navController,selectedNivo,databaseViewModel)
            }
            composable(
                route = "${Destinacije.Zanr_pogodiPevaj.ruta}/{selectedNivo}",
                arguments = listOf(navArgument("selectedNivo") { type = NavType.StringType }),
            ) { navBackStackEntry ->
                val selectedNivo = navBackStackEntry.arguments?.getString("selectedNivo") ?: ""
                Zanr_PogodiPevaj(navController,selectedNivo,viewModelIgraSam)
            }
            composable(
                route = "${Destinacije.Igra_sam.ruta}/{selectedZanrovi}/{selectedNivo}/{runda}/{poeni}",
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

                Igra_sam(navController, selectedZanrovi, selectedNivo,runda,poeni,viewModelIgraSam)
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

                Igra_offline(navController, selectedZanrovi, selectedNivo,runda,poeni,databaseViewModel)
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

                Igra_pogodiPevaj(navController, selectedZanrovi, selectedNivo,runda,poeni,viewModelIgraSam)
            }
            composable(
                route = "${Destinacije.Kraj_igre_igre_sam.ruta}/{poeni}",
                arguments = listOf(navArgument("poeni") { type = NavType.IntType }  // Pretpostavljamo da je `selectedNivo` String
                )
            ) { navBackStackEntry ->
                    val poeni = navBackStackEntry.arguments?.getInt("poeni") ?: 0
                    Kraj_igre_igre_sam(navController,poeni,viewModelLogin,viewModelIgraSam)
            }
            composable(
                route = "${Destinacije.Kraj_igre_offline.ruta}/{poeni}",
                arguments = listOf(navArgument("poeni") { type = NavType.IntType }  // Pretpostavljamo da je `selectedNivo` String
                )
            ) { navBackStackEntry ->
                val poeni = navBackStackEntry.arguments?.getInt("poeni") ?: 0
                Kraj_igre_offline(navController,poeni,databaseViewModel)
            }
            composable(
                route = "${Destinacije.Kraj_pogodiPevaj.ruta}/{poeni}",
                arguments = listOf(navArgument("poeni") { type = NavType.IntType }  // Pretpostavljamo da je `selectedNivo` String
                )
            ) { navBackStackEntry ->
                val poeni = navBackStackEntry.arguments?.getInt("poeni") ?: 0
                Kraj_pogodiPevaj(navController, poeni, viewModelLogin, viewModelIgraSam)
            }
            composable(route = Destinacije.Login.ruta) {
                userType=""
                Login(navController,viewModelLogin)
            }
            composable(route = Destinacije.Registracija.ruta) {
                Registracija(navController,viewModelLogin)
            }
            composable(route = Destinacije.ZaboravljenaLozinka.ruta) {
                ZaboravljenaLozinka(navController,viewModelZaboravljenaLozinka)
            }
            composable(route = Destinacije.ZaboravljenaLozinkaPitanje.ruta) {
                ZaboravljenaLozinkaPitanje(navController,viewModelZaboravljenaLozinka)
            }
            composable(route = Destinacije.PromenaLozinke.ruta) {
                PromenaLozinke(navController,viewModelZaboravljenaLozinka,viewModelLogin)
            }
            composable(route = Destinacije.MojProfil.ruta) {
                MojProfil(navController, viewModelLogin)
            }
            composable(route = Destinacije.RangLista.ruta) {
                RangLista(navController)
            }
            composable(route = Destinacije.PredlaganjeIzvodjaca.ruta) {
                PredlaganjeIzvodjaca(navController,viewModelLogin)
            }
            composable(route = Destinacije.PredlaganjePesme.ruta) {
                PredlaganjePesme(navController,viewModelLogin)
            }
            composable(route = Destinacije.PretragaPredlaganje.ruta) {
                PretragaPredlaganje(navController,viewModelLogin)
            }
            composable(route = Destinacije.UklanjanjeZanra.ruta) {
                UklanjanjeZanra(navController)
            }
            composable(route = Destinacije.IzborZanraUklanjanjeIzvodjaca.ruta){
                IzborZanraUklanjanjeIzvodjaca(navController)
            }
            composable(route = Destinacije.IzborZanraUklanjanjePesme.ruta){
                IzborZanraUklanjanjePesme(navController,viewModelUklanjanje)
            }
            composable(route = Destinacije.IzborIzvodjacaUklanjanjePesme.ruta) {
                IzborIzvodjacaUklanjanjePesme(navController,viewModelUklanjanje)
            }
            composable(route = Destinacije.UklanjanjePesme.ruta) {
                UklanjanjePesme(navController,viewModelUklanjanje)
            }
            composable(
                route = "${Destinacije.UklanjanjeIzvodjaca.ruta}/{zanr}",
                arguments = listOf(navArgument("zanr") { type = NavType.IntType }
                )
            ) { navBackStackEntry ->
                val zanr = navBackStackEntry.arguments?.getInt("zanr") ?: 0
                UklanjanjeIzvodjaca(navController,zanr)
            }
            composable(route = Destinacije.UklanjanjeKorisnika.ruta){
                UklanjanjeKorisnika(navController)
            }
            composable(route = Destinacije.PredloziIzvodjaca.ruta){
                PredloziIzvodjaca(navController,viewModelPredlozi)
            }
            composable(route = Destinacije.PredloziPesme.ruta){
                PredloziPesama(navController,viewModelPredlozi)
            }
            composable(route = Destinacije.PesmaPodaci.ruta){
                PesmaPodaci(navController,viewModelPredlozi)
            }
            composable(route = Destinacije.PesmaPodaci2.ruta){
                PesmaPodaci2(navController,viewModelPredlozi)
            }
            composable(route = Destinacije.NazivZanra.ruta){
                NazivZanra(navController, viewModelDodavanje)
            }
            composable(
                route = "${Destinacije.ImeIzvodjaca.ruta}/{zanr}",
                arguments = listOf(
                    navArgument("zanr") { type = NavType.StringType }
                )
                ) { navBackStackEntry ->
                val zanr=navBackStackEntry.arguments?.getString("zanr")?:""

                ImeIzvodjaca(navController,viewModelDodavanje,zanr)
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

                PesmaPodaciD(navController, viewModelDodavanje,zanr,izvodjac)
            }
            composable(route = Destinacije.IzborZanra.ruta){
                IzborZanra(navController, viewModelDodavanje)
            }
            composable(route = Destinacije.IzborZanra2.ruta){
                IzborZanra2(navController, viewModelDodavanje)
            }
            composable(route = "${Destinacije.IzborIzvodjaca.ruta}/{zanr}",
                arguments = listOf(
                    navArgument("zanr") { type = NavType.StringType }
                )
                ) { navBackStackEntry ->
                val zanr=navBackStackEntry.arguments?.getString("zanr")?:""

                IzborIzvodjaca(navController, viewModelDodavanje,zanr)
            }
            composable(route = Destinacije.Generisi_sifru_sobe.ruta){
                Generisi_sifru_sobe(navController,viewModelDuel)
            }
            composable(
                route = "${Destinacije.Duel.ruta}/{runda}/{poeni}",
                arguments = listOf(
                    navArgument("runda") { type = NavType.IntType },
                    navArgument("poeni") { type = NavType.IntType }
                )
            ) { navBackStackEntry ->
                val runda=navBackStackEntry.arguments?.getInt("runda")?:0
                val poeni=navBackStackEntry.arguments?.getInt("poeni")?:0

                Duel(navController,runda,poeni,viewModelDuel)
            }
            composable(route = "${Destinacije.Cekanje_rezultata.ruta}/{poeni}",
                    arguments = listOf(

                navArgument("poeni") { type = NavType.IntType }
            )){
                navBackStackEntry ->
                val poeni=navBackStackEntry.arguments?.getInt("poeni")?:0
                Cekanje_rezultata(navController,viewModelDuel,poeni)
            }
            composable(route = Destinacije.Kraj_duela.ruta){
                Kraj_duela(navController,viewModelDuel)
            }
        }
    }
}