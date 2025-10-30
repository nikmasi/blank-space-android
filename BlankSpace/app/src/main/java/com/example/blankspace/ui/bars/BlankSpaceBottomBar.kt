package com.example.blankspace.ui.bars

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blankspace.screens.listaBrucos
import com.example.blankspace.screens.listaMaster
import com.example.blankspace.screens.listaStudent


@Composable
fun BlankSpaceBottomBar(navController: NavController, currentRoute: String, userType: String) {
    val PrimaryDark = Color(0xFF49006B)
    val LightPink = Color(0xFFF0DAE7)

    when (userType) {
        "brucos", "student", "master" -> {
            val navigationList = when (userType) {
                "brucos" -> listaBrucos
                "student" -> listaStudent
                "master" -> listaMaster
                else -> emptyList()
            }
            NavigationBar(
                containerColor = LightPink.copy(alpha = 0.9f),
                tonalElevation = 0.dp,
                modifier = Modifier.fillMaxWidth().height(100.dp),
            ) {
                navigationList.forEach { navDestination ->
                    NavigationBarItem(
                        icon = { Icon(imageVector = navDestination.ikonica, contentDescription = navDestination.ruta) },
                        label = null,
                        selected = currentRoute == navDestination.ruta,
                        onClick = {
                            if (currentRoute != navDestination.ruta) {
                                navController.navigate(navDestination.ruta)
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(selectedIconColor = PrimaryDark,
                            unselectedIconColor = Color.Gray.copy(alpha = 0.8f), indicatorColor = LightPink
                        )
                    )
                }
            }
        }
    }
}
