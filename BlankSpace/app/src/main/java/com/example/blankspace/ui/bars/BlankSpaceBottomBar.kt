package com.example.blankspace.ui.bars

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blankspace.screens.listaBrucos
import com.example.blankspace.screens.listaMaster
import com.example.blankspace.screens.listaStudent


@Composable
fun BlankSpaceBottomBar(navController: NavController, currentRoute: String, userType: String) {

    // Boje iz PocetnaMainCard i BgCard2 za usklađivanje
    val PrimaryDark = Color(0xFF49006B) // Tamna boja za naglašavanje (selected icon)
    val LightPink = Color(0xFFF0DAE7) // Svetla roze za pozadinu (container)

    when (userType) {
        "brucos", "student", "master" -> {
            val navigationList = when (userType) {
                "brucos" -> listaBrucos
                "student" -> listaStudent
                "master" -> listaMaster
                else -> emptyList()
            }

            // Koristimo svetlo roze boju sa malom transparentnošću
            NavigationBar(
                containerColor = LightPink.copy(alpha = 0.9f),
                tonalElevation = 0.dp // Uklanjamo senku da se bolje slaže sa gradijentom
            ) {
                navigationList.forEach { navDestination ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = navDestination.ikonica,
                                // Koristimo rutu kao opis za pristupačnost
                                contentDescription = navDestination.ruta
                            )
                        },
                        // Uklonjen label tekst
                        label = null,
                        selected = currentRoute == navDestination.ruta,
                        onClick = {
                            if (currentRoute != navDestination.ruta) {
                                navController.navigate(navDestination.ruta)
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            // Odabrana ikonica je tamna PrimaryDark boja
                            selectedIconColor = PrimaryDark,
                            // Neodabrana ikonica je tamno siva ili malo tamnija roze
                            unselectedIconColor = Color.Gray.copy(alpha = 0.8f),
                            // Pozadina dugmeta (ripple) pri kliku je svetlo roze
                            indicatorColor = LightPink
                        )
                    )
                }
            }
        }
    }
}
