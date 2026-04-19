package com.example.blankspace

import androidx.activity.ComponentActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.blankspace.data.retrofit.models.ZaboravljenaLozinkaPitanjeResponse
import com.example.blankspace.screens.autorizacija.zaboravljena_lozinka.HandlePasswordQuestionResponse
import com.example.blankspace.screens.autorizacija.zaboravljena_lozinka.ZaboravljenaLozinkaPitanje_mainCard
import com.example.blankspace.viewModels.UiStateZLP
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ZaboravljenaLozinkaPitanjeTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun zaboravljenaLozinkaPitanjeTest_headerPostoji() {
        val context = composeTestRule.activity

        composeTestRule.setContent {
            ZaboravljenaLozinkaPitanje_mainCard(
                modifier = Modifier,
                korisnickoIme = "pera",
                question = "pitanje",
                onAnswerSubmit = { _, _ -> }
            )
        }
        composeTestRule.onNodeWithText(context.getString(R.string.forgot_password)).assertExists()
        composeTestRule.onNodeWithText("pitanje").assertExists()
    }

    @Test
    fun handleForgotPasswordQuestionResponse_successPozivaNavigaciju() {
        var navigacijaPozvana = false

        val uiState = UiStateZLP(
            ZaboravljenaLozinkaPitanjeResponse(
                odgovor = "Tačan odgovor!"
            )
        )

        composeTestRule.setContent {
            HandlePasswordQuestionResponse(
                uiStateP = uiState, onSuccess = { navigacijaPozvana = true }
            )
        }
        composeTestRule.waitUntil(2_000) { navigacijaPozvana }
        assert(navigacijaPozvana)
    }

    @Test
    fun handleForgotPasswordQuestionResponse_pogresanUsername_neNavigira() {
        var navigacijaPozvana = false

        val uiState = UiStateZLP(
             ZaboravljenaLozinkaPitanjeResponse(
                odgovor = "Pogrešno korisničko ime"
            )
        )
        composeTestRule.setContent {
            HandlePasswordQuestionResponse(
                uiStateP = uiState, onSuccess = { navigacijaPozvana = true }
            )
        }
        composeTestRule.waitForIdle()
        assert(!navigacijaPozvana)
    }

    @Test
    fun zaboravljenaLozinkaPitanjeTest_klikNButton() {
        val context = composeTestRule.activity
        val buttonText = context.getString(R.string.forgot_password_conf_answer)
        var backPozvan = false

        composeTestRule.setContent {
            ZaboravljenaLozinkaPitanje_mainCard(
                modifier = Modifier,
                korisnickoIme = "testUser",
                question = "pitanje",
                onAnswerSubmit = { _, _ ->
                    backPozvan = true
                }
            )
        }

        composeTestRule.onNode(hasSetTextAction()).performTextInput("Dzeki")
        composeTestRule.onNodeWithText(buttonText).performClick()
        assert(backPozvan)
    }
}