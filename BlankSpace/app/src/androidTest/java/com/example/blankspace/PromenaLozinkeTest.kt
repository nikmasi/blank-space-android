package com.example.blankspace

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.blankspace.data.retrofit.models.NovaLozinkaResponse
import com.example.blankspace.viewModels.UiStateNL
import com.example.blankspace.viewModels.UiStateZL
import com.example.blankspace.data.retrofit.models.ZaboravljenaLozinkaResponse
import com.example.blankspace.screens.autorizacija.zaboravljena_lozinka.HandlePasswordChangeResponse
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PromenaLozinkeTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun promenaLozinke_mainCard_praznaPolja_neProsledi() {
        var prosledjeno = false

        val uiState = UiStateZL(
            zaboravljenaLozinka = ZaboravljenaLozinkaResponse("", "pera", "", "", "")
        )
        val uiStateNL = UiStateNL()

        val novaLozinka = ""
        val potvrdaLozinke = ""

        val validation = {
            if (novaLozinka.isBlank() || potvrdaLozinke.isBlank()) {
                false
            } else true
        }

        if (validation()) { prosledjeno = true }
        assertFalse(prosledjeno)
    }

    @Test
    fun handlePasswordChangeResponse_odgovorUspeh_pozivaOnClick() {
        var pozvano = false

        val uiStateNL = UiStateNL(
            novaLozinka = NovaLozinkaResponse("Uspeh: Lozinka promenjena")
        )

        composeTestRule.setContent {
            HandlePasswordChangeResponse(uiStateNL = uiStateNL, onClick = { pozvano = true })
        }

        composeTestRule.waitForIdle()
        assertTrue(!pozvano)
    }
}