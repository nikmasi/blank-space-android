package com.example.blankspace

import androidx.activity.ComponentActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.blankspace.data.retrofit.models.ZaboravljenaLozinkaResponse
import com.example.blankspace.screens.autorizacija.zaboravljena_lozinka.HandleForgotPasswordResponse
import com.example.blankspace.screens.autorizacija.zaboravljena_lozinka.ZaboravljenaLozinka_mainCard
import com.example.blankspace.viewModels.UiStateZL
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ZaboravljenaLozinkaTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun zaboravljenaLozinkaTest_proslediTacanUsername() {
        val context = composeTestRule.activity
        val buttonText = context.getString(R.string.forgot_password_question_ask)

        var prosledjeniUsername = ""

        composeTestRule.setContent {
            ZaboravljenaLozinka_mainCard(
                modifier = Modifier,
                onResetClick = { prosledjeniUsername = it }
            )
        }

        val username = "pera123"

        composeTestRule.onNodeWithText(
            context.getString(R.string.textField_username)
        ).performTextInput(username)

        composeTestRule.onNodeWithText(buttonText).performClick()
        assert(prosledjeniUsername == username)
    }

    @Test
    fun zaboravljenaLozinkaTest_headerPostoji() {
        val context = composeTestRule.activity

        composeTestRule.setContent {
            ZaboravljenaLozinka_mainCard(
                modifier = Modifier,
                onResetClick = {}
            )
        }

        composeTestRule.onNodeWithText(context.getString(R.string.forgot_password)
        ).assertExists()

        composeTestRule.onNodeWithText(context.getString(R.string.forgot_password_question_username)
        ).assertExists()
    }

    @Test
    fun handleForgotPasswordResponse_successPozivaNavigaciju() {
        var navigacijaPozvana = false

        val uiState = UiStateZL(
            zaboravljenaLozinka = ZaboravljenaLozinkaResponse(
                odgovor = "OK",
                korisnicko_ime = "pera",
                pitanje_lozinka = "kucni ljubimac",
                odgovor_lozinka = "Dzeki",
                tip = ""
            )
        )

        composeTestRule.setContent {
            HandleForgotPasswordResponse(
                uiState = uiState, onSuccess = { navigacijaPozvana = true }
            )
        }
        composeTestRule.waitUntil(timeoutMillis = 2_000) { navigacijaPozvana }
        assert(navigacijaPozvana)
    }

    @Test
    fun handleForgotPasswordResponse_pogresanUsername_neNavigira() {
        var navigacijaPozvana = false

        val uiState = UiStateZL(
            zaboravljenaLozinka = ZaboravljenaLozinkaResponse(
                odgovor = "Pogrešno korisničko ime",
                korisnicko_ime = "pera",
                pitanje_lozinka = "kucni ljubimac",
                odgovor_lozinka = "Dzeki",
                tip = ""
            )
        )

        composeTestRule.setContent {
            HandleForgotPasswordResponse(
                uiState = uiState, onSuccess = { navigacijaPozvana = true }
            )
        }
        composeTestRule.waitForIdle()
        assert(!navigacijaPozvana)
    }

    @Test
    fun zaboravljenaLozinkaTest_klikNButton() {
        val context = composeTestRule.activity
        val buttonText = context.getString(R.string.forgot_password_question_ask)

        var backPozvan = false

        composeTestRule.setContent {
            ZaboravljenaLozinka_mainCard(
                modifier = Modifier,
                onResetClick = { backPozvan = true }
            )
        }

        composeTestRule.onNodeWithText(context.getString(R.string.textField_username)
        ).performTextInput("testUser")

        composeTestRule.onNodeWithText(buttonText).performClick()
        assert(backPozvan)
    }
}