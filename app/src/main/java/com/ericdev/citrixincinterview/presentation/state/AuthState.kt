package com.ericdev.citrixincinterview.presentation.state

data class AuthState(
    val isLoading: Boolean = false,
    val error: String = "",
    val success: Boolean = false
)
