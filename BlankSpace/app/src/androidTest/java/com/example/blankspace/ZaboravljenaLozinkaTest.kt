package com.example.blankspace

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.blankspace.screens.autorizacija.zaboravljena_lozinka.ZaboravljenaLozinka_mainCard
import com.example.blankspace.ui.theme.BlankSpaceTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/*
@RunWith(AndroidJUnit4::class)
class ZaboravljenaLozinkaTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun proveriPrikazElemenata_HeaderIPlaceholder() {
        composeTestRule.setContent {
            BlankSpaceTheme {
                ZaboravljenaLozinka_mainCard(
                    modifier = androidx.compose.ui.Modifier,
                    onResetClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Zaboravljena lozinka").assertIsDisplayed()
        composeTestRule.onNodeWithText("Unesite Vaše korisničko ime da bismo postavili sigurnosno pitanje.")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Korisničko ime").assertIsDisplayed()

        composeTestRule.onNodeWithText("Postavi pitanje").assertIsDisplayed()
    }

    @Test
    fun unesiKorisnickoImeIKlikniDugme_PozivaCallback() {
        var poslatoKorisnickoIme = ""
        val testIme = "stefan123"

        composeTestRule.setContent {
            BlankSpaceTheme {
                ZaboravljenaLozinka_mainCard(
                    modifier = androidx.compose.ui.Modifier,
                    onResetClick = { username ->
                        poslatoKorisnickoIme = username
                    }
                )
            }
        }

        composeTestRule.onNodeWithText("Korisničko ime")
            .performTextInput(testIme)

        composeTestRule.onNodeWithText("Postavi pitanje").performClick()

        assert(poslatoKorisnickoIme == testIme)
    }

    @Test
    fun praznoPolje_NePozivaResetClick() {
        var callbackPozvan = false

        composeTestRule.setContent {
            BlankSpaceTheme {
                ZaboravljenaLozinka_mainCard(
                    modifier = androidx.compose.ui.Modifier,
                    onResetClick = { callbackPozvan = true }
                )
            }
        }

        composeTestRule.onNodeWithText("Postavi pitanje").performClick()
        assert(!callbackPozvan)
    }

}

 */