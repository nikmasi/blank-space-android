package com.example.blankspace.viewModels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.blankspace.data.Repository
import com.example.blankspace.data.retrofit.models.LoginRequest
import com.example.blankspace.data.retrofit.models.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository,
    @ApplicationContext private val context: Context,
    private val sharedpreferences:SharedPreferences
) : ViewModel() {

    private companion object {
        const val KEY_ACCESS_TOKEN = "access_token"
        const val KEY_USERNAME = "korisnicko_ime"
        const val KEY_PASSWORD = "lozinka"
        const val KEY_USER_TYPE = "tip_korisnika"
    }


    private var _uiState = MutableStateFlow(UiStateL())
    val uiState: StateFlow<UiStateL> = _uiState

    private var _ime = MutableStateFlow(Ime())
    val ime:StateFlow<Ime> = _ime

    private val sharedPreferences = EncryptedSharedPreferences.create(
        "auth_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    init {
        checkSavedState()
    }

    private fun checkSavedState() {
        try {
            val savedUsername = sharedpreferences.getString("user_key", null)
            val savedPassword = sharedpreferences.getString("password_key", null)
            if (savedUsername != null && savedPassword != null) {
                fetchLogin(savedUsername, savedPassword)
            }
        }catch (e: Exception){
            Log.d("Login", "Error.")
        }

    }


    fun fetchLogin(username: String, password: String) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefreshing = true)
        try {
            val request = LoginRequest(username, password)
            val response = repository.login(request)
            // Sačuvaj JWT token u EncryptedSharedPreferences
            Log.d("LOGIN", response.toString())

            _uiState.value = UiStateL(login = response, isRefreshing = false)

            val editor = sharedpreferences.edit()
            editor.putString("user_key", username)
            editor.putString("password_key", password)
            editor.apply()
            _ime.value= Ime(ime=_uiState.value.login?.ime)

        } catch (e: Exception) {
            _uiState.value = UiStateL(login = null, isRefreshing = false, error = e.localizedMessage)
        }
    }

    fun izloguj_se(){
        val editor = sharedpreferences.edit()

        editor.putString("user_key", "")
        editor.putString("password_key", "")
        editor.apply()

        _ime.value= Ime(ime=_uiState.value.login?.ime)
        _uiState.value = _uiState.value.copy(login = null)

        //fetchLogin("","")
    }

    fun setKorisnik(UIStateR:UiStateR)= viewModelScope.launch {
        _uiState.value = _uiState.value.copy(
            login = UIStateR.registration?.access?.let {
                UIStateR.registration?.korisnicko_ime?.let { it1 ->
                    LoginResponse(
                        it,
                        UIStateR.registration?.refresh,UIStateR.registration.ime, it1,UIStateR.registration.tip,"")
                }
            },
            isRefreshing = false,error = null)
    }

    fun setKorisnikZL(UIStateZL:UiStateZL)= viewModelScope.launch {
        _uiState.value = _uiState.value.copy(
            login = UIStateZL.zaboravljenaLozinka?.korisnicko_ime?.let {
                UIStateZL.zaboravljenaLozinka?.tip?.let { it1 ->
                    LoginResponse("",null,"",
                        it, it1,""
                    )
                }
            }, isRefreshing = false,error = null)
    }
}

data class UiStateL(
    val login: LoginResponse?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)

data class Ime(
    val ime:String?=""
)