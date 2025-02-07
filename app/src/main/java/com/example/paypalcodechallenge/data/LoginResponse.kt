package com.example.paypalcodechallenge.data

data class LoginResponse(
    val success: Boolean,
    val errorMessage: String? = null
)