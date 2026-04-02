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
import com.example.blankspace.viewModels.UiStateL
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loginTest_unesiPodatkeIKlikni_proveriCallback() {
        val context = composeTestRule.activity
        val usernameLabel = context.getString(R.string.textField_username)
        val passwordLabel = context.getString(R.string.textField_password)
        val loginButtonText = context.getString(R.string.btn_login)

        var poslatiUsername = ""
        var poslataLozinka = ""

        composeTestRule.setContent {
            LoginContent(
                uiState = UiStateL(),
                onSignUpClick = {},
                onGuestClick = {},
                onForgotClick = {},
                onLogin = { u, p ->
                    poslatiUsername = u
                    poslataLozinka = p
                },
                onNavigate = {}
            )
        }

        composeTestRule.onNodeWithText(usernameLabel).performTextInput("admin")
        composeTestRule.onNodeWithText(passwordLabel).performTextInput("admin123")
        composeTestRule.onNodeWithText(loginButtonText).performClick()

        assert(poslatiUsername == "admin")
        assert(poslataLozinka == "admin123")
    }

    @Test
    fun loginTest_uspesnaNavigacijaAdmin() {
        var navedenaRuta: String? = null

        composeTestRule.setContent {
            val mockState = UiStateL(
                login = LoginResponse(
                    tip = "A", odgovor = "Uspeh",
                    access = "",
                    refresh = null,
                    ime = "admin",
                    korisnicko_ime = "admin"
                )
            )

            LoginContent(
                uiState = mockState,
                onSignUpClick = {},
                onGuestClick = {},
                onForgotClick = {},
                onLogin = { _, _ -> },
                onNavigate = { ruta -> navedenaRuta = ruta }
            )
        }

        assert(navedenaRuta == Destinacije.PocetnaAdmin.ruta)
    }

    @Test
    fun loginTest_uspesnaNavigacijaBrucos() {
        var navedenaRuta: String? = null

        composeTestRule.setContent {
            val mockState = UiStateL(
                login = LoginResponse(
                    tip = "B", odgovor = "Uspeh",
                    access = "",
                    refresh = null,
                    ime = "brucos",
                    korisnicko_ime = "brucos"
                )
            )

            LoginContent(
                uiState = mockState,
                onSignUpClick = {},
                onGuestClick = {},
                onForgotClick = {},
                onLogin = { _, _ -> },
                onNavigate = { ruta -> navedenaRuta = ruta }
            )
        }

        assert(navedenaRuta == Destinacije.PocetnaBrucos.ruta)
    }

    @Test
    fun loginTest_uspesnaNavigacijaMaster() {
        var navedenaRuta: String? = null

        composeTestRule.setContent {
            val mockState = UiStateL(
                login = LoginResponse(
                    tip = "M", odgovor = "Uspeh",
                    access = "",
                    refresh = null,
                    ime = "master",
                    korisnicko_ime = "master"
                )
            )

            LoginContent(
                uiState = mockState,
                onSignUpClick = {},
                onGuestClick = {},
                onForgotClick = {},
                onLogin = { _, _ -> },
                onNavigate = { ruta -> navedenaRuta = ruta }
            )
        }

        assert(navedenaRuta == Destinacije.PocetnaMaster.ruta)
    }

    @Test
    fun loginTest_uspesnaNavigacijaStudent() {
        var navedenaRuta: String? = null

        composeTestRule.setContent {
            val mockState = UiStateL(
                login = LoginResponse(
                    tip = "S", odgovor = "Uspeh",
                    access = "",
                    refresh = null,
                    ime = "student",
                    korisnicko_ime = "student"
                )
            )

            LoginContent(
                uiState = mockState,
                onSignUpClick = {},
                onGuestClick = {},
                onForgotClick = {},
                onLogin = { _, _ -> },
                onNavigate = { ruta -> navedenaRuta = ruta }
            )
        }

        assert(navedenaRuta == Destinacije.PocetnaStudent.ruta)
    }


    @Test
    fun loginTest_praznaPolja_ProveraOdsustvaSlanja() {
        val context = composeTestRule.activity
        val loginButtonText = context.getString(R.string.btn_login)

        var poslatiUsername = "pocetno"
        var poslataLozinka = "pocetno"

        composeTestRule.setContent {
            LoginContent(
                uiState = UiStateL(),
                onSignUpClick = {},
                onGuestClick = {},
                onForgotClick = {},
                onLogin = { u, p ->
                    poslatiUsername = u
                    poslataLozinka = p
                },
                onNavigate = {}
            )
        }

        composeTestRule.onNodeWithText(loginButtonText).performClick()
        composeTestRule.waitForIdle()

        assert(poslatiUsername == "pocetno")
        assert(poslataLozinka == "pocetno")
    }

    @Test
    fun loginTest_uspesnaNavigacija() {
        var navedenaRuta: String? = null

        composeTestRule.setContent {
            val mockState = UiStateL(
                login = LoginResponse(
                    tip = "X", odgovor = "Uspeh",
                    access = "",
                    refresh = null,
                    ime = "x",
                    korisnicko_ime = "x"
                )
            )

            LoginContent(
                uiState = mockState,
                onSignUpClick = {},
                onGuestClick = {},
                onForgotClick = {},
                onLogin = { _, _ -> },
                onNavigate = { ruta -> navedenaRuta = ruta }
            )
        }

        assert(navedenaRuta != Destinacije.PocetnaBrucos.ruta
                && navedenaRuta != Destinacije.PocetnaMaster.ruta
                && navedenaRuta != Destinacije.PocetnaStudent.ruta
                && navedenaRuta != Destinacije.PocetnaAdmin.ruta
                && navedenaRuta != Destinacije.Pocetna.ruta
        )
    }

    @Test
    fun loginTest_neuspesnaNavigacija() {
        var navedenaRuta: String? = null

        composeTestRule.setContent {
            val mockState = UiStateL(
                login = LoginResponse(
                    tip = "X", odgovor = "Pogrešn",
                    access = "",
                    refresh = null,
                    ime = "x",
                    korisnicko_ime = "x"
                )
            )

            LoginContent(
                uiState = mockState,
                onSignUpClick = {},
                onGuestClick = {},
                onForgotClick = {},
                onLogin = { _, _ -> },
                onNavigate = { ruta -> navedenaRuta = ruta }
            )
        }

        assert(navedenaRuta != Destinacije.PocetnaBrucos.ruta
                && navedenaRuta != Destinacije.PocetnaMaster.ruta
                && navedenaRuta != Destinacije.PocetnaStudent.ruta
                && navedenaRuta != Destinacije.PocetnaAdmin.ruta
                && navedenaRuta != Destinacije.Pocetna.ruta
        )
    }

    @Test
    fun loginNavigationTest_klikNaSignUp_vodiNaRegistraciju() {
        val context = composeTestRule.activity

        val navController = TestNavHostController(context).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        composeTestRule.setContent {
            navController.graph = navController.createGraph(
                startDestination = Destinacije.Login.ruta
            ) {
                composable(Destinacije.Login.ruta) { }
                composable(Destinacije.Registracija.ruta) { }
            }

            LoginContent(
                uiState = UiStateL(),
                onSignUpClick = { navController.navigate(Destinacije.Registracija.ruta) },
                onGuestClick = {},
                onForgotClick = {},
                onLogin = { _, _ -> },
                onNavigate = {}
            )
        }

        val signUpText = context.getString(R.string.login_sign_up)

        composeTestRule
            .onNodeWithText(signUpText, substring = true, ignoreCase = true)
            .performClick()

        val actualRoute = navController.currentBackStackEntry?.destination?.route
        assert(actualRoute == Destinacije.Registracija.ruta)
    }

    @Test
    fun loginTest_proveraKlikovaNaLinkove() {
        val context = composeTestRule.activity
        var navedenaRuta: String? = null

        val forgotPasswordText = context.getString(R.string.login_forgot_password)
        val signUpText = context.getString(R.string.login_sign_up)
        val guestText = context.getString(R.string.login_continue_as_guest)

        var forgotClicked = false
        var signUpClicked = false
        var guestClicked = false

        composeTestRule.setContent {
            LoginContent(
                uiState = UiStateL(),
                onSignUpClick = {
                    signUpClicked = true
                    navedenaRuta = Destinacije.Registracija.ruta
                },
                onGuestClick = {
                    guestClicked = true
                    navedenaRuta = Destinacije.Pocetna.ruta
                },
                onForgotClick = {
                    forgotClicked = true
                    navedenaRuta = Destinacije.ZaboravljenaLozinka.ruta
                },
                onLogin = { u, p -> },
                onNavigate = { ruta -> navedenaRuta = ruta }
            )
        }

        composeTestRule.onNodeWithText(forgotPasswordText).performClick()
        assert(forgotClicked) { "Klik na 'Zaboravljena lozinka' " }
        assert(navedenaRuta == Destinacije.ZaboravljenaLozinka.ruta)

        composeTestRule.onNodeWithText(signUpText).performClick()
        assert(signUpClicked) { "Klik na 'Registruj se' " }
        assert(navedenaRuta == Destinacije.Registracija.ruta)

        composeTestRule.onNodeWithText(guestText).performClick()
        assert(guestClicked) { "Klik na 'Gost' dugme" }
        assert(navedenaRuta == Destinacije.Pocetna.ruta)
    }

}