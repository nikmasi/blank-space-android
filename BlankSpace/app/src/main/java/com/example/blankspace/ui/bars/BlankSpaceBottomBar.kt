package com.example.blankspace.ui.bars

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.blankspace.screens.listaBrucos
import com.example.blankspace.screens.listaMaster
import com.example.blankspace.screens.listaStudent


@Composable
fun BlankSpaceBottomBar(navController: NavController, currentRoute: String, userType: String) {
    when (userType) {
        "brucos", "student", "master" -> {
            val navigationList = when (userType) {
                "brucos" -> listaBrucos
                "student" -> listaStudent
                "master" -> listaMaster
                else -> emptyList()
            }
            NavigationBar(containerColor = Color(0xFFF0DAE7)) {
                navigationList.forEach { navDestination ->
                    NavigationBarItem(
                        icon = {
                            Icon(imageVector = navDestination.ikonica, contentDescription = null,)
                        },
                        label = { Text(text = navDestination.ruta) },
                        selected = currentRoute == navDestination.ruta,
                        onClick = { navController.navigate(navDestination.ruta) },
                        colors = NavigationBarItemDefaults.colors(selectedIconColor = Color.DarkGray,
                            unselectedIconColor = Color.Gray, selectedTextColor = Color.DarkGray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                }
            }
        }
    }
}


