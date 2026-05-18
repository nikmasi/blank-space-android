package com.example.blankspace

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.blankspace.data.worker.WorkScheduler
import com.example.blankspace.navigation.graphs.adminGraph
import com.example.blankspace.navigation.graphs.authGraph
import com.example.blankspace.navigation.graphs.dodavanjeGraph
import com.example.blankspace.navigation.graphs.duelGraph
import com.example.blankspace.navigation.graphs.gameGraph
import com.example.blankspace.navigation.graphs.offlineGraph
import com.example.blankspace.navigation.graphs.pocetnaGraph
import com.example.blankspace.navigation.graphs.predloziGraph
import com.example.blankspace.navigation.graphs.profileGraph
import com.example.blankspace.navigation.utils.rememberCurrentRoute
import com.example.blankspace.navigation.graphs.uklanjanjeGraph
import com.example.blankspace.ui.screens.Destinacije
import com.example.blankspace.ui.screens.pocetne.UcitavanjeEkrana
import com.example.blankspace.ui.bars.BlankSpaceBottomBar
import com.example.blankspace.ui.theme.BlankSpaceTheme
import com.example.blankspace.viewModels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WorkScheduler.startPeriodicSync(this)
        enableEdgeToEdge()
        setContent {
            BlankSpaceTheme {
                BlankSpaceApp()
            }
        }
    }
}
@Composable
fun BlankSpaceApp(){
    // TODO: This ViewModel should ideally be scoped closer to the NavHost.
    //  For now, it remains here due to navigation graph dependencies.
    val viewModelLogin: LoginViewModel = hiltViewModel()

    val navController = rememberNavController()
    val currentRoute = rememberCurrentRoute(navController)

    val uiStateLogin by viewModelLogin.uiState.collectAsState()
    var userType by remember{ mutableStateOf("") }

    val view = LocalView.current
    val bottomBarColor = Color(0xFFF0DAE7)

    if (!view.isInEditMode) {
        val window = (view.context as Activity).window
        SideEffect { window.navigationBarColor = bottomBarColor.toArgb() }
    }


    Scaffold(bottomBar = { BlankSpaceBottomBar(navController,currentRoute,userType) }
    ) { innerPadding ->
        val padding = innerPadding

        NavHost(
            navController = navController,
            startDestination = Destinacije.UcitavanjeEkrana.ruta
        ) {
            authGraph(navController, viewModelLogin, {userType=""})
            adminGraph(navController)
            pocetnaGraph(navController, viewModelLogin, {name -> userType=name})
            dodavanjeGraph(navController)

            gameGraph(navController, viewModelLogin)
            duelGraph(navController,viewModelLogin)

            offlineGraph(navController)
            predloziGraph(navController)
            profileGraph(navController)
            uklanjanjeGraph(navController)

            composable(route = Destinacije.UcitavanjeEkrana.ruta){
                UcitavanjeEkrana(Modifier,navController, uiStateLogin = uiStateLogin)
            }
        }
    }
}