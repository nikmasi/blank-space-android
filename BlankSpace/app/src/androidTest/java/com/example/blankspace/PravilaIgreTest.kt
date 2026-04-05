package com.example.blankspace

import com.example.blankspace.screens.profil_rang_pravila.pravila_igre.PravilaIgre
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToNode
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.blankspace.ui.theme.BlankSpaceTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PravilaIgreTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun proveriNaslovIElemente_PrikazaniSu() {
        composeTestRule.setContent {
            BlankSpaceTheme {
                PravilaIgre(onClick = {})
            }
        }

        composeTestRule.onNodeWithText("Pravila Igre").assertIsDisplayed()
        composeTestRule.onNodeWithText("Opis").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mogućnosti").assertIsDisplayed()
    }

    @Test
    fun proveriTabeluRangova_SadrziPodatke() {
        composeTestRule.setContent {
            BlankSpaceTheme {
                PravilaIgre(onClick = {})
            }
        }

        composeTestRule.onNode(hasScrollAction())
            .performScrollToNode(hasText("Napredak"))

        composeTestRule.onNodeWithText("BRUCOŠ").assertIsDisplayed()
    }

    @Test
    fun klikNaKreni_ZatvaraPravila() {
        var clicked = false

        composeTestRule.setContent {
            BlankSpaceTheme {
                PravilaIgre(onClick = { clicked = true })
            }
        }

        composeTestRule.onNode(hasScrollAction())
            .performScrollToNode(hasText("KRENI"))
        composeTestRule.onNodeWithText("KRENI")
            .performScrollTo().performClick()

        assert(clicked)
    }
}