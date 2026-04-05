package com.example.blankspace

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.blankspace.data.retrofit.models.LoginResponse
import com.example.blankspace.screens.Destinacije
import com.example.blankspace.screens.autorizacija.login.LoginContent
import com.example.blankspace.screens.autorizacija.registracija.RegistracijaContent
import com.example.blankspace.viewModels.UiStateL
import com.example.blankspace.viewModels.UiStateR
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistracijaTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun registracijaTest_unesiSvePodatkeIKlikni_proveriCallback() {
        val context = composeTestRule.activity

        val nameLabel = context.getString(R.string.full_name)
        val usernameLabel = context.getString(R.string.username)
        val passwordLabel = context.getString(R.string.password)
        val coPasswordLabel = context.getString(R.string.re_enter_password)
        val questionLabel = context.getString(R.string.secret_question)
        val answerLabel = context.getString(R.string.secret_answer)
        val registerButtonText = context.getString(R.string.registration_button)

        var rezultati = mutableMapOf<String, String>()

        composeTestRule.setContent {
            RegistracijaContent(
                uiState = UiStateR(),
                onClickAction = { name, user, pass, copass, quest, ans ->
                    rezultati["name"] = name
                    rezultati["user"] = user
                    rezultati["pass"] = pass
                    rezultati["copass"] = copass
                    rezultati["quest"] = quest
                    rezultati["ans"] = ans
                },
                onBackToLogin = {},
                onClickPocetna = {}
            )
        }

        composeTestRule.onNodeWithText(nameLabel).performTextInput("Marko Markovic")
        composeTestRule.onNodeWithText(usernameLabel).performTextInput("marko")
        composeTestRule.onNodeWithText(passwordLabel).performTextInput("marko123")
        composeTestRule.onNodeWithText(coPasswordLabel).performTextInput("marko123")
        composeTestRule.onNodeWithText(questionLabel).performTextInput("Ime psa?")
        composeTestRule.onNodeWithText(answerLabel).performTextInput("Bobi")

        composeTestRule.onNodeWithText(registerButtonText).performClick()

        assert(rezultati["name"]== "Marko Markovic")
        assert(rezultati["user"]== "marko")
        assert( rezultati["pass"] == "marko123")
        assert( rezultati["copass"] == "marko123")
        assert( rezultati["quest"] == "Ime psa?")
        assert(rezultati["ans"] == "Bobi")
    }

    @Test
    fun registracijaTest_praznaPolja_neSmePozvatiRegistraciju() {
        val context = composeTestRule.activity
        val registerButtonText = context.getString(R.string.registration_button)
        var callbackPozvan = false

        composeTestRule.setContent {
            RegistracijaContent(
                uiState = UiStateR(),
                onClickAction = { _, _, _, _, _, _ -> callbackPozvan = true },
                onBackToLogin = {},
                onClickPocetna = {}
            )
        }

        composeTestRule.onNodeWithText(registerButtonText).performClick()
        assert(!callbackPozvan)
    }

    @Test
    fun registracijaTest_imeBezRazmaka_neSmePozvatiRegistraciju() {
        val context = composeTestRule.activity
        val nameLabel = context.getString(R.string.full_name)
        val registerButtonText = context.getString(R.string.registration_button)
        var callbackPozvan = false

        composeTestRule.setContent {
            RegistracijaContent(
                uiState = UiStateR(),
                onClickAction = { _, _, _, _, _, _ -> callbackPozvan = true },
                onBackToLogin = {},
                onClickPocetna = {}
            )
        }

        composeTestRule.onNodeWithText(nameLabel).performTextInput("Marko")
        composeTestRule.onNodeWithText(registerButtonText).performClick()

        assert(!callbackPozvan)
    }

    @Test
    fun registracijaTest_klikNaPovratak_pozivaBackCallback() {
        val context = composeTestRule.activity
        val loginLinkText = context.getString(R.string.registration_log_in)
        var backPozvan = false

        composeTestRule.setContent {
            RegistracijaContent(
                uiState = UiStateR(),
                onClickAction = { _, _, _, _, _, _ -> },
                onBackToLogin = { backPozvan = true },
                onClickPocetna = {}
            )
        }

        composeTestRule.onNodeWithText(loginLinkText, substring = true).performClick()
        assert(backPozvan)
    }
}