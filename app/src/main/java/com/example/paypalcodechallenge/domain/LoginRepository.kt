package com.example.paypalcodechallenge.domain

import com.example.paypalcodechallenge.data.LoginData
import com.example.paypalcodechallenge.data.LoginResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepository {

    suspend fun doLogin(loginData: LoginData): Flow<LoginResponse> = flow {
        delay(3000)

        val isSuccess = loginData.username == "admin" && loginData.password.length >= 6
        val errorMessage = if (isSuccess) {
            null
        } else {
            "The username or password you entered is incorrect"
        }

        emit(LoginResponse(
            success = isSuccess,
            errorMessage = errorMessage
        ))
    }
}
