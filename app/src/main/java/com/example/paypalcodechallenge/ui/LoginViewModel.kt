package com.example.paypalcodechallenge.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paypalcodechallenge.data.LoginData
import com.example.paypalcodechallenge.data.LoginResponse
import com.example.paypalcodechallenge.domain.LoginRepository
import com.example.paypalcodechallenge.domain.SharedPreferencesManager
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    companion object {
        private const val USERNAME_KEY = "username"
        private const val PASSWORD_KEY = "password"
    }

    private val _loginDataState = mutableStateOf(LoginData())
    val loginDataState: State<LoginData> = _loginDataState

    private val _loadingState = mutableStateOf(false)
    val loadingState: State<Boolean> = _loadingState

    private val _loginResponseState = MutableLiveData<LoginResponse?>(null)
    val loginResponseState: LiveData<LoginResponse?> = _loginResponseState

    private val repository = LoginRepository()

    init {
        if (SharedPreferencesManager.contains(USERNAME_KEY)
            && SharedPreferencesManager.contains(PASSWORD_KEY)
        ) {
            _loginDataState.value = _loginDataState.value.copy(
                username = SharedPreferencesManager.getString(USERNAME_KEY),
                password = SharedPreferencesManager.getString(PASSWORD_KEY),
                rememberMe = true
            )

            onLoginButtonPressed()
        }
    }

    fun onUsernameChanged(newValue: String) {
        _loginDataState.value = _loginDataState.value.copy(username = newValue)
        _loginResponseState.value = null
    }

    fun onPasswordChanged(newValue: String) {
        _loginDataState.value = _loginDataState.value.copy(password = newValue)
        _loginResponseState.value = null
    }

    fun onRememberMeChanged() {
        _loginDataState.value =
            _loginDataState.value.copy(rememberMe = !_loginDataState.value.rememberMe)
    }

    fun onLoginButtonPressed() {
        _loginResponseState.value = null
        val loginData = _loginDataState.value

        if (validateLoginData()) {
            _loadingState.value = true
            viewModelScope.launch {
                repository.doLogin(loginData).collect {
                    _loginResponseState.value = it
                    _loadingState.value = false

                    if (it.success) {
                        if (loginData.rememberMe) {
                            SharedPreferencesManager.saveString(USERNAME_KEY, loginData.username)
                            SharedPreferencesManager.saveString(PASSWORD_KEY, loginData.password)
                        } else {
                            SharedPreferencesManager.clear()
                        }
                    }
                }
            }
        }
    }

    private fun validateLoginData(): Boolean {
        var isValid = true
        val loginData = _loginDataState.value

        if (loginData.username.isEmpty() || loginData.password.isEmpty()) {
            isValid = false
            _loginResponseState.value = LoginResponse(
                success = false,
                errorMessage = "Username or password is empty"
            )
        }

        return isValid
    }
}
