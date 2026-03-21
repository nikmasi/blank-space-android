package com.example.blankspace.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blankspace.data.AuthRepository
import com.example.blankspace.data.retrofit.models.LoginRequest
import com.example.blankspace.data.retrofit.models.LoginResponse
import com.example.blankspace.data.storage.TokenManagerInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManagerInterface
) : ViewModel() {

    private var _uiState = MutableStateFlow(UiStateL())
    val uiState: StateFlow<UiStateL> = _uiState

    private var _ime = MutableStateFlow(Ime())
    val ime:StateFlow<Ime> = _ime

    private var _uiStateNotifikacija = MutableStateFlow(UiStateNotifikacija())
    val uiStateNotifikacija: StateFlow<UiStateNotifikacija> = _uiStateNotifikacija


    fun setNotificationTime(hour: Int, minute: Int) {
        val vreme = String.format("%02d:%02d", hour, minute)
        _uiStateNotifikacija.value = _uiStateNotifikacija.value.copy(vreme = vreme)
    }

    init {
        checkSavedState()
    }

    private fun checkSavedState() {
        val (username, password) = tokenManager.getSavedUser()
        if (!username.isNullOrEmpty() && !password.isNullOrEmpty()) {
            fetchLogin(username, password)
        }
    }


    fun fetchLogin(username: String, password: String) = viewModelScope.launch {
        _uiState.update { it.copy(isRefreshing = true) }
        try {
            val response = authRepository.login(LoginRequest(username, password))
            tokenManager.saveToken(response.access)
            tokenManager.saveUserSession(username, password)

            _uiState.update { UiStateL(login = response, isRefreshing = false) }
        } catch (e: Exception) {
            _uiState.update { it.copy(isRefreshing = false, error = e.localizedMessage) }
        }
    }

    fun izloguj_se(){
        tokenManager.clearSession()
        _uiState.update { UiStateL(login = null) }
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

data class UiStateNotifikacija(
    var vreme:String=""
)
