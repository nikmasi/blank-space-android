package com.example.blankspace.screens.igra_offline.models

import androidx.compose.ui.graphics.Color

sealed class Tezina(val naziv: String, val boja: Color, val ruta: String) {
    object Easy : Tezina("Easy", Color(0xFF5AB1BB), "easy")
    object Normal : Tezina("Normal", Color(0xFFEC8FB7), "normal")
    object Hard : Tezina("Hard", Color(0xFF49006B), "hard")

    companion object {
        val sviNivoi = listOf(Easy, Normal, Hard)
    }
}